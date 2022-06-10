package com.ruoyi.plugs.thirdlogin;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.plugs.framework.shiro.service.NoPwdLoginService;
import com.ruoyi.plugs.framework.shiro.service.SysLoginServicePlug;
import com.ruoyi.plugs.system.service.ICommonService;
import com.ruoyi.plugs.thirdlogin.domain.ThirdOauth;
import com.ruoyi.plugs.thirdlogin.domain.ThirdPartyUser;
import com.ruoyi.plugs.thirdlogin.service.IThirdOauthService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public abstract class BaseThirdLoginController extends BaseController {

    @Value("${third.login.to_bind_url}")
    public  String BIND_URL;
    @Value("${front.register.deptId}")
    public  String  registerUserDeptId;//前台用户注册赋予的默认部门id
    @Value("${front.register.roleId}")
    public  String registerUserRoleId;//前台用户注册赋予的默认角色id

    @Autowired
    public ISysUserService userService;
    @Autowired
    public ICommonService commonService;
    @Autowired
    public SysPasswordService passwordService;
    @Autowired
    public IThirdOauthService thirdOauthService;
    @Autowired
    public NoPwdLoginService noPwdLoginService;
    @Autowired
    public SysLoginServicePlug sysLoginServicePlug;


    public abstract void bindSaveCallBack(SysUser user);

    public int insertThirdOauth(ThirdPartyUser thirdPartyUser, String userId){
        int n=0;
        ThirdOauth thirdOauth=new ThirdOauth();
        thirdOauth.setOpenid(thirdPartyUser.getOpenid());
        thirdOauth.setLoginType(thirdPartyUser.getProvider());
        thirdOauth.setBindTime(DateUtils.getTime());
        thirdOauth.setUserId(userId);
        thirdOauth.setAccessToken(thirdPartyUser.getAccessToken());
        ThirdOauth form=new ThirdOauth();
        form.setOpenid(thirdPartyUser.getOpenid());
        form.setLoginType(thirdPartyUser.getProvider());
        form.setUserId(userId);
        List<ThirdOauth>  list = thirdOauthService.selectThirdOauthList(form);
        if(CollectionUtils.isEmpty(list)){
           n= thirdOauthService.insertThirdOauth(thirdOauth);
        }
        return n;
    }


}
