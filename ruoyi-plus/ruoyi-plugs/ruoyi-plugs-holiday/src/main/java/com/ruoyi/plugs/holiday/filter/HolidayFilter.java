package com.ruoyi.plugs.holiday.filter;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.plugs.holiday.domain.SysHoliday;
import com.ruoyi.plugs.holiday.service.ISysHolidayService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayFilter extends AccessControlFilter {
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private List<String> urls=new ArrayList<>();
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    @Autowired
    private ISysHolidayService holidayService;
    public static Cache<String, String> holidayCache= CacheUtil.newFIFOCache(10,1000*60*60*24);
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        if(servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request=(HttpServletRequest)servletRequest;
            String uri=request.getRequestURI();
            //if (!ServletUtils.isAjaxRequest(request)){
            if(urlMatch(uri,urls)){
                String date= DateUtils.getDate();
                String key1="holiday_"+date;
                String key2="holidayActivity_"+date;
                String value1=holidayCache.get(key1);
                if(StringUtils.isEmpty(value1)){
                    SysHoliday form=new SysHoliday();
                    form.setYear(DateUtils.getNowDate().getYear()+"");
                    Map params=new HashMap();
                    params.put("holiday","holiday");
                    form.setParams(params);
                    List<SysHoliday> list=holidayService.selectSysHolidayList(form);
                    if(CollectionUtils.isNotEmpty(list)){
                        value1=list.get(0).getCode();
                        holidayCache.put(key1,value1);
                    }
                }
                String value2=holidayCache.get(key2);
                if(StringUtils.isEmpty(value2)){
                    SysHoliday form=new SysHoliday();
                    form.setYear(DateUtils.getNowDate().getYear()+"");
                    Map params=new HashMap();
                    params.put("holidayActivity","holidayActivity");
                    form.setParams(params);
                    List<SysHoliday> list=holidayService.selectSysHolidayList(form);
                    if(CollectionUtils.isNotEmpty(list)){
                        value2=list.get(0).getCode();
                        holidayCache.put(key2,value2);
                    }
                }
                if(StringUtils.isEmpty(value1)){
                    value1="";
                }
                if(StringUtils.isEmpty(value2)){
                    value2="";
                }
                request.setAttribute("holiday",value1);
                request.setAttribute("holidayActivity",value2);
                System.out.println("uri="+uri+"\tholiday="+value1+"\tholidayActivity="+value2);
            }
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
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
