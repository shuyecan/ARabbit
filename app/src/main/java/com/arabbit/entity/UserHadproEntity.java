package com.arabbit.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/5.
 */

public class UserHadproEntity  {

    private int total_page;
    private List<UserHadproEntity.ListsBean> lists;
    public int getTotal_page() {
        return total_page;
    }
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
    public List<UserHadproEntity.ListsBean> getLists() {
        return lists;
    }
    public void setLists(List<UserHadproEntity.ListsBean> lists) {
        this.lists = lists;
    }
    public static class ListsBean {
        private int pt_id;
        private String type;
        private String far;
        private String starttime;
        private String token;
        private String endtime;
        private String rule;
        private String create_time;
        private int user_id;
        private String username;
        private int term_id;
        private int school_id;
        private int hadpt_id;
        //发布抽奖活动的用户ID
        private int senduser_id;
        private String message;
        private String avatar_img;

        public String getAvatar_img() {
            return avatar_img;
        }

        public void setAvatar_img(String avatar_img) {
            this.avatar_img = avatar_img;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getHadpt_id() {
            return hadpt_id;
        }

        public void setHadpt_id(int hadpt_id) {
            this.hadpt_id = hadpt_id;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public int getTerm_id() {
            return term_id;
        }

        public void setTerm_id(int term_id) {
            this.term_id = term_id;
        }

        public int getSchool_id() {
            return school_id;
        }

        public void setSchool_id(int school_id) {
            this.school_id = school_id;
        }

    }

}
