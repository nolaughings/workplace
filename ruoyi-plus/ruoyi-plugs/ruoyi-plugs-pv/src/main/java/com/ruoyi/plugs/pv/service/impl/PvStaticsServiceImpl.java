package com.ruoyi.plugs.pv.service.impl;

import com.ruoyi.plugs.pv.domain.PvCount;
import com.ruoyi.plugs.pv.mapper.PvStaticsMapper;
import com.ruoyi.plugs.pv.service.IPvStaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PVService业务层处理
 *
 * @author wujiyue
 * @date 2019-11-29
 */
@Service
public class PvStaticsServiceImpl implements IPvStaticsService
{
    @Autowired
    private PvStaticsMapper pvStaticsMapper;
    @Override
    public int insertPvCount(PvCount pvCount) {
        return pvStaticsMapper.insertPvCount(pvCount);
    }

    @Override
    public int updatePvCount(PvCount pvCount) {
        return pvStaticsMapper.updatePvCount(pvCount);
    }

    @Override
    public int selectTotalCount() {
        return pvStaticsMapper.selectTotalCount();
    }

    @Override
    public int selectCountByDay(String day) {
        return pvStaticsMapper.selectCountByDay(day);
    }

    @Override
    public PvCount getPvCountByDay(String day) {
        return pvStaticsMapper.getPvCountByDay(day);
    }

    @Override
    public int selectUserCount() {
        return pvStaticsMapper.selectUserCount();
    }

    @Override
    public int selectArticlesCount() {
        return pvStaticsMapper.selectArticlesCount();
    }

    @Override
    public int selectTodayCount() {
        return pvStaticsMapper.selectTodayCount();
    }

    @Override
    public int selectWeekCount() {
        return pvStaticsMapper.selectWeekCount();
    }
}
