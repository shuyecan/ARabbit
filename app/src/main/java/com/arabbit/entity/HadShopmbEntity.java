package com.arabbit.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/11/17.
 */

public class HadShopmbEntity {
    private int total_page;
    private List<HadShopmbEntity.ListsBean> lists;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<HadShopmbEntity.ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<HadShopmbEntity.ListsBean> lists) {
        this.lists = lists;
    }
    public static class ListsBean {

        private int user_id;
        private String username;
        private int create_time;
        private int mbgood_id;
        private int mb_id;
        private String mbname;
        private String info;
        private String title;
        private String mbimg;
        private Double mb_price;
        private Double 	preprice;
        private String content;
        private String avatar_img;
        private String no;
        private int hadmbgood_id;
        //发布礼品的用户ID
        private int senduser_id;
        private String senduser_name;

        public String getAvatar_img() {
            return avatar_img;
        }

        public void setAvatar_img(String avatar_img) {
            this.avatar_img = avatar_img;
        }

        public String getMbimg() {
            return mbimg;
        }

        public void setMbimg(String mbimg) {
            this.mbimg = mbimg;
        }

        public int getMbgood_id() {
            return mbgood_id;
        }

        public void setMbgood_id(int mbgood_id) {
            this.mbgood_id = mbgood_id;
        }

        public int getMb_id() {
            return mb_id;
        }

        public void setMb_id(int mb_id) {
            this.mb_id = mb_id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getSenduser_id() {
            return senduser_id;
        }

        public void setSenduser_id(int senduser_id) {
            this.senduser_id = senduser_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSenduser_name() {
            return senduser_name;
        }

        public void setSenduser_name(String senduser_name) {
            this.senduser_name = senduser_name;
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

        public String getMbname() {
            return mbname;
        }

        public void setMbname(String mbname) {
            this.mbname = mbname;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
        public int getHadmbgood_id() {
            return hadmbgood_id;
        }

        public void setHadmbgood_id(int hadmbgood_id) {
            this.hadmbgood_id = hadmbgood_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Double getMb_price() {
            return mb_price;
        }

        public void setMb_price(Double mb_price) {
            this.mb_price = mb_price;
        }

        public Double getPreprice() {
            return preprice;
        }

        public void setPreprice(Double preprice) {
            this.preprice = preprice;
        }
    }
}