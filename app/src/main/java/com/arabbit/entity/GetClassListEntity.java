package com.arabbit.entity;

/**
 * Created by net8 on 2017/8/1.
 */

public class GetClassListEntity {

    /**
     * class_id : 3
     * class_name : 101教室
     * create_time : 0
     */

    private int class_id;
    private String class_name;
    private int create_time;

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
