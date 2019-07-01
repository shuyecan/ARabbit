package com.arabbit.entity;

import java.util.List;

/**
 * Created by net8 on 2017/8/1.
 */

public class GetTermListEntity {
    /**
     * main_term : {"id":3,"name":"2017大一","school_id":2,"create_time":0,"is_main":1}
     * lists : [{"term_id":3,"term_name":"2017大一","is_main":1,"create_time":0,"school_id":2},{"term_id":4,"term_name":"2016大一","is_main":2,"create_time":0,"school_id":2}]
     * total_page : 1
     */

    private MainTermBean main_term;
    private int total_page;
    private List<ListsBean> lists;

    public MainTermBean getMain_term() {
        return main_term;
    }

    public void setMain_term(MainTermBean main_term) {
        this.main_term = main_term;
    }

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

    public static class MainTermBean {
        /**
         * id : 3
         * name : 2017大一
         * school_id : 2
         * create_time : 0
         * is_main : 1
         */

        private int id;
        private String name;
        private int school_id;
        private int create_time;
        private int starttime;
        private int is_main;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSchool_id() {
            return school_id;
        }

        public void setSchool_id(int school_id) {
            this.school_id = school_id;
        }

        public int getStarttime() {
            return starttime;
        }

        public void setStarttime(int starttime) {
            this.starttime = starttime;
        }
        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getIs_main() {
            return is_main;
        }

        public void setIs_main(int is_main) {
            this.is_main = is_main;
        }
    }

    public static class ListsBean {
        /**
         * term_id : 3
         * term_name : 2017大一
         * is_main : 1
         * create_time : 0
         * school_id : 2
         */

        private int term_id;
        private String term_name;
        private int is_main;
        private int create_time;
        private int starttime;
        private int school_id;

        public int getTerm_id() {
            return term_id;
        }

        public void setTerm_id(int term_id) {
            this.term_id = term_id;
        }

        public String getTerm_name() {
            return term_name;
        }

        public void setTerm_name(String term_name) {
            this.term_name = term_name;
        }

        public int getIs_main() {
            return is_main;
        }

        public void setIs_main(int is_main) {
            this.is_main = is_main;
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

        public int getSchool_id() {
            return school_id;
        }

        public void setSchool_id(int school_id) {
            this.school_id = school_id;
        }
    }
}
