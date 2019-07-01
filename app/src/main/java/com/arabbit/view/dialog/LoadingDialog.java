package com.arabbit.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;


/**
 * 进度条
 * Created by net8 on 2016/11/28.
 */

public class LoadingDialog extends Dialog {
    private Context mContext;

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.loading_dialog);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.view_loading_dialog);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.loading_progress);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.loading_message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.loading_message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    /**
     * 不可取消，无文字，无回调
     */
    public void showDialog() {
        showDialog(true);
    }


    /**
     * 设置是否可取消
     */
    public void showDialog(boolean cancelable) {
        showDialog(null, cancelable);
    }

    public void showDialog(CharSequence message, boolean cancelable) {
        showDialog(message, cancelable, null);
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param message        提示
     * @param cancelable     是否按返回键取消
     * @param cancelListener 按下返回键监听
     * @return
     */

    public void showDialog(final CharSequence message, final boolean cancelable, final OnCancelListener cancelListener) {
        //是否运行于主线程
        if (Thread.currentThread().getId() != 1) {
            if (mContext instanceof Activity) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        show(message, cancelable, cancelListener);
                    }
                });
            }
        } else {
            show(message, cancelable, cancelListener);
        }
    }

    public void hideDialog() {
        if (isShowing()) {
            //是否运行于主线程
            if (Thread.currentThread().getId() != 1) {
                if (mContext instanceof Activity) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    });
                }
            } else {
                dismiss();
            }
        }
    }

    private void show(CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
        //如果正在显示，则返回
        if (isShowing()) {
            return;
        }
        if (message == null || message.length() == 0) {
            findViewById(R.id.loading_message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) findViewById(R.id.loading_message);
            txt.setText(message);
        }
        // 按返回键是否取消
        setCancelable(cancelable);
        // 监听返回键处理
        setOnCancelListener(cancelListener);
        // 设置居中
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        show();
    }
}
