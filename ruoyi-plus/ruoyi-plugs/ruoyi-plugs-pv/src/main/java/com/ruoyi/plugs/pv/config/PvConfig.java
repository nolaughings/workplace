package com.ruoyi.plugs.pv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PvConfig {

    public static String scope;

    public static String pvUrls;

    public static Integer pvCacheMinute;

    public static String serverContextPath;
    public static String getScope() {
        return scope;
    }
    @Value("${plugs.pv.scope}")
    public void setScope(String scope) {
        PvConfig.scope = scope;
    }

    public static String getPvUrls() {
        return pvUrls;
    }
    @Value("${plugs.pv.urls}")
    public void setPvUrls(String pvUrls) {
        PvConfig.pvUrls = pvUrls;
    }

    public static Integer getPvCacheMinute() {
        return pvCacheMinute;
    }
    @Value("${plugs.pv.cacheMinute}")
    public void setPvCacheMinute(Integer pvCacheMinute) {
        PvConfig.pvCacheMinute = pvCacheMinute;
    }

    public static String getServerContextPath() {
        return serverContextPath;
    }
    @Value("${plugs.pv.serverContextPath}")
    public void setServerContextPath(String serverContextPath) {
        PvConfig.serverContextPath = serverContextPath;
    }
}
