package com.arabbit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.persional.NickNameActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.activity.persional.SetUpGenderActivity;
import com.arabbit.activity.persional.UpdatePasswordActivity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.activity.Userpage.UserCardActivity;
import com.arabbit.activity.Userpage.UserMoreActivity;
import com.arabbit.activity.Userpage.MygiftActivity;
import com.arabbit.activity.Userpage.MyprizeActivity;
import com.arabbit.activity.Userpage.MyshopprListActivity;
import com.arabbit.activity.Userpage.MyshopcpActivity;
import com.arabbit.activity.Userpage.MyshopmbActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2018/7/29.
 */

public class PersionInfoFragment extends BaseFragment {

    @InjectView(R.id.iv_head_image)
    ImageView ivHeadImage;
    @InjectView(R.id.tv_account)
    TextView tvAccount;
    @InjectView(R.id.tv_nick_name)
    TextView tvNickName;
    @InjectView(R.id.tv_gender)
    TextView tvGender;
    @InjectView(R.id.layout_gender)
    LinearLayout layoutGender;
    SocialModel model;

    public static final int UPDATE_HEAD_IMAGE = 301;
    public static final int UPDATE_NICKNAME = 302;
    public static final int UPDATE_GENDEY = 303;
    public static final int UPDATE_SEAT_HEAD_IMAGE = 304;
    public static final int UPDATE_PASSWORD = 305;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
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
                        password= entity.getPassword();
                        String account = entity.getAccount();
                        Log.e("aaa","头像地址："+headUrl);
                        if (!CommonUtils.isNull(headUrl)) {
                            ImgLoaderUtils.setImageloader(Config.IMG_URL + headUrl, ivHeadImage);
                        }

                        tvAccount.setText(account);
                        tvNickName.setText(nickName);
                        tvGender.setText(gender);
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
    String gender = "";
    String password = "";
    @OnClick({R.id.iv_head_image,  R.id.layout_nick_name, R.id.layout_gender,
            R.id.layout_prize, R.id.layout_gift, R.id.layout_card, R.id.layout_more
            ,R.id.layout_update_password ,R.id.layout_shoppr,R.id.layout_shopcp,R.id.layout_shopmb})

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
                intent.putExtra("type", "seat_img");
                startActivityForResult(intent, UPDATE_SEAT_HEAD_IMAGE);
                break;
            case R.id.layout_nick_name:
                intent.setClass(mActivity, NickNameActivity.class);
                intent.putExtra("name", nickName);
                startActivityForResult(intent, UPDATE_NICKNAME);
                break;
            case R.id.layout_gender:
                intent.setClass(mActivity, SetUpGenderActivity.class);
                intent.putExtra("gender", gender);
                startActivityForResult(intent, UPDATE_GENDEY);
                break;
            case R.id.layout_prize:
                intent.setClass(mActivity, MyprizeActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_gift:
                intent.setClass(mActivity, MygiftActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_card:
                intent.setClass(mActivity, UserCardActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_more:
                intent.setClass(mActivity, UserMoreActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_update_password:
                intent.setClass(mActivity, UpdatePasswordActivity.class);
                intent.putExtra("password", password);
                startActivityForResult(intent, UPDATE_PASSWORD);
                break;
            case R.id.layout_shoppr:
                intent.setClass(mActivity, MyshopprListActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_shopcp:
                intent.setClass(mActivity, MyshopcpActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_shopmb:
                intent.setClass(mActivity, MyshopmbActivity.class);
                startActivity(intent);
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
                    ImgLoaderUtils.setImageloader(Config.IMG_URL + value, ivHeadImage);
                    break;
                case UPDATE_NICKNAME:
                    nickName = value;
                    tvNickName.setText(value);
                    break;
                case UPDATE_GENDEY:
                    gender = value;
                    tvGender.setText(value);
                    break;
            }
        }
    }




}
