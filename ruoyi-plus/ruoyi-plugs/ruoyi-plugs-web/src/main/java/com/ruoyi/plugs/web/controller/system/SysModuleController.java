package com.ruoyi.plugs.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.plugs.system.domain.SysModule;
import com.ruoyi.plugs.system.service.ISysModuleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统模块Controller
 *
 * @author wujiyue
 * @date 2021-08-27
 */
@Controller
@RequestMapping("/plugs/sysModule")
public class SysModuleController extends BaseController
{
    private String prefix = "plugs/system/sysModule";

    @Autowired
    private ISysModuleService sysModuleService;

    @RequiresPermissions("plugs:sysModule:view")
    @GetMapping()
    public String sysModule()
    {
        return prefix + "/sysModule";
    }

    /**
     * 查询系统模块树列表
     */
    @RequiresPermissions("plugs:sysModule:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysModule> list(SysModule sysModule)
    {
        List<SysModule> list = sysModuleService.selectSysModuleList(sysModule);
        return list;
    }

    /**
     * 导出系统模块列表
     */
    @RequiresPermissions("plugs:sysModule:export")
    @Log(title = "系统模块", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysModule sysModule)
    {
        List<SysModule> list = sysModuleService.selectSysModuleList(sysModule);
        ExcelUtil<SysModule> util = new ExcelUtil<SysModule>(SysModule.class);
        return util.exportExcel(list, "sysModule");
    }

    /**
     * 新增系统模块
     */
    @GetMapping(value = { "/add/{id}", "/add/" })
    public String add(@PathVariable(value = "id", required = false) Long id, ModelMap mmap)
    {
        if (StringUtils.isNotNull(id))
        {
            mmap.put("sysModule", sysModuleService.selectSysModuleById(id));
        }
        List<SysModule> list = sysModuleService.selectSysModuleList(new SysModule());
        mmap.put("modules", list);
        return prefix + "/add";
    }
    /**
     * 校验模块代码是否存在
     */
    @PostMapping("/checkCodeUnique")
    @ResponseBody
    public String checkCodeUnique(SysModule module)
    {
        return sysModuleService.checkCodeUnique(module);
    }
    /**
     * 新增保存系统模块
     */
    @RequiresPermissions("plugs:sysModule:add")
    @Log(title = "系统模块", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysModule sysModule)
    {
        return toAjax(sysModuleService.insertSysModule(sysModule));
    }

    /**
     * 修改系统模块
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysModule sysModule = sysModuleService.selectSysModuleById(id);
        mmap.put("sysModule", sysModule);
        String dep=sysModule.getDependencie();

        List<SysModule> list = sysModuleService.selectSysModuleList(new SysModule());
        if(StringUtils.isNotEmpty(dep)){
            String[] arr= Convert.toStrArray(dep);
            if(arr!=null&&arr.length>0){
                list.stream().forEach(a->{
                    for(String s:arr){
                        if(s.equals(a.getId().toString())){
                            a.setSelected(true);
                        }
                    }
                });
            }
        }
        mmap.put("modules", list);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统模块
     */
    @RequiresPermissions("plugs:sysModule:edit")
    @Log(title = "系统模块", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysModule sysModule)
    {
        return toAjax(sysModuleService.updateSysModule(sysModule));
    }

    /**
     * 删除
     */
    @RequiresPermissions("plugs:sysModule:remove")
    @Log(title = "系统模块", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        return toAjax(sysModuleService.deleteSysModuleById(id));
    }

    /**
     * 选择系统模块树
     */
    @GetMapping(value = { "/selectSysModuleTree/{id}", "/selectSysModuleTree/" })
    public String selectSysModuleTree(@PathVariable(value = "id", required = false) Long id, ModelMap mmap)
    {
        if (StringUtils.isNotNull(id))
        {
            mmap.put("sysModule", sysModuleService.selectSysModuleById(id));
        }
        return prefix + "/tree";
    }

    /**
     * 加载系统模块树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = sysModuleService.selectSysModuleTree();
        return ztrees;
    }

    @RequiresPermissions("plugs:sysModule:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysModule sysModule)
    {
        return toAjax(sysModuleService.changeStatus(sysModule));
    }
}
