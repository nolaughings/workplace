package com.ruoyi.plugs.pv.filter;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.plugs.common.constant.Constants;
import com.ruoyi.plugs.pv.domain.PvCount;
import com.ruoyi.plugs.pv.service.IPvStaticsService;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PageStaticsFilter extends AccessControlFilter {

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static final String SCOPE_INCLUDE="include";
    private static final String SCOPE_EXCLUDE="exclude";
    private List<String> urls=new ArrayList<>();
    private String scope="all";
    private int cacheSeconds=30;
    private int saveDbSeconds=60;
    private int totalPvCount;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private Date buildSiteDate;
    private AtomicInteger totalAtomicInteger;
    public static ConcurrentHashMap<String,Object> dataContextMap=new ConcurrentHashMap<String,Object>();

    @Autowired
    private IPvStaticsService pvStaticsService;
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public void setBuildSiteDate(Date buildSiteDate) {
        this.buildSiteDate = buildSiteDate;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setTotalPvCount(int totalPvCount) {
        this.totalPvCount = totalPvCount;
    }
    private Cache<String, Integer> cache;
    public PageStaticsFilter(int cacheSeconds, int saveDbSeconds, int totalPvCount){
        this.cacheSeconds=cacheSeconds;
        this.saveDbSeconds=saveDbSeconds;
        this.totalPvCount=totalPvCount;
        cache= CacheUtil.newFIFOCache(2000,1000*cacheSeconds);//30秒
        totalAtomicInteger = new AtomicInteger(totalPvCount);
    }

    private static final String KEY_LAST_SAVE="lastSaveTime";

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        if(servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request=(HttpServletRequest)servletRequest;
            String uri=request.getRequestURI();
            if (!isStaticUrl(uri) && !ServletUtils.isAjaxRequest(request)){
                if(SCOPE_INCLUDE.equals(scope)){
                    if(urlMatch(uri,urls)){
                        statics(request,uri);
                    }
                }else if(SCOPE_EXCLUDE.equals(scope)){
                    if(!urlMatch(uri,urls)){
                        statics(request,uri);
                    }
                }else{
                    statics(request,uri);
                }
            }
        }
        return true;
    }
    private void statics(HttpServletRequest request, String uri){
        synchronized (this){
            String ip= IpUtils.getIpAddr(request);
            String key=ip+"_"+uri;
            String day= DateUtils.getDate();
            Integer n=cache.get(key);
            System.out.println("n=>>>>>>>key="+key+"\t n=>"+n);
            if(n==null){
                n=0;
            }
            if(n==0){//计数
                Date lastTime=(Date)dataContextMap.get(KEY_LAST_SAVE);
                if(lastTime==null){
                    lastTime=new Date();
                    dataContextMap.put(KEY_LAST_SAVE,lastTime);
                }
                int count =atomicInteger.incrementAndGet();
                if(new Date().after(DateUtils.addSeconds(lastTime,saveDbSeconds))){
                    dataContextMap.put(KEY_LAST_SAVE,new Date());
                    atomicInteger.set(0);
                    PvCount pvCount=pvStaticsService.getPvCountByDay(day);
                    if(pvCount==null){
                        pvCount=new PvCount();
                        pvCount.setDay(DateUtils.getNowDate());
                        pvCount.setCount(Long.valueOf(count));
                        pvStaticsService.insertPvCount(pvCount);
                    }else{
                        pvCount.setCount(pvCount.getCount()+ Long.valueOf(count));
                        pvStaticsService.updatePvCount(pvCount);
                    }

                }
                totalAtomicInteger.incrementAndGet();
            }
            n++;
            cache.put(key,n);
            int c=totalAtomicInteger.get();
            request.setAttribute(Constants.KEY_PAGE_STATICS_COUNT,c);
            request.setAttribute(Constants.KEY_PAGE_STATICS_USERS,getStaticsValue(Constants.KEY_PAGE_STATICS_USERS));
            request.setAttribute(Constants.KEY_PAGE_STATICS_ARTICLES,getStaticsValue(Constants.KEY_PAGE_STATICS_ARTICLES));
            request.setAttribute(Constants.KEY_PAGE_STATICS_TODAY,getStaticsValue(Constants.KEY_PAGE_STATICS_TODAY));
            request.setAttribute(Constants.KEY_PAGE_STATICS_WEEK,getStaticsValue(Constants.KEY_PAGE_STATICS_WEEK));
            request.setAttribute(Constants.KEY_PAGE_STATICS_RUNNING,getStaticsValue(Constants.KEY_PAGE_STATICS_RUNNING));
        }
    }
    private Object getStaticsValue(String key){
        Object v=dataContextMap.get(key);
        if(v==null){
            switch (key){
                case Constants.KEY_PAGE_STATICS_USERS:
                    v=pvStaticsService.selectUserCount();
                    break;
                case Constants.KEY_PAGE_STATICS_ARTICLES:
                    v=pvStaticsService.selectArticlesCount();
                    break;
                case Constants.KEY_PAGE_STATICS_TODAY:
                    v=pvStaticsService.selectTodayCount();
                    break;
                case Constants.KEY_PAGE_STATICS_WEEK:
                    v=pvStaticsService.selectWeekCount();
                    break;
                 default:
                     v= DateUtil.between(buildSiteDate, new Date(), DateUnit.DAY);
            }
            dataContextMap.put(key,v);
        }
        return v;
    }
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }

    private boolean isStaticUrl(String uri){
        if(uri.endsWith(".js")||uri.endsWith(".css")||uri.endsWith(".jpg")||uri.endsWith(".png")||uri.endsWith(".ico")||uri.endsWith(".ttf")||uri.endsWith(".woff")||uri.endsWith(".woff2")){
            return true;
        }
        return false;
    }
    private boolean urlMatch(String servletPath, List<String> paths){
        for (String path:paths) {
            String uriPattern = path.trim();
            // 支持ANT表达式
            if (antPathMatcher.match(uriPattern, servletPath)) {
                return true;
            }
        }
        return false;
    }
}
