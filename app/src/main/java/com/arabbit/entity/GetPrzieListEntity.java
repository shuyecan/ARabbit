package com.arabbit.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/8.
 */

public class GetPrzieListEntity implements Serializable {

    private int przie_id;
    private String gfname;
    private String gfinfo;
    private int gfnum;
    private String gfcontent;
    private int gfstart;
    private int gfend;
    private int gffar;
    private int user_id;
    private int school_id;
    private int term_id;
    private int create_time;
    private int hit;
    private int role;

    public int getPrzie_id() {
        return przie_id;
    }

    public void setPrzie_id(int gift_id) {
        this.przie_id = przie_id;
    }

    public String getPassword() {
        return gfname;
    }

    public void setGfname(String gfname) {
        this.gfname = gfname;
    }

    public String getGfinfo() {
        return gfinfo;
    }

    public void setGfinfo(String gfinfo) {
        this.gfinfo = gfinfo;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getGfcontent() {
        return gfcontent;
    }

    public void setGfcontent(String gfcontent) {
        this.gfcontent = gfcontent;
    }

    public int getGfnum() {
        return gfnum;
    }

    public void setGfnum(int gfnum) {
        this.gfnum = gfnum;
    }

    public int getGfstart() {
        return gfstart;
    }

    public void setGfstart(int gfstart) {
        this.gfstart = gfstart;
    }

    public int getGfend() {
        return gfend;
    }

    public void setGfend(int gfend) {
        this.gfend = gfend;
    }

    public int getGffar() {
        return gffar;
    }

    public void setGffar(int gffar) {
        this.gffar = gffar;
    }



}
