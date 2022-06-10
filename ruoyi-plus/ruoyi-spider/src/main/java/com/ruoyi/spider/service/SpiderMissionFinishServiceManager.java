package com.ruoyi.spider.service;

import com.ruoyi.common.utils.spring.SpringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpiderMissionFinishServiceManager {

    @Resource
    List<ISpiderMissionFinish> spiderMissionFinishes=new ArrayList<>();

    public void setSpiderMissionFinishes(List<ISpiderMissionFinish> spiderMissionFinishes) {
        this.spiderMissionFinishes = spiderMissionFinishes;
    }

    private static List<String> spiderMissionFinishServiceList=new ArrayList<>();
    @PostConstruct
    public void init() {
        if (CollectionUtils.isNotEmpty(spiderMissionFinishes)) {
            for (ISpiderMissionFinish finish : spiderMissionFinishes) {
                Class cls = finish.getClass();
                Object instance = null;
                if (!cls.getName().contains("proxy")) {
                    instance = SpringUtils.getBean(cls);
                    if (instance instanceof ISpiderMissionFinish) {
                        spiderMissionFinishServiceList.add(instance.getClass().getSimpleName());
                    }
                }
            }
        }
    }

    public static List<String> getSpiderMissionFinishServiceList() {
        return spiderMissionFinishServiceList;
    }
}
