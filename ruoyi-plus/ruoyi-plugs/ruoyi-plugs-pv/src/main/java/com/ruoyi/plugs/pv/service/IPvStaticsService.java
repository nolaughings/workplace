package com.ruoyi.plugs.pv.service;

import com.ruoyi.plugs.pv.domain.PvCount;

/**
 * PVCountService接口
 *
 * @author wujiyue
 * @date 2019-11-29
 */
public interface IPvStaticsService
{

    public int insertPvCount(PvCount pvCount);
    public int updatePvCount(PvCount pvCount);
    public int selectTotalCount();

    public int selectCountByDay(String day);

    public PvCount getPvCountByDay(String day);

    public int selectUserCount();
    public int selectArticlesCount();
    public int selectTodayCount();
    public int selectWeekCount();
}
