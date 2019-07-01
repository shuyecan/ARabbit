package com.arabbit.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/5.
 */

public class UserHadprizeEntity {
    private int total_page;
    private List<UserHadprizeEntity.ListsBean> lists;
    public int getTotal_page() {
        return total_page;
    }
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
    public List<UserHadprizeEntity.ListsBean> getLists() {
        return lists;
    }
    public void setLists(List<UserHadprizeEntity.ListsBean> lists) {
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
        private String username;
        private int term_id;
        private int school_id;
        private int hadprize_id;
        private String primg;
        private String avatar_img;

        public String getAvatar_img() {
            return avatar_img;
        }

        public void setAvatar_img(String avatar_img) {
            this.avatar_img = avatar_img;
        }

        public String getPrimg() {
            return primg;
        }

        public void setPrimg(String primg) {
            this.primg = primg;
        }

        public int getHadprize_id() {
            return hadprize_id;
        }

        public void setHadprize_id(int hadprize_id) {
            this.hadprize_id = hadprize_id;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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
