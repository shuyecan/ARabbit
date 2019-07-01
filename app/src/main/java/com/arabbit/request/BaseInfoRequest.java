package com.arabbit.request;

/**
 * Created by net4 on 2017/6/22.
 */

public class BaseInfoRequest extends BaseRequest {
    private String user_id;
    private String token;
    private String target_user_id;

    public BaseInfoRequest( String version, String client, String user_id, String token, String target_user_id) {
        super(  version, client);
        this.user_id = user_id;
        this.token = token;
        this.target_user_id = target_user_id;
    }


}
