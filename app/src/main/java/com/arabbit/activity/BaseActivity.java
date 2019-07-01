package com.arabbit.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.arabbit.model.IViewLoad;
import com.arabbit.view.dialog.LoadingDialog;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 *
 */
public class BaseActivity extends AppCompatActivity implements IViewLoad {
    private static List<BaseActivity> activityList = new LinkedList<>();

    protected String Tag;

    protected Activity mActivity;

    private CompositeSubscription mCompositeSubscription;
    protected LoadingDialog mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tag = this.getClass().getSimpleName();
        mActivity = this;
        activityList.add(this);
        mLoading = new LoadingDialog(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityList.remove(this);
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 销毁Activity
     *
     * @param clazz
     */
    public static void destroyActivity(Class<? extends BaseActivity> clazz) {
        if (activityList == null || activityList.size() == 0) {
            return;
        }
        for (BaseActivity activity : activityList) {
            if (clazz.getName().equals(activity.getClass().getName())) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }

    }

    public static boolean isActivityExist(Class<? extends BaseActivity> clazz) {
        if (activityList == null || activityList.size() <= 0) {
            return false;
        }
        String clazzName = clazz.getName();
        for (BaseActivity activity : activityList) {
            if (clazzName.equals(activity.getClass().getName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 保证Activity集合中只剩余登录界面
     */
  /*  public static void remainLoginActivity()
    {
        if (activityList == null || activityList.size() == 0)
        {
            return;
        }
        for (BaseActivity activity : activityList)
        {
            if (!activity.getClass().getName().equals(LoginActivity.class.getName()))
            {
                if (!activity.isFinishing())
                {
                    activity.finish();
                }
            }
        }
    }*/
    public static void remainMainActivity() {
        if (activityList == null || activityList.size() == 0) {
            return;
        }
        for (BaseActivity activity : activityList) {
            if (!activity.getClass().getName().equals(MainActivity.class.getName())) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
        // ActivityUtils.jump2Previous(mActivity);
    }

    @Override
    public void showDialog() {
        if (isFinishing())
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isDestroyed())
                return;
        }
        if (mLoading == null)
            mLoading = new LoadingDialog(this);
        mLoading.showDialog(true);
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null)
            mLoading.hideDialog();
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    public void removeSubscription() {
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
