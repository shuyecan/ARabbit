package com.arabbit.request;

/**
 * Created by net4 on 2017/6/22.
 */

public class LoginFromPassword extends BaseRequest {
    private String phone;
    private String password;

    public LoginFromPassword(String version, String client, String phone, String password) {
        super( version, client);
        this.phone = phone;
        this.password = password;
    }
}
