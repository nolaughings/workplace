package com.ruoyi.plugs.pv.controller;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.plugs.pv.domain.Pv;
import com.ruoyi.plugs.pv.service.IPvService;
import com.ruoyi.plugs.pv.service.PVQueueService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * PVController
 *
 * @author wujiyue
 * @date 2019-11-29
 */
@Controller
@ConditionalOnProperty(value = "plugs.pv.enable",havingValue = "1",matchIfMissing = false)
public class PvController extends BaseController
{
    @Value("${plugs.pv.enable}")
    private boolean pvEnabled;

    private String prefix = "plugs/pv";

    @Autowired
    private IPvService pvService;
    @Autowired
    private PVQueueService pvQueueService;

    @RequiresPermissions("plugs:pv:view")
    @GetMapping("/plugs/pv")
    public String pv()
    {
        return prefix + "/pv";
    }

    /**
     * 查询PV列表
     */
    @RequiresPermissions("plugs:pv:list")
    @PostMapping("/plugs/pv/list")
    @ResponseBody
    public TableDataInfo list(Pv pv)
    {
        startPage();
        List<Pv> list = pvService.selectPvList(pv);
        return getDataTable(list);
    }

    /**
     * 新增PV
     */
    @GetMapping("/plugs/pv/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存PV
     */
    @RequiresPermissions("plugs:pv:add")
    @Log(title = "PV", businessType = BusinessType.INSERT)
    @PostMapping("/plugs/pv/add")
    @ResponseBody
    public AjaxResult addSave(Pv pv)
    {
        return toAjax(pvService.insertPv(pv));
    }

    /**
     * 修改PV
     */
    @GetMapping("/plugs/pv/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Pv pv = pvService.selectPvById(id);
        mmap.put("pv", pv);
        return prefix + "/edit";
    }

    /**
     * 修改保存PV
     */
    @RequiresPermissions("plugs:pv:edit")
    @Log(title = "PV", businessType = BusinessType.UPDATE)
    @PostMapping("/plugs/pv/edit")
    @ResponseBody
    public AjaxResult editSave(Pv pv)
    {
        return toAjax(pvService.updatePv(pv));
    }

    /**
     * 删除PV
     */
    @RequiresPermissions("plugs:pv:remove")
    @Log(title = "PV", businessType = BusinessType.DELETE)
    @PostMapping( "/plugs/pv/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(pvService.deletePvByIds(ids));
    }


    @PostMapping( "/plugs/page/view")
    @ResponseBody
    public AjaxResult pv(Pv pv, HttpServletRequest request)
    {
        if(pvEnabled){
            pvQueueService.pushPvQueue(request,pv);
        }
        return success();
    }

}
