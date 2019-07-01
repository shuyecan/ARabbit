package com.arabbit.entity;

/**
 * Created by net8 on 2017/7/29.
 */

public class LoginFromPasswordEntity {


    /**
     * user_id : 3
     * token : x0ZkdhfQSlIhMGGt
     * phone : 18589221964
     * avatar_img : xxx
     * nickname : 18589221964
     * first_register : 1
     */

    private int user_id;
    private String token;
    private String phone;
    private String avatar_img;
    private String nickname;
    private int first_register;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar_img() {
        return avatar_img;
    }

    public void setAvatar_img(String avatar_img) {
        this.avatar_img = avatar_img;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFirst_register() {
        return first_register;
    }

    public void setFirst_register(int first_register) {
        this.first_register = first_register;
    }
}
