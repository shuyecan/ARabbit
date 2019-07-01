package com.arabbit.rx;


import com.arabbit.entity.BaseResult;
import com.arabbit.net.ErrorType;
import com.arabbit.net.ExceptionEngine;
import com.arabbit.net.ServerException;

import rx.Observable;
import rx.functions.Func1;

public class ErrorTransformer<T> implements Observable.Transformer<BaseResult<T>, T> {

    private static ErrorTransformer errorTransformer = null;

    @Override
    public Observable<T> call(Observable<BaseResult<T>> responseObservable) {
        return responseObservable.map(new Func1<BaseResult<T>, T>() {
            @Override
            public T call(BaseResult<T> httpResult) {
                if (httpResult == null)
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析对象为空");
                //除了code == 1  其他全抛出自定义异常
                if (Integer.parseInt(httpResult.getCode()) != ErrorType.SUCCESS)
                    throw new ServerException(Integer.parseInt(httpResult.getCode()), httpResult.getMessage());
                return httpResult.getData();
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        })/*.retryWhen(new RetryMechanism(3,3000))*/;
    }


    public static <T> ErrorTransformer<T> getInstance() {
        if (errorTransformer == null) {
            synchronized (ErrorTransformer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformer<>();
                }
            }
        }
        return errorTransformer;
    }
}
