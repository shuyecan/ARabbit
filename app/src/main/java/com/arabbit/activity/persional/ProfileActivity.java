package com.arabbit.activity.persional;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

public class ProfileActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.et_profile)
    EditText etProfile;

    String profile = "";
    SocialModel model;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        title = getIntent().getStringExtra("title");
        initTile();

        profile = CommonUtils.formatNull(getIntent().getStringExtra("profile"));
        etProfile.setText(profile);
        model = new SocialModel(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private void initTile() {
        titleValue.setText(title);
        etProfile.setHint(title);

        tvAdd.setText(R.string.save);
        tvAdd.setVisibility(View.VISIBLE);
        tvAdd.setBackground(null);
        tvAdd.setTextColor(Color.YELLOW);
    }

    @OnClick({R.id.iv_back, R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                updateUserInfoIntroduction();
                break;
        }
    }


    public void updateUserInfoIntroduction() {
        profile = UIUtils.getStrEditView(etProfile);
        try {
            model.updateUserInfoIntroduction(profile, new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity loginEntity) {
                    ToastUtils.showToastShort(R.string.update_success);
                    Intent intent = new Intent();
                    intent.putExtra("value", profile);
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
