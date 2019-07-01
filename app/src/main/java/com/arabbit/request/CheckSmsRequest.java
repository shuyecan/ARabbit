package com.arabbit.request;

/**
 * Created by net4 on 2017/6/22.
 */

public class CheckSmsRequest extends BaseRequest {
    private String phone;
    private String sms_type;
    private String sms_code;

    public CheckSmsRequest(String version, String client, String phone, String sms_code, String sms_type) {
        super(version, client);
        this.phone = phone;
        this.sms_code = sms_code;
        this.sms_type = sms_type;
    }
}
