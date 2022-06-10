package com.ruoyi.plugs.framework.event.eventTrigger;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.plugs.framework.event.ApplicationEvent;
import com.ruoyi.plugs.framework.event.ApplicationEventDefined;
import com.ruoyi.plugs.framework.event.IApplicationEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 定时任务被触发或者被手工触发
 */
@ApplicationEvent({ApplicationEventDefined.ON_SCHEDULER_EXECUTED, ApplicationEventDefined.ON_SCHEDULER_EXECUTED_BY_HAND})
@Component
@ConditionalOnProperty(prefix = "plugs.sysEvent",name = {"enabled"},havingValue = "true",matchIfMissing = true)
public class TaskEventTrigger  implements IApplicationEvent {
    @Override
    public boolean onTrigger(Object source, Object params) {
        System.out.println("系统任务被触发："+source.toString()+"\t\t"+ JSON.toJSONString(params));
        return true;
    }

    @Override
    public AjaxResult ajaxResultTrigger(Object source, Object params) {
        return null;
    }
}
