package com.arabbit.entity;

/**
 * Created by net8 on 2017/8/1.
 */

public class GetBuildingListEntity {

    /**
     * b_id : 1
     * building_name : 大礼堂
     * school_id : 1
     * is_seat : 1
     * type : 大礼堂
     * create_time : 1
     */

    private int b_id;
    private String building_name;
    private int school_id;
    private int is_seat;
    private String type;
    private int create_time;

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getIs_seat() {
        return is_seat;
    }

    public void setIs_seat(int is_seat) {
        this.is_seat = is_seat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
