package com.arabbit.utils;

import android.app.Activity;
import android.content.Intent;

import com.arabbit.R;


public class ActivityUtils {


    public static void jump2Next(Activity activity) {
        activity.overridePendingTransition(R.anim.tran_next_in,
                R.anim.tran_next_out);
    }


    /**
     * 跳转到另一个Activity，携带数据
     *
     * @param mActivity 上下文
     * @param cls       要跳转的类
     */
    public static void goToActivity(Activity mActivity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(mActivity, cls);
        mActivity.startActivity(intent);
        mActivity.finish();
        jump2Next(mActivity);
    }

    /**
     * 跳转到另一个Activity，携带数据(原Activity不finish)
     *
     * @param mActivity 上下文
     * @param cls       要跳转的类
     */
    public static void goToActivityNoFinish(Activity mActivity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(mActivity, cls);
        mActivity.startActivity(intent);
        jump2Next(mActivity);
    }


    /**
     * 跳转到上一个Activity
     *
     * @param activity
     */
    public static void jump2Previous(Activity activity) {
        activity.overridePendingTransition(R.anim.tran_previous_in,
                R.anim.tran_previous_out);
    }
}
