package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.address.AddressActivity;
import com.arabbit.activity.persional.NickNameActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.activity.persional.PersonalHomepageActivity;
import com.arabbit.activity.persional.PhoneActivity;
import com.arabbit.activity.persional.ProfileActivity;
import com.arabbit.activity.persional.UpdatePasswordActivity;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.dialog.address.AddressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/14.
 */

public class ShopdetailsActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.iv_head_image)
    ImageView ivHeadImage;
    @InjectView(R.id.iv_seat_head_image)
    ImageView ivSeatHeadImage;
    @InjectView(R.id.iv_other)
    ImageView ivOther;
    @InjectView(R.id.tv_nick_name)
    TextView tvNickName;
    @InjectView(R.id.tv_type)
    TextView tvType;
//    @InjectView(R.id.tv_main)
//    TextView tvMain;
    @InjectView(R.id.tv_account)
    TextView tvAccount;
    @InjectView(R.id.tv_area)
    TextView tvArea;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
//    @InjectView(R.id.tv_profile)
//    TextView tvProfile;


    @InjectView(R.id.layout_main)
    LinearLayout layoutMain;
    @InjectView(R.id.layout_area)
    LinearLayout layoutArea;
    @InjectView(R.id.layout_profile)
    LinearLayout layoutProfile;
    @InjectView(R.id.layout_nick_name)
    LinearLayout layoutNickName;
    @InjectView(R.id.layout_address)
    LinearLayout layoutAddress;
    @InjectView(R.id.layout_phone)
    LinearLayout layoutPhone;
    @InjectView(R.id.layout_password)
    LinearLayout layoutPassword;
    @InjectView(R.id.layout_update_password)
    LinearLayout layoutUpdatePassword;


    SocialModel model;
    public static final int UPDATE_HEAD_IMAGE = 101;
    public static final int UPDATE_SEAT_HEAD_IMAGE = 102;
    public static final int UPDATE_NICKNAME = 103;
    public static final int UPDATE_MAIN = 105;
    public static final int UPDATE_AREA = 106;
    public static final int UPDATE_TYPE = 107;
    public static final int UPDATE_ADDRESS = 108;
    public static final int UPDATE_PROFILE = 109;
    public static final int UPDATE_ACCOUNT = 110;
    public static final int UPDATE_PHONE = 111;
    public static final int UPDATE_PASSWORD = 113;


    String target_user_id = "";

    private AddressDialog addressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        target_user_id = getIntent().getStringExtra("user_id");
        ButterKnife.inject(this);
        initViewList();
        initTitle();
        model = new SocialModel(this);
        getUserInfo();
        initAddress();

    }

    private void initTitle() {
        titleValue.setText(R.string.detail_info);
        String user_id = SPUtils.getString("user_id", "");
        int type = SPUtils.getInt("type", 1);
        if (!user_id.equals(target_user_id) || type == 2) {
            for (TextView tv : tvList) {
                tv.setCompoundDrawables(null, null, null, null);
                tv.setPadding(0, 0, 30, 0);
            }
            for (LinearLayout linear : layoutList) {
                linear.setEnabled(false);
            }
            ivHeadImage.setEnabled(false);
            ivOther.setVisibility(View.INVISIBLE);
        }
        if (!user_id.equals(target_user_id) || type == 2) {
            layoutPassword.setVisibility(View.GONE);
        }
    }

    List<TextView> tvList;
    List<LinearLayout> layoutList;

    private void initViewList() {
        tvList = new ArrayList<>();
        tvList.add(tvNickName);
       // tvList.add(tvMain);
        tvList.add(tvArea);
        tvList.add(tvPhone);
        tvList.add(tvAddress);
        //tvList.add(tvProfile);

        layoutList = new ArrayList<>();
        layoutList.add(layoutNickName);
       // layoutList.add(layoutMain);
        layoutList.add(layoutArea);
        layoutList.add(layoutAddress);
        layoutList.add(layoutPhone);
        layoutList.add(layoutProfile);
        layoutList.add(layoutUpdatePassword);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    String headUrl = "";
    String nickName = "";
    String main = "";
    String area = "";
    String address = "";
    String phone = "";
    String account = "";
    String profile = "";
    String password = "";

    GetUserInfoEntity userInfoEntity;

    public void getUserInfo() {
        try {
            model.getUserInfo(target_user_id, new IModelResult<GetUserInfoEntity>() {
                @Override
                public void OnSuccess(GetUserInfoEntity entity) {
                    if (!CommonUtils.isNull(entity)) {
                        userInfoEntity = entity;
                        headUrl = entity.getAvatar_img();
                        nickName = entity.getNickname();
                        String type = CommonUtils.formatNull(entity.getRole());
                        main = entity.getHomepage();
                        area = entity.getAddress();
                        phone = entity.getPhone();
                        address = entity.getDetail_address();
                        profile = entity.getIntroduction();
                        account = entity.getAccount();
                        password= entity.getPassword();
                        if (!CommonUtils.isNull(headUrl)) {
//                            ImgLoaderUtils.setImageloader(Config.BASE_URL+headUrl, ivHeadImage);
                            ImgLoaderUtils.setImageloader(Config.IMG_URL + headUrl, ivHeadImage);
                        }
                        tvAccount.setText(account);
                        tvNickName.setText(nickName);
                        if (type.equals("1")){
                            tvType.setText("公司");
                        }else if(type.equals("2")){
                            tvType.setText("个人");
                        }else if(type.equals("3")){
                            tvType.setText("店铺");
                        }
                       // tvMain.setText(main);
                        tvArea.setText(area);
                        tvPhone.setText(phone);
                        tvAddress.setText(address);
                       // tvProfile.setText(profile);

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


    @OnClick({R.id.iv_back, R.id.iv_head_image,
            R.id.layout_nick_name,
           R.id.layout_phone,
            R.id.layout_main,
            R.id.layout_area,
            R.id.layout_address,
            R.id.layout_profile,
            R.id.layout_update_password})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_head_image:
                intent.setClass(mActivity, PersonalAvatarActivity.class);
                intent.putExtra("url", headUrl);
                intent.putExtra("type", "avatar_img");
                startActivityForResult(intent, UPDATE_HEAD_IMAGE);
                break;
            case R.id.layout_nick_name:
                intent.setClass(mActivity, NickNameActivity.class);
                intent.putExtra("name", nickName);
                startActivityForResult(intent, UPDATE_NICKNAME);
                break;
            case R.id.layout_main:

                intent.setClass(mActivity, PersonalHomepageActivity.class);
                intent.putExtra("main", main);
                startActivityForResult(intent, UPDATE_MAIN);
                break;
            case R.id.layout_area:
                addressDialog.open();
                break;
            case R.id.layout_address:

                intent.setClass(mActivity, AddressActivity.class);
                intent.putExtra("address", address);
//                intent.putExtra("postal_code", postal_code);
                intent.putExtra("area", area);
                startActivityForResult(intent, UPDATE_ADDRESS);
                break;
            case R.id.layout_profile:
                intent.setClass(mActivity, ProfileActivity.class);
                intent.putExtra("profile", profile);
                intent.putExtra("title", "店铺简介");
                startActivityForResult(intent, UPDATE_PROFILE);
                break;

            case R.id.layout_phone:
                intent.setClass(mActivity, PhoneActivity.class);
                intent.putExtra("phone", phone);
                startActivityForResult(intent, UPDATE_PHONE);
                break;
            case R.id.layout_update_password:
                intent.setClass(mActivity, UpdatePasswordActivity.class);
                intent.putExtra("password", password);
                startActivityForResult(intent, UPDATE_PASSWORD);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra("userInfo", userInfoEntity));
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String value = data.getStringExtra("value");
            switch (requestCode) {
                case UPDATE_HEAD_IMAGE:
                    headUrl = value;
                    ImgLoaderUtils.setImageloader(Config.IMG_URL+value, ivHeadImage);
                    userInfoEntity.setAvatar_img(headUrl);
                    break;
                case UPDATE_SEAT_HEAD_IMAGE:
//                    seatHeadUrl = value;
                    ImgLoaderUtils.setImageloader(value, ivSeatHeadImage);
                    break;
                case UPDATE_NICKNAME:
                    nickName = value;
                    tvNickName.setText(value);
                    userInfoEntity.setNickname(value);
                    break;

                case UPDATE_MAIN:
                    main = value;
                   // tvMain.setText(value);
                    userInfoEntity.setHomepage(value);
                    break;
                case UPDATE_PHONE:
                    phone = value;
                    tvPhone.setText(value);
                    userInfoEntity.setPhone(value);
                    break;
                case UPDATE_ACCOUNT:
                    account = value;
                    tvAccount.setText(value);
                    break;
                case UPDATE_AREA:
                    area = value;
                    tvArea.setText(value);
                    userInfoEntity.setAddress(value);
                    break;
                case UPDATE_ADDRESS:
                    address = value;
                    tvAddress.setText(value);
                    userInfoEntity.setDetail_address(value);
//                    tvArea.setText(area);
                    break;
                case UPDATE_PROFILE:
                    profile = value;
                    //tvProfile.setText(value);
                    userInfoEntity.setIntroduction(value);
                    break;
                case UPDATE_PASSWORD:
                    password = value;
                    userInfoEntity.setPassword(value);
                    break;
            }
        }
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
}