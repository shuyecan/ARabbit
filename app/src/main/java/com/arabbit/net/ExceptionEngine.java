package com.arabbit.net;

import android.net.ParseException;

import com.arabbit.R;
import com.arabbit.application.BaseApplication;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

public class ExceptionEngine {
    public static ApiException handleException(Throwable e) {
        ApiException ex;

        if (e instanceof ServerException) {             //HTTP错误
            ServerException httpException = (ServerException) e;
            ex = new ApiException(e, ErrorType.HTTP_ERROR);
            ex.message = httpException.message;
            return ex;

        } else if (e instanceof JSONException
                || e instanceof ParseException
                || e instanceof JsonParseException) {
            ex = new ApiException(e, ErrorType.PARSE_ERROR);
            ex.message = BaseApplication.getApplication().getString(R.string.parse_error);            //均视为解析错误
            return ex;
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ErrorType.NETWORK_ERROR);
            ex.message = BaseApplication.getApplication().getString(R.string.connection_failed);  //均视为网络错误
            return ex;
        } else if (e instanceof HttpException) {
            if ("HTTP 404 Not Found".equals(e.getMessage())) {
                ex = new ApiException(e, ErrorType.NETWORK_ERROR);
                ex.message = BaseApplication.getApplication().getString(R.string.no_connection_server);
            } else {
                ex = new ApiException(e, ErrorType.NETWORK_ERROR);
                ex.message = BaseApplication.getApplication().getString(R.string.other_connection_server_errors);
            }
            return ex;

        } else {
            ex = new ApiException(e, ErrorType.UNKONW);
            ex.message = BaseApplication.getApplication().getString(R.string.unknown_error);
            ;          //未知错误
            return ex;
        }
    }
}

