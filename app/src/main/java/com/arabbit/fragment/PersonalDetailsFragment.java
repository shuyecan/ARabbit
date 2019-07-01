package com.arabbit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.address.AddressActivity;
import com.arabbit.activity.address.AreaActivity;
import com.arabbit.activity.persional.NickNameActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.activity.persional.PersonalHomepageActivity;
import com.arabbit.activity.persional.ProfileActivity;
import com.arabbit.activity.persional.SetUpGenderActivity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

import static android.app.Activity.RESULT_OK;
import static com.arabbit.R.string.postal_code;
import static com.arabbit.activity.persional.StudentInfoActivity.UPDATE_ADDRESS;
import static com.arabbit.activity.persional.StudentInfoActivity.UPDATE_AREA;
import static com.arabbit.activity.persional.StudentInfoActivity.UPDATE_HEAD_IMAGE;
import static com.arabbit.activity.persional.StudentInfoActivity.UPDATE_MAIN;
import static com.arabbit.activity.persional.StudentInfoActivity.UPDATE_NICKNAME;
import static com.arabbit.activity.persional.StudentInfoActivity.UPDATE_PROFILE;
import static com.arabbit.activity.persional.StudentInfoActivity.UPDATE_SEAT_HEAD_IMAGE;


/**
 * 登陆用户详细信息（之前）
 */
public class PersonalDetailsFragment extends BaseFragment {

    @InjectView(R.id.iv_head_image)
    ImageView ivHeadImage;
    @InjectView(R.id.iv_seat_head_image)
    ImageView ivSeatHeadImage;
    @InjectView(R.id.tv_nick_name)
    TextView tvNickName;
    @InjectView(R.id.layout_gender)
    LinearLayout layoutGender;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_main)
    TextView tvMain;
    @InjectView(R.id.tv_account)
    TextView tvAccount;
    @InjectView(R.id.layout_main)
    LinearLayout layoutMain;
    @InjectView(R.id.tv_area)
    TextView tvArea;
    @InjectView(R.id.layout_area)
    LinearLayout layoutArea;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.layout_address)
    LinearLayout layoutAddress;
    @InjectView(R.id.tv_profile)
    TextView tvProfile;
    @InjectView(R.id.layout_profile)
    LinearLayout layoutProfile;

    SocialModel model;




    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);
        ButterKnife.inject(this, view);
        model = new SocialModel(this);
        getUserInfo();
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            getUserInfo();
        }
    }

    public void getUserInfo() {
        String user_id = SPUtils.getString("user_id", "");
        try {
            model.getUserInfo(user_id, new IModelResult<GetUserInfoEntity>() {
                @Override
                public void OnSuccess(GetUserInfoEntity entity) {
                    if (!CommonUtils.isNull(entity)) {
                        headUrl = entity.getAvatar_img();
                        nickName = entity.getNickname();
                        gender = entity.getGender();
                        String type = CommonUtils.formatNull(entity.getRole());
                        main = entity.getHomepage();
                        phone = entity.getPhone();
                        address = entity.getAddress();
                        profile = entity.getIntroduction();
                        String account = SPUtils.getString("phone", "");
                        if (!CommonUtils.isNull(headUrl)) {
                            ImgLoaderUtils.setImageloader(Config.IMG_URL+headUrl, ivHeadImage);
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
                        tvMain.setText(main);
//                        tvArea.setText(area);
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


    String headUrl = "";
    String nickName = "";
    String main = "";
    String gender = "";
    String address = "";
    String school = "";
    String phone = "";
    String profile = "";


    @OnClick({R.id.iv_head_image, R.id.iv_seat_head_image, R.id.layout_nick_name,
             R.id.layout_main, R.id.layout_area, R.id.layout_address, R.id.layout_profile})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_head_image:
                intent.setClass(mActivity, PersonalAvatarActivity.class);
                intent.putExtra("url", headUrl);
                intent.putExtra("type", "avatar_img");
                startActivityForResult(intent, UPDATE_HEAD_IMAGE);
                break;
            case R.id.iv_seat_head_image:
                intent.setClass(mActivity, PersonalAvatarActivity.class);
//                intent.putExtra("url", seatHeadUrl);
                intent.putExtra("type", "seat_img");
                startActivityForResult(intent, UPDATE_SEAT_HEAD_IMAGE);
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
                intent.setClass(mActivity, AreaActivity.class);
//                intent.putExtra("area", area);
                startActivityForResult(intent, UPDATE_AREA);
                break;
            case R.id.layout_address:

                intent.setClass(mActivity, AddressActivity.class);
                intent.putExtra("address", address);
                intent.putExtra("postal_code", postal_code);
//                intent.putExtra("area", area);
                startActivityForResult(intent, UPDATE_ADDRESS);
                break;
            case R.id.layout_profile:
                intent.setClass(mActivity, ProfileActivity.class);
                intent.putExtra("profile", profile);
                startActivityForResult(intent, UPDATE_PROFILE);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String value = data.getStringExtra("value");
            switch (requestCode) {
                case UPDATE_HEAD_IMAGE:
                    headUrl = value;
                    ImgLoaderUtils.setImageloader(value, ivHeadImage);
                    break;
                case UPDATE_SEAT_HEAD_IMAGE:
//                    seatHeadUrl = value;
                    ImgLoaderUtils.setImageloader(value, ivSeatHeadImage);
                    break;
                case UPDATE_NICKNAME:
                    nickName = value;
                    tvNickName.setText(value);
                    break;
                case UPDATE_MAIN:
                    main = value;
                    tvMain.setText(value);
                    break;
                case UPDATE_AREA:
//                    area = value;
                    tvArea.setText(value);
                    break;
                case UPDATE_ADDRESS:
                    address = value;
                    tvAddress.setText(value);
                    break;

                case UPDATE_PROFILE:
                    profile = value;
                    tvProfile.setText(value);
                    break;
            }
        }
    }


}
