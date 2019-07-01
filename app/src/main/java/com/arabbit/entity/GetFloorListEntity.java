package com.arabbit.entity;

/**
 * Created by net8 on 2017/8/1.
 */

public class GetFloorListEntity {

    /**
     * floor_id : 1
     * floor_name : 一楼
     * create_time : 1
     */

    private int floor_id;
    private String floor_name;
    private int create_time;

    public int getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(int floor_id) {
        this.floor_id = floor_id;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public void setFloor_name(String floor_name) {
        this.floor_name = floor_name;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
