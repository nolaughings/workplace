package com.ruoyi.plugs.framework.shiro.web;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.plugs.common.utils.path.impl.AntPathMatcher;
import com.ruoyi.plugs.framework.config.AnnoFilterChainDefinition;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginFilter extends UserFilter {

    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private static List<String> anonList= AnnoFilterChainDefinition.anonList;//匿名访问列表

    private boolean urlMatch(String servletPath, List<String> paths){
        for (String path:paths) {
            String uriPattern = path.trim();
            // 支持ANT表达式
            if (antPathMatcher.match(uriPattern, servletPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if(request instanceof HttpServletRequest){
            String uri=((HttpServletRequest) request).getRequestURI();
            if(urlMatch(uri,anonList)||isStaticUrl(uri)){
                return true;//匿名访问放行
            }
        }

        SysUser user = ShiroUtils.getSysUser();
        if (user == null)
        {
            return false;
        }
        return true ;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject != null)
        {
            subject.logout();
        }

        saveRequestAndRedirectToLogin(request, response);

        return false;
    }
    private boolean isStaticUrl(String uri){
        if(uri.endsWith(".js")||uri.endsWith(".css")||uri.endsWith(".jpg")||uri.endsWith(".png")||uri.endsWith(".ico")||uri.endsWith(".ttf")||uri.endsWith(".woff")||uri.endsWith(".woff2")){
            return true;
        }
        return false;
    }
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse rep=(HttpServletResponse)response;
        if (ServletUtils.isAjaxRequest(req))
        {
            ServletUtils.renderString(rep, "{\"code\":\"999\",\"msg\":\"未登录或登录超时,请重新登录!\"}");
        }
        else{
            WebUtils.issueRedirect(request, response, loginUrl);
        }
    }
}
