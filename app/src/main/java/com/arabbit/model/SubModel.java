package com.arabbit.model;


import com.arabbit.net.Http;
import com.arabbit.net.HttpService;

/**
 * Created by net4 on 2017/6/22.
 */

public class SubModel {
    protected static HttpService httpService;
    protected IViewLoad viewload;

    private SubModel() {
    }

    public SubModel(IViewLoad viewload) {
        this.viewload = viewload;
        httpService = Http.getHttpService();
    }
}
