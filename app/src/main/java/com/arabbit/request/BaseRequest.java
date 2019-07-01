package com.arabbit.request;

/**
 * Created by net4 on 2017/6/22.
 */

public class BaseRequest {
    private String version;
    private String client;

    public BaseRequest(String version,String client) {
        this.version = version;
        this.client = client;
    }
}
