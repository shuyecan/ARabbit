package com.arabbit.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/11/4.
 */

public class ShopmbListEntity {

    private int total_page;
    private List<ShopmbListEntity.ListsBean> lists;
    public int getTotal_page() {
        return total_page;
    }
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
    public List<ShopmbListEntity.ListsBean> getLists() {
        return lists;
    }
    public void setLists(List<ShopmbListEntity.ListsBean> lists) {
        this.lists = lists;
    }
    public static class ListsBean {
        private int mb_id;
        private String title;
        private String far;
        private String starttime;
        private String token;
        private String endtime;
        private String rule;
        private String create_time;
        private int user_id;
        private String mbgoodimg;
        private int buy_count;
        private int count3;

        public int getMb_id() {
            return mb_id;
        }

        public void setMb_id(int mb_id) {
            this.mb_id = mb_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFar() {
            return far;
        }

        public void setFar(String far) {
            this.far = far;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getMbgoodimg() {
            return mbgoodimg;
        }

        public void setMbgoodimg(String mbgoodimg) {
            this.mbgoodimg = mbgoodimg;
        }

        public int getBuy_count() {
            return buy_count;
        }

        public void setBuy_count(int buy_count) {
            this.buy_count = buy_count;
        }

        public int getCount3() {
            return count3;
        }

        public void setCount3(int count3) {
            this.count3 = count3;
        }

    }

}
