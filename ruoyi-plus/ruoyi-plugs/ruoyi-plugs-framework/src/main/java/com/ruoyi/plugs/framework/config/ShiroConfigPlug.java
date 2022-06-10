package com.ruoyi.plugs.framework.config;

import com.google.common.collect.Lists;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.config.ShiroConfig;
import com.ruoyi.framework.shiro.realm.UserRealm;
import com.ruoyi.plugs.framework.shiro.CustomModularRealmAuthenticator;
import com.ruoyi.plugs.framework.shiro.RetryLimitHashedCredentialsMatcher;
import com.ruoyi.plugs.framework.shiro.realm.NoPwdUserRealm;
import com.ruoyi.plugs.framework.shiro.web.LoginFilter;
import com.ruoyi.plugs.holiday.filter.HolidayFilter;
import com.ruoyi.plugs.pv.filter.PageStaticsFilter;
import com.ruoyi.plugs.pv.service.IPvStaticsService;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.text.ParseException;
import java.util.*;

@Configuration
public class ShiroConfigPlug extends ShiroConfig {

    @Value("${plugs.holiday.enable}")
    private boolean holidayEnabled;
    @Value("${plugs.holiday.urls}")
    private String holidayUrls;

    @Value("${plugs.pageStatics.enable}")
    public  boolean pageStaticsEnabled;
    @Value("${plugs.pageStatics.scope}")
    public  String pageStaticsScope;
    @Value("${plugs.pageStatics.urls}")
    public  String pageStaticsUrls;
    @Value("${plugs.pageStatics.cacheSeconds}")
    public  Integer pageStaticsCacheSeconds;
    @Value("${plugs.pageStatics.saveDbSeconds}")
    public  Integer pageStaticsSaveDbSeconds;
    @Value("${plugs.pageStatics.buildSiteDate}")
    public  String buildSiteDate;

    @Bean
    public NoPwdUserRealm noPwdUserRealm(){
        NoPwdUserRealm noPwdUserRealm = new NoPwdUserRealm();
        noPwdUserRealm.setCredentialsMatcher(new RetryLimitHashedCredentialsMatcher());
        noPwdUserRealm.setCacheManager(getEhCacheManager());
        return noPwdUserRealm;
    }

    /**
     * 安全管理器
     */
    @Bean
    @Override
    public SecurityManager securityManager(UserRealm userRealm)
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义模块化认证器，用于解决多realm抛出异常问题
        CustomModularRealmAuthenticator authenticator = new CustomModularRealmAuthenticator();
        // 认证策略：AtLeastOneSuccessfulStrategy(默认)，AllSuccessfulStrategy，FirstSuccessfulStrategy
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        // 加入realms
        List<Realm> realms = new ArrayList<>();
        realms.add(userRealm);
        realms.add(noPwdUserRealm());
        authenticator.setRealms(realms);
        securityManager.setAuthenticator(authenticator);
        // 设置realms
        securityManager.setRealms(realms);
        // 记住我
        securityManager.setRememberMeManager(rememberMeManager());
        // 注入缓存管理器;
        securityManager.setCacheManager(getEhCacheManager());
        // session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    @Override
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        List<String> anonList= AnnoFilterChainDefinition.anonList;//匿名访问列表
        for(String s:anonList){
            filterChainDefinitionMap.put(s,"anon");
        }

        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/login", "anon,captchaValidate");
        filterChainDefinitionMap.put("/admin/login", "anon,captchaValidate");

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        if(holidayEnabled){
            filters.put("holiday", holidayFilter());
        }
        if(pageStaticsEnabled){
            filters.put("pageStatics", pageStaticsFilter());
       }
        filters.put("user", loginFilter());
        filters.put("onlineSession", onlineSessionFilter());
        filters.put("syncOnlineSession", syncOnlineSessionFilter());
        filters.put("captchaValidate", captchaValidateFilter());
        filters.put("kickout", kickoutSessionFilter());
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        String filterStr="user,kickout,onlineSession,syncOnlineSession";
        if(holidayEnabled){
            filterStr="holiday,"+filterStr;
        }
        if(pageStaticsEnabled){
            filterStr="pageStatics,"+filterStr;
        }
        filterChainDefinitionMap.put("/**", filterStr);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public LoginFilter loginFilter()
    {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setLoginUrl(loginUrl);
        return loginFilter;
    }
    @Bean
    @ConditionalOnProperty(value = "plugs.holiday.enable",havingValue = "1",matchIfMissing = false)
    public HolidayFilter holidayFilter(){
        HolidayFilter holidayFilter=new HolidayFilter();
        String[] arr= Convert.toStrArray(holidayUrls);
        List<String> urls= Lists.newArrayList(arr);
        holidayFilter.setUrls(urls);
        return holidayFilter;
    }
    @Bean
    @ConditionalOnProperty(value = "plugs.pageStatics.enable",havingValue = "1",matchIfMissing = false)
    public PageStaticsFilter pageStaticsFilter(){
        int c= SpringUtils.getBean(IPvStaticsService.class).selectTotalCount();
        PageStaticsFilter pageStaticsFilter=new PageStaticsFilter(pageStaticsCacheSeconds,pageStaticsSaveDbSeconds,c);
        pageStaticsFilter.setTotalPvCount(c);
        try {
            Date d= DateUtils.parseDate(buildSiteDate,"yyyy-MM-dd");
            pageStaticsFilter.setBuildSiteDate(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pageStaticsFilter.setScope(pageStaticsScope);
        String[] arr= Convert.toStrArray(pageStaticsUrls);
        List<String> urls= Lists.newArrayList(arr);
        pageStaticsFilter.setUrls(urls);
        return pageStaticsFilter;
    }
}
