package com.ruoyi.plugs.system.service.impl;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.plugs.system.domain.SysModule;
import com.ruoyi.plugs.system.mapper.SysModuleMapper;
import com.ruoyi.plugs.system.service.ISysModuleService;
import com.ruoyi.plugs.system.service.SysModuleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统模块Service业务层处理
 *
 * @author ruoyi
 * @date 2021-08-27
 */
@Service
public class SysModuleServiceImpl implements ISysModuleService
{
    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Autowired
    private SysModuleCache sysModuleCache;

    /**
     * 查询系统模块
     *
     * @param id 系统模块ID
     * @return 系统模块
     */
    @Override
    public SysModule selectSysModuleById(Long id)
    {
        return sysModuleMapper.selectSysModuleById(id);
    }

    /**
     * 查询系统模块列表
     *
     * @param sysModule 系统模块
     * @return 系统模块
     */
    @Override
    public List<SysModule> selectSysModuleList(SysModule sysModule)
    {
        return sysModuleMapper.selectSysModuleList(sysModule);
    }

    /**
     * 新增系统模块
     *
     * @param sysModule 系统模块
     * @return 结果
     */
    @Override
    public int insertSysModule(SysModule sysModule)
    {
        sysModule.setCreateTime(DateUtils.getNowDate());
        int n=sysModuleMapper.insertSysModule(sysModule);
        if(n>0){
            sysModuleCache.MODULE_CACHE.put(sysModule.getCode(),sysModule);
        }
        return n;
    }

    /**
     * 修改系统模块
     *
     * @param sysModule 系统模块
     * @return 结果
     */
    @Override
    public int updateSysModule(SysModule sysModule)
    {
        int n=sysModuleMapper.updateSysModule(sysModule);
        if(n>0){
            sysModuleCache.MODULE_CACHE.put(sysModule.getCode(),sysModule);
        }
        return n;
    }

    /**
     * 删除系统模块对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysModuleByIds(String ids)
    {
        String[] arr= Convert.toStrArray(ids);
        if(arr==null||arr.length<=0){
            return 0;
        }
        List<SysModule> list=new ArrayList<>();
        for(String s:arr){
            SysModule sysModule=sysModuleMapper.selectSysModuleById(Long.valueOf(s));
            if(sysModule!=null){
                list.add(sysModule);
            }
        }
        int n=sysModuleMapper.deleteSysModuleByIds(arr);
        if(n>0){
            for(SysModule sysModule:list){
                sysModuleCache.MODULE_CACHE.remove(sysModule.getCode());
            }
        }
        return n;
    }

    /**
     * 删除系统模块信息
     *
     * @param id 系统模块ID
     * @return 结果
     */
    @Override
    public int deleteSysModuleById(Long id)
    {
        SysModule sysModule=sysModuleMapper.selectSysModuleById(id);
        int n=sysModuleMapper.deleteSysModuleById(id);
        if(n>0){
            sysModuleCache.MODULE_CACHE.remove(sysModule.getCode());
        }
        return n;
    }

    /**
     * 查询系统模块树列表
     *
     * @return 所有系统模块信息
     */
    @Override
    public List<Ztree> selectSysModuleTree()
    {
        List<SysModule> sysModuleList = sysModuleMapper.selectSysModuleList(new SysModule());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (SysModule sysModule : sysModuleList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(sysModule.getId());
            ztree.setpId(sysModule.getParentId());
            ztree.setName(sysModule.getName());
            ztree.setTitle(sysModule.getName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    @Override
    public String checkCodeUnique(SysModule sysModule) {
        SysModule db=sysModuleMapper.selectSysModuleByCode(sysModule.getCode());
        if((db!=null&&(sysModule.getId()!=null&&!db.getId().equals(sysModule.getId())))||(db!=null&&sysModule.getId()==null)){
            return "1";
        }
        return "0";
    }

    @Override
    public int changeStatus(SysModule sysModule) {
        SysModule sysModuleUpdate=new SysModule();
        sysModuleUpdate.setId(sysModule.getId());
        sysModuleUpdate.setStatus(sysModule.getStatus());
        int n=sysModuleMapper.updateSysModule(sysModuleUpdate);
        if(n>0){
            SysModule newModule=sysModuleMapper.selectSysModuleById(sysModuleUpdate.getId());
            sysModuleCache.MODULE_CACHE.put(newModule.getCode(),newModule);
        }
        return n;
    }
}
