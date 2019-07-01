package com.arabbit.utils;

import com.arabbit.bean.UpYunBean;
import com.arabbit.interfaces.UrlListener;
import com.google.gson.Gson;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.SignatureListener;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;
import com.upyun.library.utils.UpYunUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by net4 on 2016/9/8.
 */
public class UpLoadYun {

    public static String KEY = "GLYFGrAEI7gcToeoWMGyluk40jc=";
    public static String SPACE = "weixiangserver";
    public static final String UPYUN_URL = "http://weixiangserver.b0.upaiyun.com";
    public static String upYunUrl;

    public static void setUrlListener(UrlListener urlListener) {
        UpLoadYun.urlListener = urlListener;
    }

    public static UrlListener urlListener;

    public static void upLoad(final File filePath, final UrlListener urlListener) {
        String savePath = "/wx/image/" + CreateStringHelper.getRandomString(40) + ".jpg";
        final Map<String, Object> paramsMap = new HashMap<>();
        //上传空间
        paramsMap.put(Params.BUCKET, SPACE);
        //保存路径，任选其中一个
        paramsMap.put(Params.SAVE_KEY, savePath);
//        paramsMap.put(Params.PATH, savePath);
        //可选参数（详情见api文档介绍）
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        //结束回调，不可为空
        final UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                System.out.println("result" + result);
                if (isSuccess) {
                    UpYunBean upYunBean = new Gson().fromJson(result, UpYunBean.class);
                    upYunUrl = UPYUN_URL + upYunBean.getUrl();
                    urlListener.setResult(upYunUrl);
                    /*if (urlListener != null) {
                        urlListener.setImage(filePath.getAbsolutePath(), upYunUrl);
                           dialogHelper.dismissProgressDialog(activity);
                        ToastUtils.showToastShort("图片添加成功");
                    }*/
//                    ToastUtils.showToastShort("图片添加成功");
                } else {
//                    dialogHelper.dismissProgressDialog(activity);
                    urlListener.setResult(null);
                    ToastUtils.showToastShort("图片上传失败");
                }
            }
        };

        SignatureListener signatureListener = new SignatureListener() {
            @Override
            public String getSignature(String raw) {
                return UpYunUtils.md5(raw + KEY);
            }
        };
        //进度回调，可为空
        UpProgressListener progressListener = null;
        //上传图片
        UploadManager.getInstance().formUpload(filePath, paramsMap, signatureListener, completeListener, progressListener);
    }

    public static void upLoadVoice(final File filePath, String fileName, final UrlListener urlListener) {
        String savePath = "/wx/voice/" + fileName;
        final Map<String, Object> paramsMap = new HashMap<>();
        //上传空间
        paramsMap.put(Params.BUCKET, SPACE);
        //保存路径，任选其中一个
        paramsMap.put(Params.SAVE_KEY, savePath);
//        paramsMap.put(Params.PATH, savePath);
        //可选参数（详情见api文档介绍）
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        //结束回调，不可为空
        final UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                System.out.println("result" + result);
                if (isSuccess) {
                    UpYunBean upYunBean = new Gson().fromJson(result, UpYunBean.class);
                    upYunUrl = UPYUN_URL + upYunBean.getUrl();
                    urlListener.setResult(upYunUrl);
                    /*if (urlListener != null) {
                        urlListener.setImage(filePath.getAbsolutePath(), upYunUrl);
                           dialogHelper.dismissProgressDialog(activity);
                        ToastUtils.showToastShort("图片添加成功");
                    }*/
                } else {
//                    dialogHelper.dismissProgressDialog(activity);
                    urlListener.setResult(null);
                    ToastUtils.showToastShort("图片上传失败");
                }
            }
        };

        SignatureListener signatureListener = new SignatureListener() {
            @Override
            public String getSignature(String raw) {
                return UpYunUtils.md5(raw + KEY);
            }
        };
        //进度回调，可为空
        UpProgressListener progressListener = null;
        //上传图片
        UploadManager.getInstance().formUpload(filePath, paramsMap, signatureListener, completeListener, progressListener);
    }


    public static void upLoadMusic(final File filePath, String fileName, final UrlListener urlListener) {
        String savePath = "/wx/music/" + fileName;
        final Map<String, Object> paramsMap = new HashMap<>();
        //上传空间
        paramsMap.put(Params.BUCKET, SPACE);
        //保存路径，任选其中一个
        paramsMap.put(Params.SAVE_KEY, savePath);
//        paramsMap.put(Params.PATH, savePath);
        //可选参数（详情见api文档介绍）
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        //结束回调，不可为空
        final UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                System.out.println("result" + result);
                if (isSuccess) {
                    UpYunBean upYunBean = new Gson().fromJson(result, UpYunBean.class);
                    upYunUrl = UPYUN_URL + upYunBean.getUrl();
                    urlListener.setResult(upYunUrl);
                    /*if (urlListener != null) {
                        urlListener.setImage(filePath.getAbsolutePath(), upYunUrl);
                           dialogHelper.dismissProgressDialog(activity);
                        ToastUtils.showToastShort("图片添加成功");
                    }*/
                } else {
//                    dialogHelper.dismissProgressDialog(activity);
                    urlListener.setResult(null);
                    ToastUtils.showToastShort("图片上传失败");
                }
            }
        };

        SignatureListener signatureListener = new SignatureListener() {
            @Override
            public String getSignature(String raw) {
                return UpYunUtils.md5(raw + KEY);
            }
        };
        //进度回调，可为空
        UpProgressListener progressListener = null;
        //上传图片
        UploadManager.getInstance().formUpload(filePath, paramsMap, signatureListener, completeListener, progressListener);
    }

}


