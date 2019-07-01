package com.arabbit.entity;
import java.util.List;
/**
 * Created by Administrator on 2018/7/9.
 */

public class ProtionListEntity {

    private int total_page;
    private List<ProtionListEntity.ListsBean> lists;
    public int getTotal_page() {
        return total_page;
    }
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
    public List<ProtionListEntity.ListsBean> getLists() {
        return lists;
    }
    public void setLists(List<ProtionListEntity.ListsBean> lists) {
        this.lists = lists;
    }
    public static class ListsBean {
    private int pt_id;
    private int type;
    private String far;
    private String starttime;
    private String token;
    private String endtime;
    private String rule;
    private String create_time;
    private int user_id;
    private int term_id;
    private int school_id;
    private int count2;
    private int count3;

        public int getCount3() {
            return count3;
        }

        public void setCount3(int count3) {
            this.count3 = count3;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

        public int getCount2() {
            return count2;
        }

        public void setCount2(int count2) {
            this.count2 = count2;
        }

    }


}
