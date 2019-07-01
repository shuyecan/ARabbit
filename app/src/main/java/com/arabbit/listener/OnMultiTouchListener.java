package com.arabbit.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * 连续点击事件监听器 可以用作双击事件
 */
public abstract class OnMultiTouchListener implements View.OnTouchListener {

    int count = 0;
    long firClick = 0;
    long secClick = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            count++;
            if (count == 1) {
                firClick = System.currentTimeMillis();
            } else if (count == 2) {
                secClick = System.currentTimeMillis();
                if (secClick - firClick < 1000) {
                    //双击事件
                    DoubleClick(v, event);
                }
                count = 0;
                firClick = 0;
                secClick = 0;
            }
        }
        return true;
    }

    public abstract void DoubleClick(View v, MotionEvent event);


}