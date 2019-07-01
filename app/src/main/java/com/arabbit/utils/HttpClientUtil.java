package com.arabbit.utils;

import android.text.TextUtils;
import android.util.Log;

import com.arabbit.model.Config;
import com.arabbit.model.IViewLoad;
import com.arabbit.model.SubModel;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClientUtil extends SubModel {

    public static OkHttpClient mOkHttpClient = new OkHttpClient();

    public HttpClientUtil(IViewLoad viewload) {
        super(viewload);
    }

    public static void post() {
        //创建Form表单对象，可以add多个键值队
        FormBody formBody = new FormBody.Builder()
                .add("param", "value")
                .add("param", "value")
                .build();
        //创建一个Request
        Request request = new Request.Builder()
                .url("http://www.jianshu.com/")
                .post(formBody)
                .build();
        //发起异步请求，并加入回调
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("okHttp", "post=" + response.body().string());
            }
        });
    }


    public static void upload(File file, String type, Callback callback) {
        //多个文件集合
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置为表单类型
            builder.setType(MultipartBody.FORM);
            //添加表单键值
            builder.addFormDataPart("usage", type);
            if(TextUtils.equals(type,"upload_video")){
                RequestBody requestBody1 = RequestBody.create(MediaType.parse("video/mp4") , file);
                builder.addFormDataPart("pic", file.getName(), requestBody1);
            }else{

                //添加多个文件
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                builder.addFormDataPart("pic", file.getName(), fileBody);
            }


            Request request = new Request.Builder()
                    .url(Config.BASE_URL + "system/uploadPic")
                    .post(builder.build())
                    .build();
            //发起异步请求，并加入回调
            mOkHttpClient.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void uploadMulti(File file, String type, Callback callback) {
        //多个文件集合
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置为表单类型
            builder.setType(MultipartBody.FORM);
            //添加表单键值
            builder.addFormDataPart("usage", type);
            if(TextUtils.equals(type,"upload_video")){
                RequestBody requestBody1 = RequestBody.create(MediaType.parse("video/mp4") , file);
                builder.addFormDataPart("pic", file.getName(), requestBody1);
            }else{

                //添加多个文件
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                builder.addFormDataPart("pic", file.getName(), fileBody);
            }


            Request request = new Request.Builder()
                    .url(Config.BASE_URL + "system/uploadPic")
                    .post(builder.build())
                    .build();
            //发起异步请求，并加入回调
            mOkHttpClient.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTimeout() {
        mOkHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//10秒连接超时
                .writeTimeout(10, TimeUnit.SECONDS)//10m秒写入超时
                .readTimeout(10, TimeUnit.SECONDS)//10秒读取超时
                .build();
    }


}