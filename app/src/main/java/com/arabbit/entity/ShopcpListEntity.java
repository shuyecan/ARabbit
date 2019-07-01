package com.arabbit.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/11/8.
 */

public class ShopcpListEntity {
    private int total_page;
    private List<ShopcpListEntity.ListsBean> lists;
    public int getTotal_page() {
        return total_page;
    }
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
    public List<ShopcpListEntity.ListsBean> getLists() {
        return lists;
    }
    public void setLists(List<ShopcpListEntity.ListsBean> lists) {
        this.lists = lists;
    }
    public static class ListsBean {
        private int cp_id;
        private String cpname;
        private String info;
        private int num;
        private String token;
        private String content;
        private Double cp_price;
        private Double 	preprice;
        private String starttime;
        private String endtime;
        private String create_time;
        private int user_id;
        private String cpimg;
        private String buy_count;//购买人数
        private String pullon;
        private int pull_time;;


        //发布礼品的用户ID
        private int senduser_id;
        private String senduser_name;
        private int had_time;

        public int getCp_id() {
            return cp_id;
        }

        public void setCp_id(int cp_id) {
            this.cp_id = cp_id;
        }

        public String getBuy_count() {
            return buy_count;
        }

        public void setBuy_count(String buy_count) {
            this.buy_count = buy_count;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getCpimg() {
            return cpimg;
        }

        public void setCpimg(String cpimg) {
            this.cpimg = cpimg;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCpname() {
            return cpname;
        }

        public void setCpname(String cpname) {
            this.cpname = cpname;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Double getCp_price() {
            return cp_price;
        }

        public void setCp_price(Double cp_price) {
            this.cp_price = cp_price;
        }

        public Double getPreprice() {
            return preprice;
        }

        public void setPreprice(Double preprice) {
            this.preprice = preprice;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPullon() {
            return pullon;
        }

        public void setPullon(String pullon) {
            this.pullon = pullon;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getSenduser_name() {
            return senduser_name;
        }

        public void setSenduser_name(String senduser_name) {
            this.senduser_name = senduser_name;
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

        public int getPull_time() {
            return pull_time;
        }

        public void setPull_time(int pull_time) {
            this.pull_time = pull_time;
        }

    }
}
