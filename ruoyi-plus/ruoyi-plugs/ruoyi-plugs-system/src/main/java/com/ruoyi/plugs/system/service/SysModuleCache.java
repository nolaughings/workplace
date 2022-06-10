package com.ruoyi.plugs.system.service;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.ruoyi.plugs.system.domain.SysModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SysModuleCache implements ApplicationRunner {

    @Autowired
    private ISysModuleService sysModuleService;


    public  Cache<String, SysModule> MODULE_CACHE= CacheUtil.newLRUCache(100);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*List<SysModule> list=  sysModuleService.selectSysModuleList(new SysModule());
        for(SysModule module:list){
            MODULE_CACHE.put(module.getCode(),module);
        }*/
    }
}
