package com.ruoyi.spider.backend;

import com.ruoyi.common.core.domain.AjaxResult;
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
import com.ruoyi.spider.service.ISpiderMissionFinish;
import com.ruoyi.spider.service.ISpiderMissionService;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 任务爬虫入口类，通过传入爬虫任务id，从数据库中查询配置参数
 * 该种方式的爬虫相比快速配置的FastSpiderBackendService来比，它可以设置字段值处理规则。
 * 该类只能单用户单线程调用
 */
public class SpiderBackendService extends Thread {
    protected final Logger logger = LoggerFactory.getLogger(SpiderBackendService.class);
    private String missionId;
    private ICallBack callBack;


    private ISpiderMissionService spiderMissionService= SpringUtils.getBean(ISpiderMissionService.class);//爬虫任务

    private ISpiderConfigService spiderConfigService=SpringUtils.getBean(ISpiderConfigService.class);//爬虫配置

    private SpiderFieldMapper spiderFieldMapper=SpringUtils.getBean(SpiderFieldMapper.class);//爬虫字段

    private SpiderFiledRuleMapper spiderFiledRuleMapper=SpringUtils.getBean(SpiderFiledRuleMapper.class);//爬虫字段值处理规则

    public SpiderBackendService(String missionId){
        this.missionId=missionId;
    }

    public SpiderBackendService(String missionId,ICallBack callBack){
        this.missionId=missionId;
        this.callBack=callBack;
    }
    @Override
    public void run() {
        SpiderMission mission=spiderMissionService.selectSpiderMissionById(Long.valueOf(missionId));
        if(mission!=null){
            if(SpiderConstants.SPIDER_MISSION_STATUS_RUNNING.equals(mission.getStatus())){
                logger.warn(">>>>>>>>>>>>>>>爬虫任务["+missionId+"]已经在运行!本次不在执行!<<<<<<<<<");
                return;
            }
            //查询爬虫配置
            Long configId=mission.getSpiderConfigId();
            SpiderConfig config = spiderConfigService.selectSpiderConfigById(configId);

            //查询字段配置
            SpiderField queryForm=new SpiderField();
            queryForm.setConfigId(config.getId());
            List<SpiderField> fields = spiderFieldMapper.selectSpiderFieldList(queryForm);
            config.setFieldsList(fields);
            //设置字段值处理规则
            for(SpiderField field:fields){
                SpiderFiledRule ruleQueryForm=new SpiderFiledRule();
                ruleQueryForm.setFieldId(field.getFieldId().toString());
                List<SpiderFiledRule> rules = spiderFiledRuleMapper.selectSpiderFiledRuleList(ruleQueryForm);
                field.setFieldRules(rules);
            }
            //设置入口地址
            String entryUrls=mission.getEntryUrls();
            if(StringUtils.isNotEmpty(entryUrls)){
                config.setEntryUrls(entryUrls);
            }
            logger.info("===>入口地址为:");
            for(int i=0;i<config.getEntryUrlsList().size();i++){
                logger.info(config.getEntryUrlsList().get(i));
            }
            if(mission.getLoopFlag()==1&&config.getEntryUrlsList().size()==1&&StringUtils.isNotEmpty(mission.getLoopParam())) {//注意:当翻页标志位1并且入口地址只有一个的时候才会计算翻页
                config.setCascade(0);//该模式不启用级联发现url
                List<String> allUrl=new ArrayList<String>();
                allUrl=pageUrls_Reg(config.getEntryUrlsList().get(0),mission.getLoopParam(),mission.getLoopNum());
                config.setEntryUrlsList(allUrl);
                logger.info("===>经计算翻页后的入口地址为:");
                for(int i=0;i<allUrl.size();i++){
                    logger.info(allUrl.get(i));
                }
            }
            //设置退出方式
            config.setExitWay(mission.getExitWay());
            Long c= mission.getExitWayCount();
            if(c==null){
                c=0L;
            }
            config.setCount(Integer.valueOf(c.toString()));
            //设置header
            if(StringUtils.isNotEmpty(mission.getHeaderStr())){
                config.setHeader(mission.getHeaderStr());
            }
            //设置Cookie
            if(StringUtils.isNotEmpty(mission.getCookieStr())){
                config.setCookie(mission.getCookieStr());
            }
            AbstractProcessor processor=new DefalutProcessor(config,missionId.toString());

            //执行开始前更新爬虫任务状态
            mission.setStatus(SpiderConstants.SPIDER_MISSION_STATUS_RUNNING);
            mission.setStartTime(new Date());
            spiderMissionService.updateSpiderMission(mission);
            //执行爬虫并接收返回数据
            CopyOnWriteArrayList<LinkedHashMap<String, Object>> datas = processor.execute();
            //执行任务结束回调
            if(com.ruoyi.common.utils.StringUtils.isNotEmpty(config.getMissionFinish())){
                String[] arr=config.getMissionFinish().split(",");
                if(arr!=null&&arr.length>0){
                    for(String str:arr){
                        if(com.ruoyi.common.utils.StringUtils.isNotEmpty(str)){
                            char[]chars = str.toCharArray();
                            chars[0] += 32;
                            str=String.valueOf(chars);
                            ISpiderMissionFinish finish = SpringUtils.getBean(str);
                            if(finish!=null){
                                finish.onFinish(datas);
                            }
                        }
                    }
                }
            }

            //执行结束后更新爬虫任务状态
            mission.setEndTime(new Date());
            mission.setStatus(SpiderConstants.SPIDER_MISSION_STATUS_DONE);
            mission.setSuccessNum(Long.valueOf(datas.size()));
            Long count=(mission.getEndTime().getTime()-mission.getStartTime().getTime())/1000;
            mission.setTimeCost(count.toString());
            spiderMissionService.updateSpiderMission(mission);
            if(callBack!=null){
                Map<String,CopyOnWriteArrayList<LinkedHashMap<String, Object>>> rmap=new HashMap();
                rmap.put("datas",datas);
                callBack.setParams(rmap);
                callBack.onSuccess();
            }
        }
    }


    /**
     *获得分页URL集合
     * @param url
     *              起始的url
     * @param s
     *              用来截取当前页面
     * @param loopNum
     *              分页次数
     * @return
     */
    private static List<String> pageUrls_Reg(String url, String s, int loopNum){
        //根据正则解析分页url
        // http://localhost:8099/sys/spider?code=&page=11
        // https://699pic.com/music/so-so-0-0-0-0-0-complex-0-1-0-36.html
        String[] arr=s.split("#");
        String s1="";
        String s2="";
        if(arr.length==1){
            s1=arr[0];
        }else{
            s1=arr[0];
            s2=arr[1];
        }
        List<String> urls=new ArrayList<String>();
        String  nowNumStr="";
        if(com.ruoyi.common.utils.StringUtils.isEmpty(s2)){
            nowNumStr= com.ruoyi.plugs.common.utils.StringUtils.trim_end_exclu(url,s1);
        }else{
            nowNumStr= com.ruoyi.plugs.common.utils.StringUtils.trim_mid_exclu(url,s1,s2);
        }
        int nowNum=1;
        nowNum=Integer.valueOf(nowNumStr);
        for(int i=0;i<loopNum;i++){
            url=s1+nowNum+s2;
            urls.add(url);
            nowNum++;
        }
        return urls;
    }

}
