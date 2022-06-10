package com.ruoyi.cms.controller;

import com.ruoyi.cms.domain.CmsChassis;
import com.ruoyi.cms.domain.CmsNews;
import com.ruoyi.cms.domain.CmsRobot;
import com.ruoyi.cms.service.ICmsChassisService;
import com.ruoyi.cms.service.ICmsNewsService;
import com.ruoyi.cms.service.ICmsRobotService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cms/test")
public class TestsController extends BaseController {

    private String prefix = "cms/test";

    @Autowired
    private ICmsNewsService cmsNewsService;
    @Autowired
    private ICmsRobotService cmsRobotService;
    @Autowired
    private ICmsChassisService cmsChassisService;

    @RequiresPermissions("cms:test:view")
    @GetMapping()
    public String news(CmsNews cmsNews, ModelMap modelMap)
    {
        List<CmsNews> list = cmsNewsService.selectCmsNewsList(cmsNews);
        modelMap.put("News",list);
        return prefix + "/test";
    }

    @RequiresPermissions("cms:test:product")
    @GetMapping("/product")
    public String list(CmsRobot cmsRobot , CmsChassis cmsChassis , ModelMap modelMap)
    {
        List<CmsRobot> list = cmsRobotService.selectCmsRobotList(cmsRobot);
        List<CmsChassis> chassislist = cmsChassisService.selectCmsChassisList(cmsChassis);
        modelMap.put("robot",list);
        modelMap.put("chassis",chassislist);
        return prefix + "/product";
    }

    @RequestMapping ("/robot_detail/{robotId}")
    public String robot_detail(@PathVariable Long robotId, Model model){
        CmsRobot cmsRobot = cmsRobotService.selectCmsRobotByRobotId(robotId);
        model.addAttribute("robotdetail",cmsRobot);
        System.out.println(model);
        return prefix + "/robotdetail";
    }

    @RequestMapping ("/chassis_detail/{chassisId}")
    public String chassis_detail(@PathVariable Long chassisId, Model model){
        CmsChassis cmsChassis = cmsChassisService.selectCmsChassisByChassisId(chassisId);
        model.addAttribute("chassisdetail",cmsChassis);
        return prefix + "/chassisdetail";
    }

}
