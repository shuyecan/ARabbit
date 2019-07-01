package com.arabbit.rx;


import com.arabbit.model.BaseSubscriber;
import com.arabbit.model.IViewLoad;
import com.arabbit.net.ApiException;
import com.arabbit.utils.NetWorkUtils;

public class CommonSubscriber<T> extends BaseSubscriber<T> {
    private CommonSubscriber() {}

    private IViewLoad iViewLoad;
    public CommonSubscriber(IViewLoad ac) {
        iViewLoad = ac;
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
         *  请求前若网络不可用 ，则直接结束
         * */
        if (!NetWorkUtils.isNetworkAvailable()) {
            onNetError();
            onCompleted();
            return;
        }
        if (iViewLoad != null)
            iViewLoad.showDialog();
        System.out.println("CommonSubscriber onStart");
    }

    @Override
    public void onCompleted() {
        if (iViewLoad != null)
            iViewLoad.dismissLoading();
        System.out.println("CommonSubscriber onCompleted");
    }

    @Override
    public void onNext(T t) {
        if (iViewLoad != null)
            iViewLoad.dismissLoading();
        System.out.println("CommonSubscriber onNext");
    }

    @Override
    protected void onError(ApiException e) {
        if (iViewLoad != null)
            iViewLoad.dismissLoading();
        System.out.println("CommonSubscriber onError");
    }

    public void onNetError() {
        System.out.println("CommonSubscriber onNetError");
        if (iViewLoad != null)
            iViewLoad.dismissLoading();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (iViewLoad != null)
            iViewLoad.dismissLoading();
        System.out.println("CommonSubscriber onError(throwable)");
    }
}
