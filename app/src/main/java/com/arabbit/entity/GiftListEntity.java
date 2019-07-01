package com.arabbit.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/24.
 */

public class GiftListEntity {

    private int total_page;
    private List<GiftListEntity.ListsBean> lists;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<GiftListEntity.ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<GiftListEntity.ListsBean> lists) {
        this.lists = lists;
    }
    public static class ListsBean {

        private int seat_id;
        private int user_id;
        private int school_id;
        private String no;
        private String name;
        private String school_name;
        private String city;
        private int create_time;
        private int starttime;
        private int term_id;
        private int ava_num;//已经领取礼品的数量
        private int ava_num2;//参加活动的人数

        public int getAva_num() {
            return ava_num;
        }

        public void setAva_num(int ava_num) {
            this.ava_num = ava_num;
        }

        public int getAva_num2() {
            return ava_num2;
        }

        public void setAva_num2(int ava_num2) {
            this.ava_num2 = ava_num2;
        }

        private int gift_id;
        private String gfname;
        private String gfinfo;
        private String gfnum;
        private String gfcontent;
        private String gfstart;
        private String gfend;
        private String gffar;
        private String gfimg;

        //发布礼品的用户ID
        private int senduser_id;
        private String senduser_name;
        private String term_name;
        private int had_time;


        public String getGfimg() {
            return gfimg;
        }

        public void setGfimg(String gfimg) {
            this.gfimg = gfimg;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getSchool_id() {
            return school_id;
        }

        public void setSchool_id(int school_id) {
            this.school_id = school_id;
        }

        public int getSeat_id() {
            return seat_id;
        }

        public void setSeat_id(int seat_id) {
            this.seat_id = seat_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
        public int getStarttime() {
            return starttime;
        }

        public void setStarttime(int starttime) {
            this.starttime = starttime;
        }

        public int getTerm_id() {
            return term_id;
        }

        public void setTerm_id(int term_id) {
            this.term_id = term_id;
        }


        public int getGift_id() {
            return gift_id;
        }

        public void setGift_id(int gift_id) {
            this.gift_id = gift_id;
        }

        public String getGfname() {
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

        public String getGfnum() {
            return gfnum;
        }

        public void setGfnum(String gfnum) {
            this.gfnum = gfnum;
        }

        public String getGfcontent() {
            return gfcontent;
        }

        public void setGfcontent(String gfcontent) {
            this.gfcontent = gfcontent;
        }

        public String getGfstart() {
            return gfstart;
        }

        public void setGfstart(String gfstart) {
            this.gfstart = gfstart;
        }

        public String getGfend() {
            return gfend;
        }

        public void setGfend(String gfend) {
            this.gfend = gfend;
        }

        public String getGffar() {
            return gffar;
        }

        public void setGffar(String gffar) {
            this.gffar = gffar;
        }

        public String getSenduser_name() {
            return senduser_name;
        }

        public void setSenduser_name(String senduser_name) {
            this.senduser_name = senduser_name;
        }

        public String getTerm_name() {
            return term_name;
        }

        public void setTerm_name(String term_name) {
            this.term_name = term_name;
        }

        public int getSenduser_id() {
            return senduser_id;
        }

        public void setSenduser_id(int senduser_id) {
            this.senduser_id = senduser_id;
        }

        public int getHad_time() {
            return had_time;
        }

        public void setHad_time(int had_time) {
            this.had_time = had_time;
        }
    }


}
