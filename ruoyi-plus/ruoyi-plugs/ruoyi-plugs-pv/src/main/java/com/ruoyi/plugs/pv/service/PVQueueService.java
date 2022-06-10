package com.ruoyi.plugs.pv.service;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.plugs.common.utils.UserAgentUtils;
import com.ruoyi.plugs.pv.config.PvConfig;
import com.ruoyi.plugs.pv.domain.Pv;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.util.AntPathMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component("pvQueueService")
@ConditionalOnProperty(value = "plugs.pv.enable",havingValue = "1",matchIfMissing = false)
public class PVQueueService {

    private static final Logger logger = LogManager.getLogger(PVQueueService.class);
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static final String SCOPE_INCLUDE="include";
    private static final String SCOPE_EXCLUDE="exclude";
    private String scope= PvConfig.getScope();
    private String pvUrls=PvConfig.getPvUrls();
    private Integer pvCacheMinute=PvConfig.getPvCacheMinute();
    private String serverContextPath=PvConfig.getServerContextPath();
    private List<String> urls=new ArrayList<>();
    private Cache<String, Integer> cache= CacheUtil.newFIFOCache(2000,1000*60*pvCacheMinute);//1分钟
    @Autowired
    IPvService pvService;

    //创建一个可重用固定线程数的线程池
    private ExecutorService pool = Executors.newFixedThreadPool(1);

    private static final BlockingQueue<Pv> blockingQueue = new ArrayBlockingQueue<Pv>(1000000);

    //线程活动
    private volatile boolean threadActivity = true;

    @PostConstruct
    public void init(){
        String[] arr= Convert.toStrArray(pvUrls);
        urls= Lists.newArrayList(arr);
        logger.info("PVQueueService init===>scope:"+scope+"\tcacheMinute:"+pvCacheMinute);
        try {
            pool.execute(new Runnable(){
                @Override
                public void run() {
                    while (threadActivity) { //如果系统关闭，则不再运行
                        try {
                            List<Pv> data = new ArrayList<Pv>();
                            //每次到1000条数据才进行入库，或者等待1分钟，没达到100条也继续入库
                            Queues.drain(blockingQueue, data, 50, 1, TimeUnit.MINUTES);//第三个参数：数量; 第四个参数：时间; 第五个参数：时间单位
                            if(CollectionUtil.isNotEmpty(data)){
                                pvService.insertPvBatch(data);
                            }

                        } catch (InterruptedException e) {
                            //    e.printStackTrace();
                            if (logger.isErrorEnabled()) {
                                logger.error("访问量消费队列错误",e);
                            }
                        }
                    }
                }});
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void pushPvQueue3(HttpServletRequest request,Pv pv){

        pv.setIp(IpUtils.getIpAddr(request));
        if(StringUtils.isEmpty(pv.getReferer())){
            pv.setReferer(request.getHeader("referer"));
        }

        Browser browserObj = UserAgentUtils.getBrowser(request);
        String browser=browserObj.getName();//浏览器类型
        if(UserAgentUtils.isFirefox(request)){
            browser="Firefox";
        }
        if(UserAgentUtils.isQQBrowser(request)){
            browser="QQBrowser";
        }
        pv.setBrowser(browser);
        String deviceType="Unknown";//设备类型
        DeviceType deviceType1=UserAgentUtils.getDeviceType(request);//是否pc
        if(deviceType1!=null){
            deviceType=deviceType1.getName();
        }
        pv.setDeviceType(deviceType);

        //add(anObject):添加元素到队列里，添加成功返回true，容量满了添加失败会抛出IllegalStateException异常
        //offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false.（本方法不阻塞当前执行方法的线程）
        //offer(E o, long timeout, TimeUnit unit),可以设定等待的时间，如果在指定的时间内，还不能往队列中加入BlockingQueue，则返回失败。
        //put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续.
        blockingQueue.offer(pv);//添加一个元素并返回true 如果队列已满，则返回false
    }

    private void pushPvQueue2(HttpServletRequest request, Pv pv, String referer){

        String ip= IpUtils.getIpAddr(request);
        String key=ip+"_"+referer;
        Integer count=cache.get(key);
        if(count==null){
            count=0;
        }
        if(count==0){
            this.pushPvQueue3(request,pv);
        }
        count++;
        cache.put(key,count);
    }
    public void pushPvQueue(HttpServletRequest request,Pv pv) {
        String referer=request.getHeader("referer");
        referer=referer.replaceAll(serverContextPath,"");
        if(SCOPE_INCLUDE.equals(scope)){
            if(urlMatch(referer,urls)){
                this.pushPvQueue2(request,pv,referer);
            }
        }else if(SCOPE_EXCLUDE.equals(scope)){
            if(!urlMatch(referer,urls)){
                this.pushPvQueue2(request,pv,referer);
            }
        }else{
            this.pushPvQueue2(request,pv,referer);
        }
    }
    @PreDestroy
    public void destroy() {
        threadActivity = false;
        pool.shutdownNow();
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
