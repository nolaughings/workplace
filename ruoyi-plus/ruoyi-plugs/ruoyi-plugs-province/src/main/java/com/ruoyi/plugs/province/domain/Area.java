package com.ruoyi.plugs.province.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class Area extends BaseEntity {

    private String id;

    private String name;

    private Integer selected;

    private Integer disabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", selected=" + selected +
                ", disabled=" + disabled +
                '}';
    }
}
