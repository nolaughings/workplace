package com.ruoyi.plugs.framework.shiro.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.plugs.common.utils.ServletUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.plugs.framework.shiro.token.UsernamePasswordToken;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 免密登录校验方法
 *
 * @author ruoyi
 */
@Service
public class NoPwdLoginService {

    @Autowired
    ISysUserService userService;

    public AjaxResult loginNoPwd(Long userId){
        SysUser user=userService.selectUserById(userId);
        if(user!=null){
            UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName());
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(token);
                user= ShiroUtils.getSysUser();
                ServletUtils.setCookieUid(user.getUserId().toString());
                Map<String,Object> returnMap=new HashMap<String,Object>();
                if(user!=null){
                    user=userService.selectUserById(user.getUserId());
                    Date lastDate =   user.getLoginDate();
                    if(lastDate==null){
                        returnMap.put("todayLogin",true);//今天是第一次登录
                        return AjaxResult.success("登录成功!",returnMap);
                    }
                    if(!isToday(lastDate)){
                        //不是今天的登录时间
                        returnMap.put("todayLogin",true);//今天是第一次登录
                    }else{
                        returnMap.put("todayLogin",false);//今天是第n次登录
                    }
                }
                return AjaxResult.success("登录成功!",returnMap);
            }catch (Exception ex){
                AjaxResult.error(ex.getMessage());
            }
        }
        return AjaxResult.error();
    }

    private static boolean isToday(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }
}
