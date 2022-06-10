package com.ruoyi.plugs.framework.event;


import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 系统事件接口
 * Created by zengchao on 2017-03-09.
 */
public interface IApplicationEvent {
    /**
     * 当事件被触发时
     * @param source
     * @param params
     */
    boolean onTrigger(Object source, Object params);

    AjaxResult ajaxResultTrigger(Object source, Object params);
}
