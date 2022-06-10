package com.ruoyi.plugs.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 系统事件日志对象 plugs_sys_event_log
 *
 * @author markbro
 * @date 2021-09-15
 */
public class SysEventLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;
    private String userName;//扩展字段
    /** 事件编号 */
    @Excel(name = "事件编号")
    private String eventCode;

    /** 事件名称 */
    @Excel(name = "事件名称")
    private String eventName;

    /** 来源 */
    @Excel(name = "来源")
    private String source;

    /** 参数 */
    private String datas;

    /** 结果 */
    @Excel(name = "结果")
    private Integer result;

    /** 标志 */
    @Excel(name = "标志")
    private Integer flag;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setEventCode(String eventCode)
    {
        this.eventCode = eventCode;
    }

    public String getEventCode()
    {
        return eventCode;
    }
    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public String getEventName()
    {
        return eventName;
    }
    public void setSource(String source)
    {
        this.source = source;
    }

    public String getSource()
    {
        return source;
    }
    public void setDatas(String datas)
    {
        this.datas = datas;
    }

    public String getDatas()
    {
        return datas;
    }
    public void setResult(Integer result)
    {
        this.result = result;
    }

    public Integer getResult()
    {
        return result;
    }
    public void setFlag(Integer flag)
    {
        this.flag = flag;
    }

    public Integer getFlag()
    {
        return flag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("eventCode", getEventCode())
            .append("eventName", getEventName())
            .append("source", getSource())
            .append("datas", getDatas())
            .append("result", getResult())
            .append("createTime", getCreateTime())
            .append("flag", getFlag())
            .toString();
    }
}
