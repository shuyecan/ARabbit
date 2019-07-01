package com.arabbit.request;

/**
 * Created by net4 on 2017/6/22.
 */

public class RegisterFromPhoneRequest extends BaseRequest {
    String phone;
    String password;
    String role;
    String avatar_img;
    String district;
    String address;
    String postal_code;

    public RegisterFromPhoneRequest(String version, String client, String phone, String password, String role, String avatar_img, String district, String address, String postal_code) {
        super(version, client);
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.avatar_img = avatar_img;
        this.district = district;
        this.address = address;
        this.postal_code = postal_code;
    }
}
