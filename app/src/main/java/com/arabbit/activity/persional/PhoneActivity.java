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
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

public class PhoneActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.layout_clear)
    RelativeLayout layoutClear;

    String phone = "";
    SocialModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        phone = CommonUtils.formatNull(getIntent().getStringExtra("phone"));
        initTile();
        initEdit();
    }

    private void initEdit() {
        etPhone.setText(phone);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (CommonUtils.isNull(s.toString())) {
                    layoutClear.setVisibility(View.GONE);
                } else {
                    layoutClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initTile() {
        titleValue.setText(R.string.phone);
        tvAdd.setText(R.string.save);
        tvAdd.setVisibility(View.VISIBLE);
        tvAdd.setTextColor(Color.YELLOW);
        tvAdd.setBackground(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_add, R.id.layout_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                String phone = UIUtils.getStrEditView(etPhone);
                updateUserInfoPhone(phone);
                break;
            case R.id.layout_clear:
                etPhone.setText("");
                break;
        }
    }


    public void updateUserInfoPhone(final String phone) {
        try {
            model.updateUserInfoPhone(phone, new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity loginEntity) {
                    ToastUtils.showToastShort(R.string.update_success);
                    Intent intent = new Intent();
                    intent.putExtra("value", phone);
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
