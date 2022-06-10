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
import com.ruoyi.cms.domain.CmsRobot;
import com.ruoyi.cms.service.ICmsRobotService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 人工智能机器人Controller
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Controller
@RequestMapping("/cms/robot")
public class CmsRobotController extends BaseController
{
    private String prefix = "cms/robot";

    @Autowired
    private ICmsRobotService cmsRobotService;

    @RequiresPermissions("cms:robot:view")
    @GetMapping()
    public String robot()
    {
        return prefix + "/robot";
    }

    /**
     * 查询人工智能机器人列表
     */
    @RequiresPermissions("cms:robot:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CmsRobot cmsRobot)
    {
        startPage();
        List<CmsRobot> list = cmsRobotService.selectCmsRobotList(cmsRobot);
        return getDataTable(list);
    }

    /**
     * 导出人工智能机器人列表
     */
    @RequiresPermissions("cms:robot:export")
    @Log(title = "人工智能机器人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CmsRobot cmsRobot)
    {
        List<CmsRobot> list = cmsRobotService.selectCmsRobotList(cmsRobot);
        ExcelUtil<CmsRobot> util = new ExcelUtil<CmsRobot>(CmsRobot.class);
        return util.exportExcel(list, "人工智能机器人数据");
    }

    /**
     * 新增人工智能机器人
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存人工智能机器人
     */
    @RequiresPermissions("cms:robot:add")
    @Log(title = "人工智能机器人", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CmsRobot cmsRobot)
    {
        return toAjax(cmsRobotService.insertCmsRobot(cmsRobot));
    }

    /**
     * 修改人工智能机器人
     */
    @GetMapping("/edit/{robotId}")
    public String edit(@PathVariable("robotId") Long robotId, ModelMap mmap)
    {
        CmsRobot cmsRobot = cmsRobotService.selectCmsRobotByRobotId(robotId);
        mmap.put("cmsRobot", cmsRobot);
        return prefix + "/edit";
    }

    /**
     * 修改保存人工智能机器人
     */
    @RequiresPermissions("cms:robot:edit")
    @Log(title = "人工智能机器人", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CmsRobot cmsRobot)
    {
        return toAjax(cmsRobotService.updateCmsRobot(cmsRobot));
    }

    /**
     * 删除人工智能机器人
     */
    @RequiresPermissions("cms:robot:remove")
    @Log(title = "人工智能机器人", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(cmsRobotService.deleteCmsRobotByRobotIds(ids));
    }
}
