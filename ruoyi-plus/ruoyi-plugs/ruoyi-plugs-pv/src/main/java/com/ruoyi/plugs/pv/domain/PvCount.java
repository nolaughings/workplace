package com.ruoyi.plugs.pv.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

public class PvCount extends BaseEntity {

    private Long id;

    private Date day;

    private Long count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PvCount{" +
                "id=" + id +
                ", day=" + day +
                ", count=" + count +
                '}';
    }
}
