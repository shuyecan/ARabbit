package com.arabbit.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/11/4.
 */

public class ShopptListEntity {

    private int total_page;
    private List<ShopptListEntity.ListsBean> lists;
    public int getTotal_page() {
        return total_page;
    }
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
    public List<ShopptListEntity.ListsBean> getLists() {
        return lists;
    }
    public void setLists(List<ShopptListEntity.ListsBean> lists) {
        this.lists = lists;
    }
    public static class ListsBean {
        private int pt_id;
        private int type;
        private String title;
        private String far;
        private String starttime;
        private String token;
        private String endtime;
        private String rule;
        private String create_time;
        private int user_id;
        private String ptgoodimg;
        private int count2;
        private int count3;

        public int getPt_id() {
            return pt_id;
        }

        public void setPt_id(int pt_id) {
            this.pt_id = pt_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public String getPtgoodimg() {
            return ptgoodimg;
        }

        public void setPtgoodimg(String ptgoodimg) {
            this.ptgoodimg = ptgoodimg;
        }

        public int getCount2() {
            return count2;
        }

        public void setCount2(int count2) {
            this.count2 = count2;
        }

        public int getCount3() {
            return count3;
        }

        public void setCount3(int count3) {
            this.count3 = count3;
        }

    }
}
