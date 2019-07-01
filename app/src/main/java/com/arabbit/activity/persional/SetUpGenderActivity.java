package com.arabbit.activity.persional;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

public class SetUpGenderActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.iv_boy)
    ImageView ivBoy;
    @InjectView(R.id.iv_girl)
    ImageView ivGirl;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    String gender = "";
    SocialModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_gender);
        ButterKnife.inject(this);
        titleValue.setText(R.string.setup);
        tvAdd.setText(R.string.save);
        tvAdd.setVisibility(View.VISIBLE);
        tvAdd.setBackground(null);
        tvAdd.setTextColor(Color.YELLOW);

        model = new SocialModel(this);
        gender = CommonUtils.formatNull(getIntent().getStringExtra("gender"));
        if (gender.equals(getString(R.string.boy))) {
            ivBoy.setVisibility(View.VISIBLE);
            ivGirl.setVisibility(View.GONE);
        } else {
            ivBoy.setVisibility(View.GONE);
            ivGirl.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.iv_back, R.id.tv_add, R.id.layout_boy, R.id.layout_girl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add:
                updateUserInfoGender();
                break;
            case R.id.layout_boy:
                gender = getString(R.string.boy);
                ivBoy.setVisibility(View.VISIBLE);
                ivGirl.setVisibility(View.GONE);
                break;
            case R.id.layout_girl:
                ivBoy.setVisibility(View.GONE);
                ivGirl.setVisibility(View.VISIBLE);
                gender = getString(R.string.girl);
                break;
        }
    }


    public void updateUserInfoGender() {
        try {
            model.updateUserInfoGender(gender, new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity loginEntity) {
                    ToastUtils.showToastShort(R.string.update_success);
                    Intent intent = new Intent();
                    intent.putExtra("value", gender);
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
