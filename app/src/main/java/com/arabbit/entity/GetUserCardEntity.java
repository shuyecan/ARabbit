package com.arabbit.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/7/29.
 */

public class GetUserCardEntity implements Serializable{

    private int total_page;
    private List<GetUserCardEntity.ListsBean> lists;
    public int getTotal_page() {
        return total_page;
    }
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
    public List<GetUserCardEntity.ListsBean> getLists() {
        return lists;
    }
    public void setLists(List<GetUserCardEntity.ListsBean> lists) {
        this.lists = lists;
    }
    public static class ListsBean implements Serializable{
        private int card_id;
        private String real_name;
        private String company;
        private String 	job;
        private String token;
        private String com_address;
        private String com_page;
        private String user_phone;
        private String 	user_tel;
        private String user_fax;
        private String user_email;
        private String user_qq;
        private String wechat;
        private String post_address;
        private String com_intro;
        private String create_time;
        private int user_id;

        public int getCard_id() {
            return card_id;
        }

        public void setCard_id(int card_id) {
            this.card_id = card_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getCom_address() {
            return com_address;
        }

        public void setCom_address(String com_address) {
            this.com_address = com_address;
        }

        public String getCom_page() {
            return com_page;
        }

        public void setCom_page(String com_page) {
            this.com_page = com_page;
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

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_tel() {
            return user_tel;
        }

        public void setUser_tel(String user_tel) {
            this.user_tel = user_tel;
        }

        public String getUser_fax() {
            return user_fax;
        }

        public void seUser_fax(String user_fax) {
            this.user_fax = user_fax;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_qq() {
            return user_qq;
        }

        public void setUser_qq(String user_qq) {
            this.user_qq = user_qq;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getPost_address() {
            return post_address;
        }

        public void setPost_address(String post_address) {
            this.post_address = post_address;
        }

        public String getCom_intro() {
            return com_intro;
        }

        public void setCom_intro(String com_intro) {
            this.com_intro = com_intro;
        }

    }

}
