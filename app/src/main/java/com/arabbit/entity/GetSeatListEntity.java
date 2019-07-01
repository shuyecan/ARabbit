package com.arabbit.entity;

import java.util.List;

/**
 * Created by net8 on 2017/8/1.
 */

public class GetSeatListEntity {

    /**
     * full_name : 第一教学楼 一楼 101A课室
     * lists : [{"seat_id":5,"sn":"gd","no":12,"user_id":10,"nickname":"测试名字长度使用","create_time":0,"school_id":1},{"seat_id":6,"sn":"sadf","no":45,"user_id":11,"nickname":"托尼屎大颗2","create_time":0,"school_id":1},{"seat_id":8,"sn":"adf","no":87,"user_id":13,"nickname":"测试名字","create_time":0,"school_id":2}]
     */

    private String full_name;
    private List<ListsBean> lists;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * seat_id : 5
         * sn : gd
         * no : 12
         * user_id : 10
         * nickname : 测试名字长度使用
         * create_time : 0
         * school_id : 1
         */

        private int seat_id;
        private String sn;
        private int no;
        private int user_id;
        private String nickname;
        private int create_time;
        private int school_id;
        private String b_id;
        private String hasgift;
        private String hasproize;

        public String getHasproize() {
            return hasproize;
        }

        public void setHasproize(String hasproize) {
            this.hasproize = hasproize;
        }

        public String getHasgift() {
            return hasgift;
        }

        public void setHasgift(String hasgift) {
            this.hasgift = hasgift;
        }

        public String getB_id() {
            return b_id;
        }

        public void setB_id(String b_id) {
            this.b_id = b_id;
        }

        public int getSeat_id() {
            return seat_id;
        }

        public void setSeat_id(int seat_id) {
            this.seat_id = seat_id;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getSchool_id() {
            return school_id;
        }

        public void setSchool_id(int school_id) {
            this.school_id = school_id;
        }
    }
}
