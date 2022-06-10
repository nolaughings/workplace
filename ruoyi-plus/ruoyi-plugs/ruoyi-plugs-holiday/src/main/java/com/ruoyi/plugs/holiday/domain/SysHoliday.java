package com.ruoyi.plugs.holiday.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 节假日对象 plugs_sys_holiday
 *
 * @author ruoyi-plus
 * @date 2021-09-30
 */
public class SysHoliday extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 节日简码 */
    @Excel(name = "节日简码")
    private String code;

    /** 节日名称 */
    @Excel(name = "节日名称")
    private String name;

    /** 开始日期 */
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 活动开始日期 */
    @Excel(name = "活动开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activityStartDate;

    /** 活动结束日期 */
    @Excel(name = "活动结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activityEndDate;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setYear(String year)
    {
        this.year = year;
    }

    public String getYear()
    {
        return year;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setActivityStartDate(Date activityStartDate)
    {
        this.activityStartDate = activityStartDate;
    }

    public Date getActivityStartDate()
    {
        return activityStartDate;
    }
    public void setActivityEndDate(Date activityEndDate)
    {
        this.activityEndDate = activityEndDate;
    }

    public Date getActivityEndDate()
    {
        return activityEndDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("year", getYear())
            .append("code", getCode())
            .append("name", getName())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("activityStartDate", getActivityStartDate())
            .append("activityEndDate", getActivityEndDate())
            .append("createTime", getCreateTime())
            .toString();
    }
}
