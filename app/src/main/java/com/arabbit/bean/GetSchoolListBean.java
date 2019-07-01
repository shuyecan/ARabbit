package com.arabbit.bean;

/**
 * Created by net8 on 2017/8/1.
 */

public class GetSchoolListBean {

    /**
     * school_id : 4
     * school_name : 中山大学
     * create_time : 1
     */

    private int school_id;
    private String school_name;
    private int create_time;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
