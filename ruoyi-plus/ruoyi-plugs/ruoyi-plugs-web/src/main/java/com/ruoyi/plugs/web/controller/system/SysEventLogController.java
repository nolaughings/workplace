package com.ruoyi.plugs.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.plugs.framework.event.ApplicationEventDefined;
import com.ruoyi.plugs.system.domain.SysEventLog;
import com.ruoyi.plugs.system.service.ISysEventLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统事件日志Controller
 *
 * @author wujiyue
 * @date 2021-09-15
 */
@Controller
@RequestMapping("/system/sysEventLog")
public class SysEventLogController extends BaseController
{
    private String prefix = "plugs/system/sysEventLog";

    @Autowired
    private ISysEventLogService sysEventLogService;

    @RequiresPermissions("system:sysEventLog:view")
    @GetMapping()
    public String sysEventLog(ModelMap mmap)
    {
        List<Map> list=new ArrayList<>();
        Map temp=null;
        ApplicationEventDefined[] applicationEventDefineds= ApplicationEventDefined.values();
        for(ApplicationEventDefined applicationEventDefined:applicationEventDefineds){
            temp=new HashMap();
            temp.put("code",applicationEventDefined.getValue());
            temp.put("text",applicationEventDefined.getDescription());
            list.add(temp);
        }
        mmap.addAttribute("events",list);
        return prefix + "/sysEventLog";
    }

    /**
     * 查询系统事件日志列表
     */
    @RequiresPermissions("system:sysEventLog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysEventLog sysEventLog)
    {
        startPage();
        List<SysEventLog> list = sysEventLogService.selectSysEventLogList(sysEventLog);
        return getDataTable(list);
    }

    /**
     * 新增系统事件日志
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存系统事件日志
     */
    @RequiresPermissions("system:sysEventLog:add")
    @Log(title = "系统事件日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysEventLog sysEventLog)
    {
        return toAjax(sysEventLogService.insertSysEventLog(sysEventLog));
    }

    /**
     * 修改系统事件日志
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysEventLog sysEventLog = sysEventLogService.selectSysEventLogById(id);
        mmap.put("sysEventLog", sysEventLog);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统事件日志
     */
    @RequiresPermissions("system:sysEventLog:edit")
    @Log(title = "系统事件日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysEventLog sysEventLog)
    {
        return toAjax(sysEventLogService.updateSysEventLog(sysEventLog));
    }

    /**
     * 删除系统事件日志
     */
    @RequiresPermissions("system:sysEventLog:remove")
    @Log(title = "系统事件日志", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysEventLogService.deleteSysEventLogByIds(ids));
    }
}
