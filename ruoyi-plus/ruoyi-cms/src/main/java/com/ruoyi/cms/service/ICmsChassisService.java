package com.ruoyi.cms.service;

import java.util.List;
import com.ruoyi.cms.domain.CmsChassis;

/**
 * 底盘Service接口
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
public interface ICmsChassisService 
{
    /**
     * 查询底盘
     * 
     * @param chassisId 底盘主键
     * @return 底盘
     */
    public CmsChassis selectCmsChassisByChassisId(Long chassisId);

    /**
     * 查询底盘列表
     * 
     * @param cmsChassis 底盘
     * @return 底盘集合
     */
    public List<CmsChassis> selectCmsChassisList(CmsChassis cmsChassis);

    /**
     * 新增底盘
     * 
     * @param cmsChassis 底盘
     * @return 结果
     */
    public int insertCmsChassis(CmsChassis cmsChassis);

    /**
     * 修改底盘
     * 
     * @param cmsChassis 底盘
     * @return 结果
     */
    public int updateCmsChassis(CmsChassis cmsChassis);

    /**
     * 批量删除底盘
     * 
     * @param chassisIds 需要删除的底盘主键集合
     * @return 结果
     */
    public int deleteCmsChassisByChassisIds(String chassisIds);

    /**
     * 删除底盘信息
     * 
     * @param chassisId 底盘主键
     * @return 结果
     */
    public int deleteCmsChassisByChassisId(Long chassisId);
}
