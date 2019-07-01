package com.arabbit.activity.persional;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.MD5;
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

public class UpdatePasswordActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.et_pwd)
    EditText etPwd;
    @InjectView(R.id.et_confirm_pwd)
    EditText etConfirmPwd;

    String password = "";
    SocialModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        password = CommonUtils.formatNull(getIntent().getStringExtra("password"));
        initTile();
    }

    private void initTile() {
        titleValue.setText(R.string.update_password);
        tvAdd.setText(R.string.modify);
        tvAdd.setVisibility(View.VISIBLE);
        tvAdd.setTextColor(Color.YELLOW);
        tvAdd.setBackground(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                String password = UIUtils.getStrEditView(etPwd);
                if (CommonUtils.isNull(password)) {
                    ToastUtils.showToastShort(R.string.pwd_empty);
                    return;
                }
                if (password.length() < 6) {
                    ToastUtils.showToastShort(R.string.pwd_length);
                    return;
                }
                String passConfirm = UIUtils.getStrEditView(etConfirmPwd);
                if (!password.equals(passConfirm)) {
                    ToastUtils.showToastShort(R.string.pwd_inconsistent);
                    return;
                }
                updateUserPassword(password);
                break;

        }
    }


    public void updateUserPassword(final String password) {
        try {
            model.updateUserPassword(password, new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity loginEntity) {
                    ToastUtils.showToastShort(R.string.update_success);
                    Intent intent = new Intent();
                    intent.putExtra("value", password);
                    setResult(RESULT_OK, intent);
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
}
