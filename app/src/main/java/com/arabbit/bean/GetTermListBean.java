package com.arabbit.bean;

/**
 * Created by net8 on 2017/8/1.
 */

public class GetTermListBean {


    /**
     * term_id : 3
     * term_name : 2017大一
     * is_main : 2
     * create_time : 0
     */

    private int term_id;
    private String term_name;
    private int is_main;
    private int create_time;
    private int starttime;
    private int select;


    public int getIs_main() {
        return is_main;
    }

    public void setIs_main(int is_main) {
        this.is_main = is_main;
    }


    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

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
}
