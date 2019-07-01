package com.arabbit.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.arabbit.application.BaseApplication;


public class ToastUtils {
    private static long lastTime = 0;

    private static Toast toast;


    public static void showToastShort(String text) {
        long currentTime = System.currentTimeMillis();
        if (lastTime == 0 || (currentTime - lastTime) > 1000) {
            if (toast == null) {
                toast = Toast.makeText(BaseApplication.getApplication(), text, Toast.LENGTH_SHORT);
            } else {
                toast.setText(text);
            }
            toast.show();
            lastTime = currentTime;
        }
    }

    public static void showToastShort(int redId) {
        Context context = BaseApplication.getApplication();
        String str = context.getString(redId);
        showToastShort(str);
    }

    public static void showToastLong(String text) {
        long currentTime = System.currentTimeMillis();
        if (lastTime == 0 || (currentTime - lastTime) > 1000) {
//            Toast.makeText(BaseApplication.getApplication(), text, Toast.LENGTH_LONG).show();
            if (toast == null) {
                toast = Toast.makeText(BaseApplication.getApplication(), text, Toast.LENGTH_LONG);
            } else {
                toast.setText(text);
            }
            toast.show();
            lastTime = currentTime;
        }
    }


    public static void showToastOnUiThreadShort(Activity activity, final String text) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToastShort(text);
            }
        });
    }

    public static void showToastOnUiThreadLong(Activity activity, final String text) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToastLong(text);
            }
        });
    }


    public static void showShortNetworkError() {
        showToastShort("网络连接失败!");
    }

    public static void showLongNetworkError() {
        showToastLong("网络连接失败!");
    }
}
