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
import com.ruoyi.cms.domain.CmsNews;
import com.ruoyi.cms.service.ICmsNewsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新闻Controller
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Controller
@RequestMapping("/cms/news")
public class CmsNewsController extends BaseController
{
    private String prefix = "cms/news";

    @Autowired
    private ICmsNewsService cmsNewsService;

    @RequiresPermissions("cms:news:view")
    @GetMapping()
    public String news()
    {
        return prefix + "/news";
    }

    /**
     * 查询新闻列表
     */
    @RequiresPermissions("cms:news:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CmsNews cmsNews)
    {
        startPage();
        List<CmsNews> list = cmsNewsService.selectCmsNewsList(cmsNews);
        return getDataTable(list);
    }

    /**
     * 导出新闻列表
     */
    @RequiresPermissions("cms:news:export")
    @Log(title = "新闻", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CmsNews cmsNews)
    {
        List<CmsNews> list = cmsNewsService.selectCmsNewsList(cmsNews);
        ExcelUtil<CmsNews> util = new ExcelUtil<CmsNews>(CmsNews.class);
        return util.exportExcel(list, "新闻数据");
    }

    /**
     * 新增新闻
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存新闻
     */
    @RequiresPermissions("cms:news:add")
    @Log(title = "新闻", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CmsNews cmsNews)
    {
        return toAjax(cmsNewsService.insertCmsNews(cmsNews));
    }

    /**
     * 修改新闻
     */
    @GetMapping("/edit/{newsId}")
    public String edit(@PathVariable("newsId") Long newsId, ModelMap mmap)
    {
        CmsNews cmsNews = cmsNewsService.selectCmsNewsByNewsId(newsId);
        mmap.put("cmsNews", cmsNews);
        return prefix + "/edit";
    }

    /**
     * 修改保存新闻
     */
    @RequiresPermissions("cms:news:edit")
    @Log(title = "新闻", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CmsNews cmsNews)
    {
        return toAjax(cmsNewsService.updateCmsNews(cmsNews));
    }

    /**
     * 删除新闻
     */
    @RequiresPermissions("cms:news:remove")
    @Log(title = "新闻", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(cmsNewsService.deleteCmsNewsByNewsIds(ids));
    }
}
