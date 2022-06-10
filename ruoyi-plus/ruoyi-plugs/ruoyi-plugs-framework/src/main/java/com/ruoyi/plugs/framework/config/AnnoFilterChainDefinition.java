package com.ruoyi.plugs.framework.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 匿名访问列表
 */
public class AnnoFilterChainDefinition {


    public static List<String> anonList=new ArrayList<>();
    static {
        // 对静态资源设置匿名访问
        anonList.add("/favicon.ico");
        anonList.add("/ruoyi.png**");
        anonList.add("/css/**");
        anonList.add("/docs/**");
        anonList.add("/fonts/**");
        anonList.add("/img/**");
        anonList.add("/ajax/**");
        anonList.add("/js/**");
        anonList.add("/ruoyi/**");
        anonList.add("/druid/**");
        anonList.add("/templates/**");
        anonList.add("/segments/**");
        anonList.add("/captcha/captchaImage**");
        anonList.add("/decoratefront/**");
        anonList.add("/decorate/*.html");
        anonList.add("/decorate/zt/*.html");
        anonList.add("/");
        anonList.add("/profile/**");
        anonList.add("/profile/upload/**");
        anonList.add("/blog/**");
        anonList.add("/plugs/page/view");
        anonList.add("/images/**");
        anonList.add("/login/theme/**");
        anonList.add("/login/sms");
        anonList.add("/login/sms/send");
        anonList.add("/front/**");
        anonList.add("/register");
        anonList.add("/register/sendSms");
        anonList.add("/register/sendEmail");
        anonList.add("/resetpwd");
        anonList.add("/resetpwd/sendCode");
        anonList.add("/login/third/**");
        anonList.add("/logout");
        anonList.add("/login");
        anonList.add("/admin/login");
        anonList.add("/login/front");
        anonList.add("/forum/**");
        anonList.add("/login/frame");
        anonList.add("/checkPhone");
        anonList.add("/checkAccount");
        anonList.add("/forum/u/**");
        anonList.add("/nav/getUserInfo");
        anonList.add("/cms/category/treeDataWithArticle");
        anonList.add("/cms/album/getAlbum");
        anonList.add("/aboutUs");
        anonList.add("/userAgreement");
        anonList.add("/contactUs");
        anonList.add("/help");
        anonList.add("/violationClaim");
        anonList.add("/policy");
        anonList.add("/rule");
        anonList.add("/login");
        anonList.add("/resourceStore/login");
        anonList.add("/register");
        anonList.add("/resetpwd");

        anonList.add("/resourceStore/pages/**");
        anonList.add("/score/scoreRule");
        anonList.add("/scoreStore");
        anonList.add("/scoreStore/*.html");
        anonList.add("/badge");
        anonList.add("/badge/*.html");
        anonList.add("/thirdLogin/**");
        anonList.add("/document");
        anonList.add("/gitee/webhook/**");

        anonList.add("/segmentStore");
        anonList.add("/segmentStore/*.html");
        anonList.add("/preview/segment/**");

        anonList.add("/templateStore");
        anonList.add("/templateStore/*.html");
        anonList.add("/preview/template/**");

        anonList.add("/couponCenter");
        anonList.add("/couponCenter/*.html");
        anonList.add("/resourceStore/donate");
        anonList.add("/resourceStore/donateUsers");
        anonList.add("/resourceStore/wish");
        anonList.add("/resourceStore/anli");
        anonList.add("/resourceStore/anli/*.html");
        anonList.add("/score/rule");
        anonList.add("/scores");
        anonList.add("/plugs/scoreLotteryRecord/elseRecord");

        anonList.add("/resourceStore/personVip");
        anonList.add("/resourceStore/companyVip");

        anonList.add("/codeStore");
        anonList.add("/codeStore/*.html");
        anonList.add("/courseStore");
        anonList.add("/courseStore/*.html");

        anonList.add("/picStore/wallPater");

        anonList.add("/audioEffectStore");
        anonList.add("/audioEffectStore/*.html");
        anonList.add("/docStore");
        anonList.add("/docStore/*.html");

    }
}
