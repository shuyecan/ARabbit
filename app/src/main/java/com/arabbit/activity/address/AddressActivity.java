package com.arabbit.activity.address;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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


/**
 * 改  去掉邮编和地区
 */
public class AddressActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.et_area)
    EditText etArea;
    @InjectView(R.id.et_detail_address)
    EditText etDetailAddress;
    @InjectView(R.id.et_postal_code)
    EditText etPostalCode;


    String address = "";
    String postal_code = "";
    String area = "";
    SocialModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.inject(this);
        initTile();
        address = CommonUtils.formatNull(getIntent().getStringExtra("address"));
        postal_code = CommonUtils.formatNull(getIntent().getStringExtra("postal_code"));
        area = CommonUtils.formatNull(getIntent().getStringExtra("area"));
        etDetailAddress.setText(address);
        etPostalCode.setText(postal_code);
        etArea.setText(area);
        model = new SocialModel(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private void initTile() {
        titleValue.setText(R.string.detail_address);
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
                updateUserInfoDetailAddress();
                break;
        }
    }


    public void updateUserInfoDetailAddress() {
        final String resultValue = UIUtils.getStrEditView(etDetailAddress);
        try {
            model.updateUserInfoDetailAddress(resultValue, new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity loginEntity) {
                    ToastUtils.showToastShort(R.string.update_success);
                    Intent intent = new Intent();
                    intent.putExtra("value", resultValue);
//                    intent.putExtra("postal_code", postal_code);
//                    intent.putExtra("area", area);
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
