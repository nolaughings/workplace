package com.ruoyi.plugs.holiday.mapper;


import com.ruoyi.plugs.holiday.domain.SysHoliday;

import java.util.List;

/**
 * 节假日Mapper接口
 *
 * @author ruoyi-plus
 * @date 2021-09-30
 */
public interface SysHolidayMapper
{
    /**
     * 查询节假日
     *
     * @param id 节假日ID
     * @return 节假日
     */
    public SysHoliday selectSysHolidayById(Long id);

    /**
     * 查询节假日列表
     *
     * @param sysHoliday 节假日
     * @return 节假日集合
     */
    public List<SysHoliday> selectSysHolidayList(SysHoliday sysHoliday);

    /**
     * 新增节假日
     *
     * @param sysHoliday 节假日
     * @return 结果
     */
    public int insertSysHoliday(SysHoliday sysHoliday);

    /**
     * 修改节假日
     *
     * @param sysHoliday 节假日
     * @return 结果
     */
    public int updateSysHoliday(SysHoliday sysHoliday);

    /**
     * 删除节假日
     *
     * @param id 节假日ID
     * @return 结果
     */
    public int deleteSysHolidayById(Long id);

    /**
     * 批量删除节假日
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysHolidayByIds(String[] ids);
}
