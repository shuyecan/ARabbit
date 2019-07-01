package com.arabbit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.WindowManager;

import java.io.File;

public class SystemUtil {

    public static int getScreenOrientent(Context mContext) {
        Configuration newConfig = mContext.getResources().getConfiguration();
        int direct = 0;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏 
            direct = 0;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏 
            direct = 1;
        } else if (newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
            //键盘没关闭。屏幕方向为横屏 
            direct = 2;
        } else if (newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_YES) {
            //键盘关闭。屏幕方向为竖屏
            direct = 3;
        }
        return direct;
    }


    //通知系统更新相册
    public static void refreshGallery(Activity activity, String file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(new File(file)));
        activity.sendBroadcast(mediaScanIntent);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
}
