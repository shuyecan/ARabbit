package com.arabbit.utils;


import android.util.Log;

import com.arabbit.model.Config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by linana on 2018-05-30.
 */

public class OkHttp3Utils {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    static  OkHttp3Utils instance;

    private static OkHttpClient singleton;
    private static OkHttpClient.Builder builder;

    public static OkHttp3Utils getInstance(){
        if(instance == null){
            synchronized (OkHttp3Utils.class){
                instance = new OkHttp3Utils();
                builder = new OkHttpClient.Builder();
                singleton = builder.build();
            }
        }
        return instance;

    }

    /**
     * 上传多张图片及参数
     * @param
     * @param
     * @param
     * @param
     */
    public Observable<String> sendMultipart(final List<File> files){
        return Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(final Subscriber<? super String> subscriber) {
                MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
                multipartBodyBuilder.setType(MultipartBody.FORM);
                //遍历map中所有参数到builder
//                if (params != null){
//                    for (String key : params.keySet()) {
//                        multipartBodyBuilder.addFormDataPart(key, params.get(key));
//                    }
//                }
//                SharedPreferenceData sp = BmobIMApplication.getSp();
                multipartBodyBuilder.addFormDataPart("usage", "upload_img");

                //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
                if (files != null){
                    for (File file : files) {
                        String name = file.getName();
                        Log.e("aaa","上传的图片名称："+name);
                        multipartBodyBuilder.addFormDataPart("pic[]",name, RequestBody.create(MEDIA_TYPE_PNG, file));
                    }
                }
                //构建请求体
                RequestBody requestBody = multipartBodyBuilder.build();

                Request.Builder RequestBuilder = new Request.Builder();
                RequestBuilder.url(Config.BASE_URL + "system/uploadMultiPic");// 添加URL地址
                RequestBuilder.post(requestBody);
                Request request = RequestBuilder.build();
                singleton.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                        subscriber.onCompleted();
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        subscriber.onNext(str);
                        subscriber.onCompleted();
                        call.cancel();
                    }
                });
            }
        });
    }


}
