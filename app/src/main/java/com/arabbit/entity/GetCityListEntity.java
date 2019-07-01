package com.arabbit.entity;

/**
 * Created by net8 on 2017/7/31.
 */

public class GetCityListEntity {

    /**
     * city_id : 1
     * city : 北京
     * city_initial : B
     * create_time : 1
     */

    private int city_id;
    private String city;
    private String city_initial;
    private int create_time;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_initial() {
        return city_initial;
    }

    public void setCity_initial(String city_initial) {
        this.city_initial = city_initial;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
