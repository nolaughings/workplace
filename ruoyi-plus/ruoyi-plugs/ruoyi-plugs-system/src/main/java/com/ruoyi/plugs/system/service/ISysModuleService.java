package com.ruoyi.plugs.system.service;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.plugs.system.domain.SysModule;

import java.util.List;

/**
 * 系统模块Service接口
 *
 * @author wujiyue
 * @date 2021-08-27
 */
public interface ISysModuleService
{
    /**
     * 查询系统模块
     *
     * @param id 系统模块ID
     * @return 系统模块
     */
    public SysModule selectSysModuleById(Long id);

    /**
     * 查询系统模块列表
     *
     * @param sysModule 系统模块
     * @return 系统模块集合
     */
    public List<SysModule> selectSysModuleList(SysModule sysModule);

    /**
     * 新增系统模块
     *
     * @param sysModule 系统模块
     * @return 结果
     */
    public int insertSysModule(SysModule sysModule);

    /**
     * 修改系统模块
     *
     * @param sysModule 系统模块
     * @return 结果
     */
    public int updateSysModule(SysModule sysModule);

    /**
     * 批量删除系统模块
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysModuleByIds(String ids);

    /**
     * 删除系统模块信息
     *
     * @param id 系统模块ID
     * @return 结果
     */
    public int deleteSysModuleById(Long id);

    /**
     * 查询系统模块树列表
     *
     * @return 所有系统模块信息
     */
    public List<Ztree> selectSysModuleTree();

    public String checkCodeUnique(SysModule sysModule);

    public int changeStatus(SysModule sysModule);
}
