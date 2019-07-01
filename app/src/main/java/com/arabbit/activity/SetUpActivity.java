package com.arabbit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.arabbit.Contancts;
import com.arabbit.R;
import com.arabbit.activity.login.LoginActivity;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.DataCleanManager;
import com.arabbit.utils.DialogHelper;
import com.arabbit.utils.FileUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.SystemUtil;
import com.arabbit.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

public class SetUpActivity extends BaseActivity {


    @InjectView(R.id.title_value)
    TextView titleValue;
    SocialModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        ButterKnife.inject(this);
        titleValue.setText(R.string.setup);
        model = new SocialModel(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private DialogHelper dialoghelp;

    @OnClick({R.id.iv_back, R.id.layout_cache, R.id.layout_logou})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.layout_cache:

                dialoghelp = new DialogHelper();
                dialoghelp.showProgressDialog(mActivity, R.string.is_clear_cache, false);
                if (thread == null) {
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DataCleanManager.cleanInternalCache(SetUpActivity.this);
                            DataCleanManager.deleteFolderFile(FileUtils.getImgCacheDir(), false);
                            DataCleanManager.deleteFolderFile(Contancts.APPLICATION_FOLDER, true);
                            SystemUtil.refreshGallery(mActivity, Contancts.APPLICATION_FOLDER);
//                            DataCleanManager.deleteFolderFile(path, true);
                            dialoghelp.dismissProgressDialog(mActivity);
                            thread = null;
                        }
                    });
                    thread.start();
                }
                break;
            case R.id.layout_logou:
                logout();
                break;
        }
    }

    public void logout() {
        try {
            model.logout(new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity emptyEntity) {
                    ToastUtils.showToastShort(R.string.logut_success);
                    //改变登录状态
                    SPUtils.setString("isLogin", "2");//已经退出，下次需要登录  登录的时候改变为1
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void OnError(ApiException e) {
                    ToastUtils.showToastShort(e.message);
                }

                @Override
                public void AddSubscription(Subscription subscription) {
                    addSubscription(subscription);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    Thread thread;
}
