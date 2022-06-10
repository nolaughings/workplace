package com.ruoyi.cms.service;

import java.util.List;
import com.ruoyi.cms.domain.CmsRobot;

/**
 * 人工智能机器人Service接口
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
public interface ICmsRobotService 
{
    /**
     * 查询人工智能机器人
     * 
     * @param robotId 人工智能机器人主键
     * @return 人工智能机器人
     */
    public CmsRobot selectCmsRobotByRobotId(Long robotId);

    /**
     * 查询人工智能机器人列表
     * 
     * @param cmsRobot 人工智能机器人
     * @return 人工智能机器人集合
     */
    public List<CmsRobot> selectCmsRobotList(CmsRobot cmsRobot);

    /**
     * 新增人工智能机器人
     * 
     * @param cmsRobot 人工智能机器人
     * @return 结果
     */
    public int insertCmsRobot(CmsRobot cmsRobot);

    /**
     * 修改人工智能机器人
     * 
     * @param cmsRobot 人工智能机器人
     * @return 结果
     */
    public int updateCmsRobot(CmsRobot cmsRobot);

    /**
     * 批量删除人工智能机器人
     * 
     * @param robotIds 需要删除的人工智能机器人主键集合
     * @return 结果
     */
    public int deleteCmsRobotByRobotIds(String robotIds);

    /**
     * 删除人工智能机器人信息
     * 
     * @param robotId 人工智能机器人主键
     * @return 结果
     */
    public int deleteCmsRobotByRobotId(Long robotId);
}
