package com.arabbit.model;


import com.arabbit.net.ApiException;

import rx.Subscriber;

/**
 * Created by net8 on 2017/2/15.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        ApiException apiException = (ApiException) e;
        onError(apiException);
    }

    /**
     * @param e 错误的一个回调
     */
    protected abstract void onError(ApiException e);
}
