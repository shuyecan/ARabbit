package com.arabbit.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Gravity;
import android.view.WindowManager;

public class DialogHelper {

    private ProgressDialog mProgressDialog;

    /**
     * 弹窗提示正在进行操作，已作了处理可以在子线程中调用
     *
     * @param mess       要提示的内容
     * @param cancelable 是否可取消
     */
    public void showProgressDialog(final Activity activity, final String mess, final boolean cancelable) {
        // 主线程中操作
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mProgressDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
                mProgressDialog.setIndeterminate(true); //
                mProgressDialog.setCancelable(cancelable); // 用户是否可以取消
                if (mess != null && mess != "")
                    mProgressDialog.setMessage(mess); // 设置提示的内容
                mProgressDialog.setCanceledOnTouchOutside(false); // 点击外部不会销毁


                // activity销毁了弹窗会报错
                if (!activity.isFinishing())
                    mProgressDialog.show();
            }
        });
    }


    /**
     * 弹窗提示正在进行操作，已作了处理可以在子线程中调用
     *
     * @param resID      要提示的内容ID
     * @param cancelable 是否可取消
     */
    public void showProgressDialog(final Activity activity, final int resID, final boolean cancelable) {
        final String mess = activity.getString(resID);
        // 主线程中操作
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mProgressDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
                mProgressDialog.setIndeterminate(true); //
                mProgressDialog.setCancelable(cancelable); // 用户是否可以取消
                if (!CommonUtils.isNull(mess))
                    mProgressDialog.setMessage(mess); // 设置提示的内容
                mProgressDialog.setCanceledOnTouchOutside(false); // 点击外部不会销毁


                // activity销毁了弹窗会报错
                if (!activity.isFinishing()) {
                    if (activity != null) {
                        if (!mProgressDialog.isShowing()) {
                            mProgressDialog.show();
                        }
                    }
                }
            }
        });
    }


    public boolean getProgressDialogState() {
        return mProgressDialog.isShowing();
    }

    public void showProgressDialogForCentet(final Activity activity, final String mess, final boolean cancelable) {
        // 主线程中操作
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mProgressDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
                mProgressDialog.setIndeterminate(true); //
                mProgressDialog.setCancelable(cancelable); // 用户是否可以取消
                if (mess != null && mess != "")
                    mProgressDialog.setMessage(mess); // 设置提示的内容
                mProgressDialog.setCanceledOnTouchOutside(false); // 点击外部不会销毁

                // activity销毁了弹窗会报错
                if (!activity.isFinishing())
                    mProgressDialog.show();
                mProgressDialog.getWindow().setGravity(Gravity.CENTER);
                WindowManager.LayoutParams params =
                        mProgressDialog.getWindow().getAttributes();
                params.width = 400;
                params.height = 400;
                mProgressDialog.getWindow().setAttributes(params);
            }
        });
    }

    /**
     * 销毁加载提示框，已作了处理，可以在子线程中调用
     */
    public void dismissProgressDialog(Activity activity) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // 主线程中操作
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        });
    }

    private ProgressDialog mRecProgressDialog;

    //有进度条的Dialog(长方形)
    public void showRecProgressDialog(final Activity activity, final String msg, final int max) {
        // 主线程中操作
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mRecProgressDialog = new ProgressDialog(activity); // 得到一个对象
                mRecProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置为矩形进度条
                mRecProgressDialog.setTitle("提示");
                mRecProgressDialog.setMessage(msg);
                //mRecProgressDialog.setIcon(R.drawable.ic_launcher);
                mRecProgressDialog.setIndeterminate(false); // 设置进度条是否为不明确
                //mRecProgressDialog.setCancelable(true);
                mRecProgressDialog.setCancelable(false);
                mRecProgressDialog.setMax(max); // 设置进度条的最大值
                mRecProgressDialog.setProgress(0); // 设置当前默认进度为 0
                //mRecProgressDialog.setSecondaryProgress(1000); // 设置第二条进度值为100

                // 为进度条添加取消按钮
                //		myDialog.setButton("取消", new DialogInterface.OnClickListener() {
                //			public void onClick(DialogInterface dialog, int which) {
                //				myDialog.cancel();
                //			}
                //		});

                // activity销毁了弹窗会报错
                if (!activity.isFinishing())
                    mRecProgressDialog.show(); // 显示进度条
            }
        });

    }

    public void setProgress(Activity activity, final int progress) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRecProgressDialog != null && mRecProgressDialog.isShowing()) {
                    mRecProgressDialog.setProgress(progress);
                }
            }
        });

    }

    public void dismissRecProgressDialog(Activity activity) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // 主线程中操作
                if (mRecProgressDialog != null) {
                    mRecProgressDialog.dismiss();
                    mRecProgressDialog = null;
                }
            }
        });
    }


}
