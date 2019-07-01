package com.arabbit.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.entity.LoginFromPasswordEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.ActivityUtils;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.MD5;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;
import com.arabbit.view.dialog.DialogWithContent;
import com.arabbit.view.spinner.widget.CustomSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

public class LoginActivity extends BaseActivity implements CustomSpinner.OnSpinnerListener {

    @InjectView(R.id.cp_type)
    CustomSpinner cpType;
    @InjectView(R.id.edit_account)
    EditText etAccount;
    @InjectView(R.id.edit_pwd)
    EditText editPwd;
    SocialModel model;
    String role ="2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initSpinner();
        model = new SocialModel(this);
        isLogin();
    }


    void isLogin() {
        String islogin = SPUtils.getString("isLogin", "");
        if (CommonUtils.isNull(islogin) || islogin.equals("2")) {//没有登录过
            return;
        } else {//登录过，自动登录
            String account = SPUtils.getString("account", "");
            String pass = SPUtils.getString("pass", "");
            String role= SPUtils.getString("role", "");
            if (CommonUtils.isNull(account)) return;
            if (CommonUtils.isNull(pass)) return;
            if (CommonUtils.isNull(role)) return;
            login(account, pass, role);
        }
    }

    private void login(final String account, final String pass, final String role) {
        try {
            model.loginFromPassword(account, pass, role, new IModelResult<LoginFromPasswordEntity>() {
                @Override
                public void OnSuccess(LoginFromPasswordEntity loginEntity) {
                    if (loginEntity != null) {
                        //保存基本信息
                        SPUtils.setString("user_id", "" + loginEntity.getUser_id());
                        SPUtils.setString("token", "" + loginEntity.getToken());
                        SPUtils.setString("first_register", "" + loginEntity.getFirst_register());
                        SPUtils.setString("avatar_img", "" + loginEntity.getAvatar_img());
                        SPUtils.setString("nickname", "" + loginEntity.getNickname());
                        SPUtils.setString("phone", "" + loginEntity.getPhone());
                        SPUtils.setString("account", account);
                        SPUtils.setString("pass", pass);
                        SPUtils.setString("role", role);
                        //改变登录状态
                        SPUtils.setString("isLogin", "1");//已经登录，下次自动登录  退出登录的时候改变为2
                        SPUtils.setInt("type", 1);//正常登陆
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
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

    private void initSpinner() {
        List<String> list = new ArrayList<>();

        String[] formats = getResources().getStringArray(R.array.select_type);

        Collections.addAll(list, formats);

        cpType.setResource(list, null);
        cpType.setSelection(0);
        cpType.setOnSpinnerListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_tourist_enter, R.id.tv_forget_pwd })
    public void onClick(View view) {
        String account = UIUtils.getStrEditView(etAccount);
        String password = UIUtils.getStrEditView(editPwd);
        switch (view.getId()) {
            case R.id.btn_login:
                if (CommonUtils.isNull(account)) {
                    ToastUtils.showToastShort(R.string.account_empty);
                    return;
                }
//                if (!UIUtils.isMobile(phone)) {
//                    ToastUtils.showToastShort(R.string.not_phone);
//                    return;
//                }
                if (CommonUtils.isNull(password)) {
                    ToastUtils.showToastShort(R.string.pwd_empty);
                    return;
                }
                password = MD5.getMd5(password);
                login(account, password, role);
                break;
            case R.id.tv_register:
                ActivityUtils.goToActivityNoFinish(mActivity, RegisterActivity.class);
                break;
            case R.id.tv_tourist_enter:

                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("type", 2);
                startActivity(intent);
                SPUtils.setInt("type", 2);//游客登陆
                finish();
                break;
            case R.id.tv_forget_pwd:
                showTip();
                break;

        }
    }

    DialogWithContent mDialogWithContent;

    public void showTip() {
        mDialogWithContent = new DialogWithContent(mActivity);
        mDialogWithContent.setContent(getString(R.string.cantact_manager));
        mDialogWithContent.setYesText(getString(R.string.confirm), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogWithContent.dismiss();
            }
        });
        mDialogWithContent.show();
    }



    String IdentityType;

    @Override
    public void onSpinnerItemSelected(String selected, View view) {
        switch (view.getId()) {
            case R.id.cp_type:
//                IdentityType = CommonUtils.formatNull(cpType.getSelectPosition() + 1);//记录index
                IdentityType = cpType.getSelectedItem();
                if(IdentityType.equals(getString(R.string.student))){
                    role = "1";
                }else if (IdentityType.equals(getString(R.string.average_user))){
                    role = "2";
                }
                else if (IdentityType.equals(getString(R.string.shop))){
                    role = "3";
                }
                break;
        }
    }

    @Override
    public void onSpinnerItemChanged(String changed, View view) {

    }
}
