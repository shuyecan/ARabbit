package com.arabbit.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/11/4.
 */

public class ShopprListEntity {
    private int total_page;
    private List<ShopprListEntity.ListsBean> lists;
    public int getTotal_page() {
        return total_page;
    }
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
    public List<ShopprListEntity.ListsBean> getLists() {
        return lists;
    }
    public void setLists(List<ShopprListEntity.ListsBean> lists) {
        this.lists = lists;
    }
    public static class ListsBean {
        private int pr_id;
        private int pt_id;
        private String prname;
        private String info;
        private int num;
        private String token;
        private String content;
        private String rank;
        private int rate;
        private String create_time;
        private int user_id;
        private String primg;
        private Double price;
        private String prized_count;//中奖人数

        //发布礼品的用户ID
        private int senduser_id;
        private String senduser_name;
        private int had_time;

        public String getPrized_count() {
            return prized_count;
        }

        public void setPrized_count(String prized_count) {
            this.prized_count = prized_count;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPrimg() {
            return primg;
        }

        public void setPrimg(String primg) {
            this.primg = primg;
        }

        public int getPr_id() {
            return pr_id;
        }

        public void setPr_id(int pr_id) {
            this.pr_id = pr_id;
        }

        public int getPrrate() {
            return rate;
        }

        public void setPrrate(int rate) {
            this.rate = rate;
        }

        public int getPt_id() {
            return pt_id;
        }

        public void setPt_id(int pt_id) {
            this.pt_id = pt_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPrname() {
            return prname;
        }

        public void setPrname(String prname) {
            this.prname = prname;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getPrnum() {
            return num;
        }

        public void setPrnum(int num) {
            this.num = num;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
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

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
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

    }

}
