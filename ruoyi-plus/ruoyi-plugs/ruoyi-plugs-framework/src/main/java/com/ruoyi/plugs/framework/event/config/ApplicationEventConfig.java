package com.ruoyi.plugs.framework.event.config;


import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.plugs.framework.event.ApplicationEventManager;
import com.ruoyi.plugs.framework.event.IApplicationEvent;
import com.ruoyi.plugs.system.service.ISysEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 系统事件配置
 *
 * @author ruoyi
 */
@Configuration
@ConditionalOnProperty(prefix = "plugs.sysEvent",name = {"enabled"},havingValue = "true",matchIfMissing = true)
public class ApplicationEventConfig
{

    @Value("${plugs.sysEvent.logType}")
    public  String  logType;//日志记录类型  2记录所有 1记录成功 0 记录失败
    @Autowired
    List<IApplicationEvent> applicationEventList;//所有实现系统事件类
    @Autowired
    ISysEventLogService eventLogService;//系统事件日志记录

    /**
     * 系统事件管理器
     * @return
     */
    @Bean("applicationEventManager")
    public ApplicationEventManager getApplicationEventManager(){
        ApplicationEventManager applicationEventManager =new ApplicationEventManager();
        applicationEventManager.setApplicationEventList(applicationEventList);
        applicationEventManager.setEventLogService(eventLogService);
        if(StringUtils.isEmpty(logType)){
            logType="2";
        }
        int logTypeInt=2;
        try{
            logTypeInt=Integer.valueOf(logType);
        }catch (Exception ex){}
        applicationEventManager.setLogType(logTypeInt);
        return applicationEventManager;
    }
}
