package com.ruoyi.plugs.framework.event;


import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.plugs.system.domain.SysEventLog;
import com.ruoyi.plugs.system.service.ISysEventLogService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 事件管理器
 */
public class ApplicationEventManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private   int  logType;//日志记录类型  2记录所有 1记录成功 0 记录失败

    private Map<ApplicationEventDefined, List<IApplicationEvent>> eventMap = new HashMap<>();//按照系事件分组

    List<IApplicationEvent> applicationEventList;//所有实现系统事件类

    ISysEventLogService eventLogService;

    public void setApplicationEventList(List<IApplicationEvent> applicationEventList) {
        this.applicationEventList = applicationEventList;
    }

    public void setEventLogService(ISysEventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    /**
     * 绑定事件
     *
     * @param applicationEventDefineds 系统自定义事件数组
     * @param applicationEvent     事件处理程序
     */
    public synchronized void bind(ApplicationEventDefined[] applicationEventDefineds, IApplicationEvent applicationEvent) {
        for(ApplicationEventDefined applicationEventDefined:applicationEventDefineds){
            List<IApplicationEvent> eventList = eventMap.get(applicationEventDefined);
            if (eventList == null) {
                eventList = new ArrayList<>();
            }
            eventList.add(applicationEvent);
            eventMap.put(applicationEventDefined, eventList);

            logger.debug("Bind Event : " + applicationEventDefined.name());
        }

    }

    /**
     * 解绑事件
     *
     * @param applicationEventDefined 事件名称
     * @param applicationEvent     事件处理程序
     */
    public synchronized void unbind(ApplicationEventDefined applicationEventDefined, IApplicationEvent applicationEvent) {
        List<IApplicationEvent> eventList = eventMap.get(applicationEventDefined);
        if (eventList == null) {
            return;
        }
        eventList.remove(applicationEvent);
        logger.debug("UnBind Event : " + applicationEventDefined.name());
    }

    /**
     * 触发事件
     *
     * @param applicationEventDefined 事件名称
     * @param source    来源
     * @param params    参数
     */
    public synchronized void trigger(ApplicationEventDefined applicationEventDefined, Object source, Object params) {
        List<IApplicationEvent> eventList = eventMap.get(applicationEventDefined);
        if (eventList == null) {
            return;
        }
        for (IApplicationEvent event : eventList) {
            try {
               boolean flag= event.onTrigger(source, params);
               if(logType==2){
                   saveLog(applicationEventDefined,source,params,flag);
               }else if(logType==0){
                   if(!flag){
                       saveLog(applicationEventDefined,source,params,flag);
                   }
                }else{
                   if(flag){
                       saveLog(applicationEventDefined,source,params,flag);
                   }
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized AjaxResult ajaxResultTrigger(ApplicationEventDefined applicationEventDefined, Object source, Object params) {
        List<IApplicationEvent> eventList = eventMap.get(applicationEventDefined);
        if (eventList == null||eventList.size()==0) {
            return null;
        }
        IApplicationEvent event=eventList.get(0);
        try {
            AjaxResult ajaxResult= event.ajaxResultTrigger(source, params);
            if(logType==2){
                saveLog(applicationEventDefined,source,params,ajaxResult.isSuccess());
            }else if(logType==0){
                if(!ajaxResult.isSuccess()){
                    saveLog(applicationEventDefined,source,params,ajaxResult.isSuccess());
                }
            }else{
                if(ajaxResult.isSuccess()){
                    saveLog(applicationEventDefined,source,params,ajaxResult.isSuccess());
                }
            }
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void saveLog(ApplicationEventDefined applicationEventDefined, Object source, Object params,boolean flag){
            String clsName="";
            if(source instanceof String){
                clsName=source.toString();
            }else{
                clsName=source.getClass().getTypeName();
            }
            //JSONObject jsonObject= JSONObject.parseObject(JSON.toJSONString(params));
            SysEventLog log= new SysEventLog();
            SysUser user= ShiroUtils.getSysUser();
            log.setUserId(user.getUserId().toString());
            log.setUserName(user.getUserName());
            log.setEventCode(applicationEventDefined.getValue());
            log.setEventName(applicationEventDefined.getDescription());
            log.setCreateTime(new Date());
            log.setSource(clsName);
            log.setDatas(JSON.toJSONString(params));
            if(flag){
                log.setResult(1);
            }else{
                log.setResult(0);
            }
            eventLogService.insertSysEventLog(log);
    }
    /**
     * 初始化
     * 扫描系统中注解形式的事件
     */
    @PostConstruct
    public void init() {
        if(CollectionUtils.isNotEmpty(applicationEventList)){
            for(IApplicationEvent event:applicationEventList){
                 Class cls =   event.getClass();
                Object instance = null;
                if(!cls.getName().contains("proxy")){
                    instance= SpringUtils.getBean(cls);
                    if (instance instanceof IApplicationEvent) {
                        ApplicationEvent eventAnno =(ApplicationEvent) cls.getAnnotation(ApplicationEvent.class);
                        this.bind(eventAnno.value(), (IApplicationEvent) instance);
                    }
                }
            }
            logger.info("系统事件总计注册数量：" + this.eventMap.size());
            //遍历自定义系统内置事件列出各个事件注册数量
            ApplicationEventDefined[] applicationEventDefineds= ApplicationEventDefined.values();
            for(ApplicationEventDefined applicationEventDefined:applicationEventDefineds){
                List<IApplicationEvent> eventList = eventMap.get(applicationEventDefined);
                if(CollectionUtils.isNotEmpty(eventList)){
                    logger.info("系统事件"+applicationEventDefined.getValue()+"["+applicationEventDefined.getDescription()+"]注册数量：" + eventList.size());
                }else{
                    logger.info("系统事件"+applicationEventDefined.getValue()+"["+applicationEventDefined.getDescription()+"]注册数量：0");
                }
            }

        }
    }
}
