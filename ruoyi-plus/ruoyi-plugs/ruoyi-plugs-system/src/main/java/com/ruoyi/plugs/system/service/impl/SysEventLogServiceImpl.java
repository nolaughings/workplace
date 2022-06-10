package com.ruoyi.plugs.system.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.plugs.system.domain.SysEventLog;
import com.ruoyi.plugs.system.mapper.SysEventLogMapper;
import com.ruoyi.plugs.system.service.ISysEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统事件日志Service业务层处理
 *
 * @author ruoyi-plus
 * @date 2021-09-15
 */
@Service
public class SysEventLogServiceImpl implements ISysEventLogService
{
    @Autowired
    private SysEventLogMapper sysEventLogMapper;

    /**
     * 查询系统事件日志
     *
     * @param id 系统事件日志ID
     * @return 系统事件日志
     */
    @Override
    public SysEventLog selectSysEventLogById(Long id)
    {
        return sysEventLogMapper.selectSysEventLogById(id);
    }

    /**
     * 查询系统事件日志列表
     *
     * @param sysEventLog 系统事件日志
     * @return 系统事件日志
     */
    @Override
    public List<SysEventLog> selectSysEventLogList(SysEventLog sysEventLog)
    {
        return sysEventLogMapper.selectSysEventLogList(sysEventLog);
    }

    /**
     * 新增系统事件日志
     *
     * @param sysEventLog 系统事件日志
     * @return 结果
     */
    @Override
    public int insertSysEventLog(SysEventLog sysEventLog)
    {
        sysEventLog.setCreateTime(DateUtils.getNowDate());
        return sysEventLogMapper.insertSysEventLog(sysEventLog);
    }

    /**
     * 修改系统事件日志
     *
     * @param sysEventLog 系统事件日志
     * @return 结果
     */
    @Override
    public int updateSysEventLog(SysEventLog sysEventLog)
    {
        return sysEventLogMapper.updateSysEventLog(sysEventLog);
    }

    /**
     * 删除系统事件日志对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysEventLogByIds(String ids)
    {
        return sysEventLogMapper.deleteSysEventLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统事件日志信息
     *
     * @param id 系统事件日志ID
     * @return 结果
     */
    @Override
    public int deleteSysEventLogById(Long id)
    {
        return sysEventLogMapper.deleteSysEventLogById(id);
    }
}
