package com.ruoyi.plugs.common.utils;

import com.ruoyi.plugs.common.constant.Constants;
import com.ruoyi.common.core.text.Convert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端工具类
 *
 * @author ruoyi
 */
public class ServletUtils
{
    /**
     * 获取String参数
     */
    public static String getParameter(String name)
    {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue)
    {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name)
    {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue)
    {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse()
    {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession()
    {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是Ajax异步请求
     *
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1)
        {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
        {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml"))
        {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.inStringIgnoreCase(ajax, "json", "xml"))
        {
            return true;
        }
        return false;
    }


    public static void setCookieUid(String uid){
        HttpServletResponse response= ServletUtils.getResponse();
        response.setHeader("Access-Control-Allow-Credentials","true");
        CookieUtils.setCookie(response, Constants.FRONT_COOKIE_UID, uid, -1);

    }
    public static void deleteCookieUid(){
        HttpServletResponse response= ServletUtils.getResponse();
        response.setHeader("Access-Control-Allow-Credentials","true");
        CookieUtils.setCookie(response, Constants.FRONT_COOKIE_UID, "0", 0);

    }
    /**
     * 前台手机短信登录成功后设置cookie
     * @param phone
     * @param rememberMe
     */
    public static void setSmsLoginCookieFront(String phone, Boolean rememberMe){
       /* HttpServletResponse response= ServletUtils.getResponse();
        response.setHeader("Access-Control-Allow-Credentials","true");
        CookieUtils.setCookie(response, Constants.FRONT_COOKIE_USERNAME_PHONE, phone, Constants.SECONDS_7_DAYS);
        if (rememberMe) {//记住密码
            CookieUtils.setCookie(response, Constants.FRONT_COOKIE_REMEMBERME_PHONE, "1", Constants.SECONDS_7_DAYS);
        } else {
            CookieUtils.setCookie(response, Constants.FRONT_COOKIE_REMEMBERME_PHONE, "0", Constants.SECONDS_7_DAYS);
        }*/
    }

    public static Map<String, String> getMap(HttpServletRequest req) {
        Map<String, String> map=new HashMap<String, String>();
        Enumeration enu = req.getParameterNames();
        while(enu.hasMoreElements()) {
            String paramName = (String)enu.nextElement();
            String[] paramValues = req.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }
    /**
     * 定义移动端请求的所有可能类型
     */
    private final static String[] agent = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };
    /**
     * 判断User-Agent 是不是来自于手机
     */
    public static boolean checkAgentIsMobile(String ua)
    {
        boolean flag = false;
        if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;")))
        {
            // 排除 苹果桌面系统
            if (!ua.contains("Windows NT") && !ua.contains("Macintosh"))
            {
                for (String item : agent)
                {
                    if (ua.contains(item))
                    {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }
}