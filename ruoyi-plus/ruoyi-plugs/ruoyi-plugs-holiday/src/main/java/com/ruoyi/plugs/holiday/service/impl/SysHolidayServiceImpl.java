package com.ruoyi.plugs.holiday.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.plugs.holiday.domain.SysHoliday;
import com.ruoyi.plugs.holiday.mapper.SysHolidayMapper;
import com.ruoyi.plugs.holiday.service.ISysHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 节假日Service业务层处理
 *
 * @author ruoyi-plus
 * @date 2021-09-30
 */
@Service
public class SysHolidayServiceImpl implements ISysHolidayService
{
    @Autowired
    private SysHolidayMapper sysHolidayMapper;

    /**
     * 查询节假日
     *
     * @param id 节假日ID
     * @return 节假日
     */
    @Override
    public SysHoliday selectSysHolidayById(Long id)
    {
        return sysHolidayMapper.selectSysHolidayById(id);
    }

    /**
     * 查询节假日列表
     *
     * @param sysHoliday 节假日
     * @return 节假日
     */
    @Override
    public List<SysHoliday> selectSysHolidayList(SysHoliday sysHoliday)
    {
        return sysHolidayMapper.selectSysHolidayList(sysHoliday);
    }

    /**
     * 新增节假日
     *
     * @param sysHoliday 节假日
     * @return 结果
     */
    @Override
    public int insertSysHoliday(SysHoliday sysHoliday)
    {
        sysHoliday.setCreateTime(DateUtils.getNowDate());
        return sysHolidayMapper.insertSysHoliday(sysHoliday);
    }

    /**
     * 修改节假日
     *
     * @param sysHoliday 节假日
     * @return 结果
     */
    @Override
    public int updateSysHoliday(SysHoliday sysHoliday)
    {
        return sysHolidayMapper.updateSysHoliday(sysHoliday);
    }

    /**
     * 删除节假日对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysHolidayByIds(String ids)
    {
        return sysHolidayMapper.deleteSysHolidayByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除节假日信息
     *
     * @param id 节假日ID
     * @return 结果
     */
    @Override
    public int deleteSysHolidayById(Long id)
    {
        return sysHolidayMapper.deleteSysHolidayById(id);
    }
}
