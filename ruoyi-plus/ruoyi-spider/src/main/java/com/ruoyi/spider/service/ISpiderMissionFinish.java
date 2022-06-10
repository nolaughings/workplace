package com.ruoyi.spider.service;

import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ISpiderMissionFinish {
    void onFinish(CopyOnWriteArrayList<LinkedHashMap<String, Object>> datas);
}
