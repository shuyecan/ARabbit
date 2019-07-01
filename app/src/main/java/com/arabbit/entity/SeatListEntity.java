package com.arabbit.entity;

import java.util.List;

/**
 * Created by net8 on 2017/8/1.
 */

public class SeatListEntity {

    /**
     * lists : [{"seat_id":1,"user_id":10,"no":"2500","name":"2017大一新生宿舍安排","create_time":0,"term_id":1},{"seat_id":6,"user_id":10,"no":"45","name":"2016大一","create_time":1502251540,"term_id":2}]
     * total_page : 1
     */

    private int total_page;
    private List<ListsBean> lists;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * seat_id : 1
         * user_id : 10
         * school_id : 10
         * no : 2500
         * name : 2017大一新生宿舍安排
         * create_time : 0
         * term_id : 1
         */

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
    }
}
