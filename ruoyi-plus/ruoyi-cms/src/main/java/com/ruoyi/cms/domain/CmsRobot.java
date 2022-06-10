package com.ruoyi.cms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 人工智能机器人对象 cms_robot
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
public class CmsRobot extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机器人编号 */
    @Excel(name = "机器人编号")
    private Long robotId;

    /** 机器人照片 */
    @Excel(name = "机器人照片")
    private String robotImg;

    /** 机器人名称 */
    @Excel(name = "机器人名称")
    private String robotName;

    /** 机器人功能 */
    @Excel(name = "机器人功能")
    private String robotFunction;

    public void setRobotId(Long robotId) 
    {
        this.robotId = robotId;
    }

    public Long getRobotId() 
    {
        return robotId;
    }
    public void setRobotImg(String robotImg) 
    {
        this.robotImg = robotImg;
    }

    public String getRobotImg() 
    {
        return robotImg;
    }
    public void setRobotName(String robotName) 
    {
        this.robotName = robotName;
    }

    public String getRobotName() 
    {
        return robotName;
    }
    public void setRobotFunction(String robotFunction) 
    {
        this.robotFunction = robotFunction;
    }

    public String getRobotFunction() 
    {
        return robotFunction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("robotId", getRobotId())
            .append("robotImg", getRobotImg())
            .append("robotName", getRobotName())
            .append("robotFunction", getRobotFunction())
            .toString();
    }
}
