package com.ruoyi.cms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cms.mapper.CmsRobotMapper;
import com.ruoyi.cms.domain.CmsRobot;
import com.ruoyi.cms.service.ICmsRobotService;
import com.ruoyi.common.core.text.Convert;

/**
 * 人工智能机器人Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Service
public class CmsRobotServiceImpl implements ICmsRobotService 
{
    @Autowired
    private CmsRobotMapper cmsRobotMapper;

    /**
     * 查询人工智能机器人
     * 
     * @param robotId 人工智能机器人主键
     * @return 人工智能机器人
     */
    @Override
    public CmsRobot selectCmsRobotByRobotId(Long robotId)
    {
        return cmsRobotMapper.selectCmsRobotByRobotId(robotId);
    }

    /**
     * 查询人工智能机器人列表
     * 
     * @param cmsRobot 人工智能机器人
     * @return 人工智能机器人
     */
    @Override
    public List<CmsRobot> selectCmsRobotList(CmsRobot cmsRobot)
    {
        return cmsRobotMapper.selectCmsRobotList(cmsRobot);
    }

    /**
     * 新增人工智能机器人
     * 
     * @param cmsRobot 人工智能机器人
     * @return 结果
     */
    @Override
    public int insertCmsRobot(CmsRobot cmsRobot)
    {
        return cmsRobotMapper.insertCmsRobot(cmsRobot);
    }

    /**
     * 修改人工智能机器人
     * 
     * @param cmsRobot 人工智能机器人
     * @return 结果
     */
    @Override
    public int updateCmsRobot(CmsRobot cmsRobot)
    {
        return cmsRobotMapper.updateCmsRobot(cmsRobot);
    }

    /**
     * 批量删除人工智能机器人
     * 
     * @param robotIds 需要删除的人工智能机器人主键
     * @return 结果
     */
    @Override
    public int deleteCmsRobotByRobotIds(String robotIds)
    {
        return cmsRobotMapper.deleteCmsRobotByRobotIds(Convert.toStrArray(robotIds));
    }

    /**
     * 删除人工智能机器人信息
     * 
     * @param robotId 人工智能机器人主键
     * @return 结果
     */
    @Override
    public int deleteCmsRobotByRobotId(Long robotId)
    {
        return cmsRobotMapper.deleteCmsRobotByRobotId(robotId);
    }
}
