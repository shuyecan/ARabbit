package com.arabbit.request;

/**
 * Created by net4 on 2017/6/22.
 */

public class SendSmsCodeRequest extends BaseRequest {
    private String phone;
    private String sms_type;

    public SendSmsCodeRequest( String version, String client, String phone, String sms_type) {
        super(version, client);
        this.phone = phone;
        this.sms_type = sms_type;
    }
}
