package com.arabbit.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

import com.arabbit.application.BaseApplication;


/**
 * Px转换
 *
 * @author Vin
 */
public class ScreenManager {

    public final static float LARGESCREEN = 2.0f;
    public final static float NORMALSCREEN = 1.5f;
    public final static float SMALLSCREEN = 1f;
    private final static Context context = BaseApplication.getApplication();
    private static float scale = context.getResources().getDisplayMetrics().density;
    private final static int screenHeight;
    private final static int screenWidth;
    private static int statusHeight;
    private static boolean isMeizu;

    static {
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        isMeizu = android.os.Build.MANUFACTURER != null && "meizu".equals(android.os.Build.MANUFACTURER.toLowerCase());
    }

    private ScreenManager() {
    }

    /**
     * 屏幕像素高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return Math.max(screenHeight, screenWidth);
    }

    /**
     * 屏幕像素宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        return Math.min(screenHeight, screenWidth);
    }

    /**
     * 将Px值转换为Dip值
     *
     * @param px 值
     * @return 对应的Dip
     */
    public static int toDipValue(float px) {
        return (int) (px * scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    /**
     * 将Px值转换为Dip值
     *
     * @param px 值
     * @return 对应的Dip
     */
    public static float toDipFloatValue(float px) {
        return px * scale + 0.5f * (px >= 0 ? 1 : -1);
    }

    /**
     * 将Dip值转换为Px值
     *
     * @param dip 值
     * @return 对应的Px
     */
    public static int toPxValue(float dip) {
        return (int) (dip / scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 返回每英寸像素值
     */
    public static int getDensityDpi() {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int pxToSp(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int spTpPx(float spValue) {
        return (int) (spValue * scale + 0.5f);
    }

    public static int spTpPx2(float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据字体缩放(sp)比获得的大小
     *
     * @param value
     * @return
     */
    public static int getValueDependenceFontScale(int value) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value * fontScale + 0.5f);
    }

    /**
     */
    public static float getDensity() {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取手机状态栏高度
     */
    public static int getStatusHeight(Activity activity) {
        if (activity == null || statusHeight != 0) {
            return statusHeight;
        }
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * <b>类功能描述：</b>
     * <div style="margin-left:40px;margin-top:-10px"> 用于简化ScreenManager代码的缩写,
     * 建议使用->import static ClassPath.SM.*; </div> 修改时间：</br>
     * 修改备注：</br>
     */
    public final static class SM {
        /**
         */
        public final static float CurrentDensity = getDensity();
        /**
         * 当前手机屏幕宽度
         */
        public final static float ScreenWidth = getScreenWidth();
        /**
         * 当前手机屏幕高度
         */
        public final static float ScreenHeight = getScreenHeight();
        /**
         * 大屏幕手机
         */
        public final static float LARGESCREEN = ScreenManager.LARGESCREEN;
        /**
         * 正常屏幕手机
         */
        public final static float NORMALSCREEN = ScreenManager.NORMALSCREEN;
        /**
         * 烂屏幕手机
         */
        public final static float SMALLSCREEN = ScreenManager.SMALLSCREEN;

        /**
         * ScreenManager.toDipValue(int px)的缩水版,简化代码视图
         */
        public final static int Dip(int px) {
            return toDipValue(px);
        }

        ;
    }

    /**
     * 判断是否是魅族屏幕
     */
    public static boolean isMeizhu() {
        return isMeizu;
    }
}
