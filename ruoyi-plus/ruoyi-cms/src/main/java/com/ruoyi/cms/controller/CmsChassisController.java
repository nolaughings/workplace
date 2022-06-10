package com.ruoyi.cms.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.cms.domain.CmsChassis;
import com.ruoyi.cms.service.ICmsChassisService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 底盘Controller
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Controller
@RequestMapping("/cms/chassis")
public class CmsChassisController extends BaseController
{
    private String prefix = "cms/chassis";

    @Autowired
    private ICmsChassisService cmsChassisService;

    @RequiresPermissions("cms:chassis:view")
    @GetMapping()
    public String chassis()
    {
        return prefix + "/chassis";
    }

    /**
     * 查询底盘列表
     */
    @RequiresPermissions("cms:chassis:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CmsChassis cmsChassis)
    {
        startPage();
        List<CmsChassis> list = cmsChassisService.selectCmsChassisList(cmsChassis);
        return getDataTable(list);
    }

    /**
     * 导出底盘列表
     */
    @RequiresPermissions("cms:chassis:export")
    @Log(title = "底盘", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CmsChassis cmsChassis)
    {
        List<CmsChassis> list = cmsChassisService.selectCmsChassisList(cmsChassis);
        ExcelUtil<CmsChassis> util = new ExcelUtil<CmsChassis>(CmsChassis.class);
        return util.exportExcel(list, "底盘数据");
    }

    /**
     * 新增底盘
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存底盘
     */
    @RequiresPermissions("cms:chassis:add")
    @Log(title = "底盘", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CmsChassis cmsChassis)
    {
        return toAjax(cmsChassisService.insertCmsChassis(cmsChassis));
    }

    /**
     * 修改底盘
     */
    @GetMapping("/edit/{chassisId}")
    public String edit(@PathVariable("chassisId") Long chassisId, ModelMap mmap)
    {
        CmsChassis cmsChassis = cmsChassisService.selectCmsChassisByChassisId(chassisId);
        mmap.put("cmsChassis", cmsChassis);
        return prefix + "/edit";
    }

    /**
     * 修改保存底盘
     */
    @RequiresPermissions("cms:chassis:edit")
    @Log(title = "底盘", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CmsChassis cmsChassis)
    {
        return toAjax(cmsChassisService.updateCmsChassis(cmsChassis));
    }

    /**
     * 删除底盘
     */
    @RequiresPermissions("cms:chassis:remove")
    @Log(title = "底盘", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(cmsChassisService.deleteCmsChassisByChassisIds(ids));
    }
}
