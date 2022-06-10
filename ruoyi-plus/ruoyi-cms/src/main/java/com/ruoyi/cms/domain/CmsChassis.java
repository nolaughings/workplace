package com.ruoyi.cms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 底盘对象 cms_chassis
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
public class CmsChassis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 底盘编号 */
    @Excel(name = "底盘编号")
    private Long chassisId;

    /** 底盘照片 */
    @Excel(name = "底盘照片")
    private String chassisImg;

    /** 底盘名称 */
    @Excel(name = "底盘名称")
    private String chassisName;

    /** 底盘功能 */
    @Excel(name = "底盘功能")
    private String chassisFunction;

    public void setChassisId(Long chassisId) 
    {
        this.chassisId = chassisId;
    }

    public Long getChassisId() 
    {
        return chassisId;
    }
    public void setChassisImg(String chassisImg) 
    {
        this.chassisImg = chassisImg;
    }

    public String getChassisImg() 
    {
        return chassisImg;
    }
    public void setChassisName(String chassisName) 
    {
        this.chassisName = chassisName;
    }

    public String getChassisName() 
    {
        return chassisName;
    }
    public void setChassisFunction(String chassisFunction) 
    {
        this.chassisFunction = chassisFunction;
    }

    public String getChassisFunction() 
    {
        return chassisFunction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("chassisId", getChassisId())
            .append("chassisImg", getChassisImg())
            .append("chassisName", getChassisName())
            .append("chassisFunction", getChassisFunction())
            .toString();
    }
}
