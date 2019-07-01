package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.address.AddressActivity;
import com.arabbit.activity.persional.PhoneActivity;
import com.arabbit.activity.persional.ProfileActivity;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.dialog.address.AddressDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/8/3.
 */

public class UserMoreActivity  extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_area)
    TextView tvArea;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_profile)
    TextView tvProfile;
    @InjectView(R.id.layout_area)
    LinearLayout layoutArea;
    @InjectView(R.id.layout_profile)
    LinearLayout layoutProfile;
    @InjectView(R.id.layout_address)
    LinearLayout layoutAddress;
    @InjectView(R.id.layout_phone)
    LinearLayout layoutPhone;

    SocialModel model;
    String user_id ="";
    String area = "";
    String address = "";
    String phone = "";
    String profile = "";

    public static final int UPDATE_AREA = 106;
    public static final int UPDATE_ADDRESS = 108;
    public static final int UPDATE_PHONE = 111;
    public static final int UPDATE_PROFILE = 109;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermore_info);
        ButterKnife.inject(this);
        user_id = SPUtils.getString("user_id", "");
        model = new SocialModel(this);
        initTitle();
        getUserInfo();
        initAddress();

    }

    GetUserInfoEntity userInfoEntity;
    private AddressDialog addressDialog;

    public void getUserInfo() {
        try {
            model.getUserInfo(user_id, new IModelResult<GetUserInfoEntity>() {
                @Override
                public void OnSuccess(GetUserInfoEntity entity) {
                    if (!CommonUtils.isNull(entity)) {
                        userInfoEntity = entity;
                         area = entity.getAddress();
                         phone = entity.getPhone();
                         address = entity.getDetail_address();
                         profile = entity.getIntroduction();
                        tvArea.setText(area);
                        tvPhone.setText(phone);
                        tvAddress.setText(address);
                        tvProfile.setText(profile);

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

    private void initAddress() {
        addressDialog = new AddressDialog(this);
        addressDialog.setOnClick(new AddressDialog.OnClick() {
            @Override
            public void onConfirm(String province, String city, String area, String address) {
                String region = "";
                region = province + city;
                updateUserArea(region);

            }
        });
    }
    private void initTitle() {

        titleValue.setText("个人资料信息");
    }


    @OnClick({R.id.iv_back,
            R.id.layout_area,
            R.id.layout_address,
            R.id.layout_phone,
            R.id.layout_profile,})

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.layout_area:
                addressDialog.open();
                break;
            case R.id.layout_address:
                intent.setClass(mActivity, AddressActivity.class);
                intent.putExtra("address", address);
                intent.putExtra("area", area);
                startActivityForResult(intent, UPDATE_ADDRESS);
                break;
            case R.id.layout_profile:
                intent.setClass(mActivity, ProfileActivity.class);
                intent.putExtra("profile", profile);
                intent.putExtra("title", "个人简介");
                startActivityForResult(intent, UPDATE_PROFILE);
                break;

            case R.id.layout_phone:
                intent.setClass(mActivity, PhoneActivity.class);
                intent.putExtra("phone", phone);
                startActivityForResult(intent, UPDATE_PHONE);
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }



    public void updateUserArea(final String area) {
        try {
            model.updateUserInfoArea(area, new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity loginEntity) {
                    ToastUtils.showToastShort(R.string.update_success);
                    tvArea.setText(area);
                    userInfoEntity.setAddress(area);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String value = data.getStringExtra("value");
            switch (requestCode) {
                case UPDATE_ADDRESS:
                    address = value;
                    tvAddress.setText(value);
                    break;
                case UPDATE_PROFILE:
                    profile = value;
                    tvProfile.setText(value);
                    break;
                case UPDATE_PHONE:
                    phone = value;
                    tvPhone.setText(value);
                    break;
            }
        }
    }



}
