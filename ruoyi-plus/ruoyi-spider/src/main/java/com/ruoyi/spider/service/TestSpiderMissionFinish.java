package com.ruoyi.spider.service;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TestSpiderMissionFinish implements ISpiderMissionFinish{
    @Override
    public void onFinish(CopyOnWriteArrayList<LinkedHashMap<String, Object>> datas) {

    }
}
