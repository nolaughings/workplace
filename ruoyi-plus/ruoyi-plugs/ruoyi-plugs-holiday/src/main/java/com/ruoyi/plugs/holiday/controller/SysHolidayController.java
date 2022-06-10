package com.ruoyi.plugs.holiday.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.plugs.holiday.domain.SysHoliday;
import com.ruoyi.plugs.holiday.service.ISysHolidayService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 节假日Controller
 *
 * @author ruoyi-plus
 * @date 2021-09-30
 */
@Controller
@RequestMapping("/plugs/sysHoliday")
public class SysHolidayController extends BaseController
{
    private String prefix = "plugs/sysHoliday";

    @Autowired
    private ISysHolidayService sysHolidayService;

    @RequiresPermissions("plugs:sysHoliday:view")
    @GetMapping()
    public String sysHoliday()
    {
        return prefix + "/sysHoliday";
    }

    /**
     * 查询节假日列表
     */
    @RequiresPermissions("plugs:sysHoliday:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHoliday sysHoliday)
    {
        startPage();
        List<SysHoliday> list = sysHolidayService.selectSysHolidayList(sysHoliday);
        return getDataTable(list);
    }

    /**
     * 导出节假日列表
     */
    @RequiresPermissions("plugs:sysHoliday:export")
    @Log(title = "节假日", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHoliday sysHoliday)
    {
        List<SysHoliday> list = sysHolidayService.selectSysHolidayList(sysHoliday);
        ExcelUtil<SysHoliday> util = new ExcelUtil<SysHoliday>(SysHoliday.class);
        return util.exportExcel(list, "sysHoliday");
    }

    /**
     * 新增节假日
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存节假日
     */
    @RequiresPermissions("plugs:sysHoliday:add")
    @Log(title = "节假日", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysHoliday sysHoliday)
    {
        return toAjax(sysHolidayService.insertSysHoliday(sysHoliday));
    }

    /**
     * 修改节假日
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysHoliday sysHoliday = sysHolidayService.selectSysHolidayById(id);
        mmap.put("sysHoliday", sysHoliday);
        return prefix + "/edit";
    }

    /**
     * 修改保存节假日
     */
    @RequiresPermissions("plugs:sysHoliday:edit")
    @Log(title = "节假日", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHoliday sysHoliday)
    {
        return toAjax(sysHolidayService.updateSysHoliday(sysHoliday));
    }

    /**
     * 删除节假日
     */
    @RequiresPermissions("plugs:sysHoliday:remove")
    @Log(title = "节假日", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysHolidayService.deleteSysHolidayByIds(ids));
    }
}
