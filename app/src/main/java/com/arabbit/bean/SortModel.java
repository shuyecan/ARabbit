package com.arabbit.bean;

import com.arabbit.entity.GetSchoolListEntity;

import java.util.List;

public class SortModel {
    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母
    private int status;  //显示状态

    public List<GetSchoolListEntity> entitys;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<GetSchoolListEntity> getEntity() {
        return entitys;
    }

    public void setEntity(List<GetSchoolListEntity> entity) {
        this.entitys = entity;
    }
}
