package com.ruoyi.cms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.cms.mapper.CmsChassisMapper;
import com.ruoyi.cms.domain.CmsChassis;
import com.ruoyi.cms.service.ICmsChassisService;
import com.ruoyi.common.core.text.Convert;

/**
 * 底盘Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Service
public class CmsChassisServiceImpl implements ICmsChassisService 
{
    @Autowired
    private CmsChassisMapper cmsChassisMapper;

    /**
     * 查询底盘
     * 
     * @param chassisId 底盘主键
     * @return 底盘
     */
    @Override
    public CmsChassis selectCmsChassisByChassisId(Long chassisId)
    {
        return cmsChassisMapper.selectCmsChassisByChassisId(chassisId);
    }

    /**
     * 查询底盘列表
     * 
     * @param cmsChassis 底盘
     * @return 底盘
     */
    @Override
    public List<CmsChassis> selectCmsChassisList(CmsChassis cmsChassis)
    {
        return cmsChassisMapper.selectCmsChassisList(cmsChassis);
    }

    /**
     * 新增底盘
     * 
     * @param cmsChassis 底盘
     * @return 结果
     */
    @Override
    public int insertCmsChassis(CmsChassis cmsChassis)
    {
        return cmsChassisMapper.insertCmsChassis(cmsChassis);
    }

    /**
     * 修改底盘
     * 
     * @param cmsChassis 底盘
     * @return 结果
     */
    @Override
    public int updateCmsChassis(CmsChassis cmsChassis)
    {
        return cmsChassisMapper.updateCmsChassis(cmsChassis);
    }

    /**
     * 批量删除底盘
     * 
     * @param chassisIds 需要删除的底盘主键
     * @return 结果
     */
    @Override
    public int deleteCmsChassisByChassisIds(String chassisIds)
    {
        return cmsChassisMapper.deleteCmsChassisByChassisIds(Convert.toStrArray(chassisIds));
    }

    /**
     * 删除底盘信息
     * 
     * @param chassisId 底盘主键
     * @return 结果
     */
    @Override
    public int deleteCmsChassisByChassisId(Long chassisId)
    {
        return cmsChassisMapper.deleteCmsChassisByChassisId(chassisId);
    }
}
