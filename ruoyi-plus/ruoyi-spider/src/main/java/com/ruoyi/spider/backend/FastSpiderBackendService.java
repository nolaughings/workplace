package com.ruoyi.spider.backend;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.plugs.common.core.domain.ICallBack;
import com.ruoyi.spider.config.SpiderConstants;
import com.ruoyi.spider.domain.SpiderConfig;
import com.ruoyi.spider.domain.SpiderField;
import com.ruoyi.spider.domain.SpiderFiledRule;
import com.ruoyi.spider.domain.SpiderMission;
import com.ruoyi.spider.mapper.SpiderFieldMapper;
import com.ruoyi.spider.mapper.SpiderFiledRuleMapper;
import com.ruoyi.spider.processer.AbstractProcessor;
import com.ruoyi.spider.processer.DefalutProcessor;
import com.ruoyi.spider.service.ISpiderConfigService;
import com.ruoyi.spider.service.ISpiderMissionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 运行多线程用户爬取任务
 */
public class FastSpiderBackendService extends Thread {
    protected final Logger logger = LoggerFactory.getLogger(FastSpiderBackendService.class);
    private ICallBack callBack;
    private SpiderMission spiderMission;
    private SpiderConfig spiderConfig;
    private String uuid;

    public static void main(String[] args) {
    }
    public FastSpiderBackendService(SpiderMission spiderMission,SpiderConfig spiderConfig,ICallBack callBack,String uuid){
        this.spiderMission=spiderMission;
        this.spiderConfig=spiderConfig;
        this.callBack=callBack;
        this.uuid=uuid;
    }
    @Override
    public void run() {
        if(spiderMission!=null){
            Date start=new Date();
            logger.info(">>>>>>>>>>>>爬虫任务开始>>>>>>>>>>>>"+Thread.currentThread().getName());
            //设置入口地址
            String entryUrls=spiderMission.getEntryUrls();
            if(StringUtils.isNotEmpty(entryUrls)){
                spiderConfig.setEntryUrls(entryUrls);
            }
            //设置退出方式
            spiderConfig.setExitWay(spiderMission.getExitWay());
            Long c= spiderMission.getExitWayCount();
            if(c==null){
                c=0L;
            }
            spiderConfig.setCount(Integer.valueOf(c.toString()));
            //设置header
            if(StringUtils.isNotEmpty(spiderMission.getHeaderStr())){
                spiderConfig.setHeader(spiderMission.getHeaderStr());
            }
            //设置Cookie
            if(StringUtils.isNotEmpty(spiderMission.getCookieStr())){
                spiderConfig.setCookie(spiderMission.getCookieStr());
            }
            AbstractProcessor processor=new DefalutProcessor(spiderConfig,uuid);
            //执行爬虫并接收返回数据
            CopyOnWriteArrayList<LinkedHashMap<String, Object>> datas = processor.execute();
            if(callBack!=null){
                Map<String,CopyOnWriteArrayList<LinkedHashMap<String, Object>>> rmap=new HashMap();
                rmap.put("datas",datas);
                callBack.setParams(rmap);
                callBack.onSuccess();
            }

            Date end=new Date();
            Long timeSeconds=(end.getTime()-start.getTime())/1000;
            logger.info(">>>>>>>>>>>>爬虫任务结束>>>>>耗时>"+timeSeconds+"秒>>>>>>>总计爬取到"+datas.size()+"条数据!"+Thread.currentThread().getName());
        }

    }
}
