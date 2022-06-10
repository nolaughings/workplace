package com.ruoyi.plugs.system.mapper;


import com.ruoyi.plugs.system.domain.SysEventLog;

import java.util.List;

/**
 * 系统事件日志Mapper接口
 *
 * @author markbro
 * @date 2021-09-15
 */
public interface SysEventLogMapper
{
    /**
     * 查询系统事件日志
     *
     * @param id 系统事件日志ID
     * @return 系统事件日志
     */
    public SysEventLog selectSysEventLogById(Long id);

    /**
     * 查询系统事件日志列表
     *
     * @param sysEventLog 系统事件日志
     * @return 系统事件日志集合
     */
    public List<SysEventLog> selectSysEventLogList(SysEventLog sysEventLog);

    /**
     * 新增系统事件日志
     *
     * @param sysEventLog 系统事件日志
     * @return 结果
     */
    public int insertSysEventLog(SysEventLog sysEventLog);

    /**
     * 修改系统事件日志
     *
     * @param sysEventLog 系统事件日志
     * @return 结果
     */
    public int updateSysEventLog(SysEventLog sysEventLog);

    /**
     * 删除系统事件日志
     *
     * @param id 系统事件日志ID
     * @return 结果
     */
    public int deleteSysEventLogById(Long id);

    /**
     * 批量删除系统事件日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysEventLogByIds(String[] ids);
}
