package com.ruoyi.plugs.blog.job;

import com.ruoyi.plugs.blog.controller.BlogController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 方便手工清除前台缓存
 */
@Component("clearBannerCacheJob")
public class ClearBannerCacheJob {

    @Autowired
    BlogController blogController;

    public void clearBannerCache()
    {
        System.out.println("==========触发清空banner缓存===========");
        blogController.clearBannerCache();
    }
}
