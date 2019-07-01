package com.arabbit.rx;


import com.arabbit.entity.BaseResult;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CommonTransformer<T> implements Observable.Transformer<BaseResult<T>, T> {

    @Override
    public Observable<T> call(Observable<BaseResult<T>> tansFormerObservable) {
        return tansFormerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorTransformer.<T>getInstance());
    }
}

