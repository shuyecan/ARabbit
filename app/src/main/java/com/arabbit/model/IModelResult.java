package com.arabbit.model;


import com.arabbit.net.ApiException;

import rx.Subscription;

/**
 * Created by net8 on 2017/2/24.
 */

public interface IModelResult<T> {
    void OnSuccess(T t);
    void OnError(ApiException e);
    void AddSubscription(Subscription subscription);
}
