package com.arabbit.entity;

import java.util.List;

public class ShopInfoEntity {
    String name;
    int business_type;
    int status;
    String mobile;
    String province;
    String city;
    String area;
    String address;
    String introduce;
    String lng;
    String lat;
    String avatar_img;
    int user_id;
    int id;
    List<String> mbimg;
    List<String> cpimg;
    List<String> primg;
    List<String> storeimg;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(int business_type) {
        this.business_type = business_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAvatar_img() {
        return avatar_img;
    }

    public void setAvatar_img(String avatar_img) {
        this.avatar_img = avatar_img;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getMbimg() {
        return mbimg;
    }

    public void setMbimg(List<String> mbimg) {
        this.mbimg = mbimg;
    }

    public List<String> getCpimg() {
        return cpimg;
    }

    public void setCpimg(List<String> cpimg) {
        this.cpimg = cpimg;
    }

    public List<String> getPrimg() {
        return primg;
    }

    public void setPrimg(List<String> primg) {
        this.primg = primg;
    }

    public List<String> getStoreimg() {
        return storeimg;
    }

    public void setStoreimg(List<String> storeimg) {
        this.storeimg = storeimg;
    }
}
