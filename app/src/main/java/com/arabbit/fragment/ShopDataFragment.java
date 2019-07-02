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
import com.arabbit.activity.shop.ShopcpActivity;
import com.arabbit.activity.shop.ShopdetailsActivity;
import com.arabbit.activity.shop.ShopmbActivity;
import com.arabbit.activity.shop.ShopptActivity;
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

/**
 * Created by Administrator on 2018/10/22.
 */

public class ShopDataFragment extends BaseFragment {

    @InjectView(R.id.iv_image)
    ImageView ivImage;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_account)
    TextView tvAccount;
    @InjectView(R.id.shop_address)
    LinearLayout layoutAddress;
    @InjectView(R.id.opening)
    LinearLayout layoutOpening;
    @InjectView(R.id.line_phone)
    LinearLayout layoutPhone;

    SocialModel model;
    @InjectView(R.id.tv_shop_address)
    TextView tvShopAddress;
    @InjectView(R.id.tv_phone)
    TextView tv_phone;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_data, container, false);
        ButterKnife.inject(this, view);
        model = new SocialModel(this);
        getUserInfo();
        return view;
    }

    public void getUserInfo() {
        String user_id = SPUtils.getString("user_id", "");
        try {
            model.getUserInfo(user_id, new IModelResult<GetUserInfoEntity>() {
                @Override
                public void OnSuccess(GetUserInfoEntity entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initUserInfo(entity);

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

    private void initUserInfo(GetUserInfoEntity entity) {
        String url = entity.getAvatar_img();
        String name = entity.getNickname();
        String account = entity.getAccount();
        String type = CommonUtils.formatNull(entity.getRole());
        String address = entity.getAddress();
        tvName.setText(name);
        if (!CommonUtils.isNull(url)) {
//            ImgLoaderUtils.setImageloader(Config.BASE_URL + url, ivImage);
            ImgLoaderUtils.setImageloader(Config.IMG_URL + url, ivImage);
        }
        tvAddress.setText(address);
        tvAccount.setText(account);
        tvShopAddress.setText(entity.getDetail_address());
        tv_phone.setText(entity.getPhone());
        if (type.equals("1")) {
            tvType.setText("公司");
        } else if (type.equals("2")) {
            tvType.setText("个人");
        } else if (type.equals("3")) {
            tvType.setText("店铺");
        }
    }

    @OnClick({R.id.iv_image, R.id.tv_more, R.id.miaob_sales, R.id.layout_prize, R.id.layout_coupon})
    public void onClick(View view) {
        String user_id = SPUtils.getString("user_id", "");
        switch (view.getId()) {
            case R.id.iv_image:
                Intent infointent = new Intent(mActivity, ShopdetailsActivity.class);
                infointent.putExtra("user_id", user_id);
                startActivityForResult(infointent, 701);
                break;
            case R.id.tv_more:
                Intent intent = new Intent(mActivity, ShopdetailsActivity.class);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent, 702);
                break;
            case R.id.miaob_sales:
                Intent mintent = new Intent(mActivity, ShopmbActivity.class);
                startActivity(mintent);
                break;
            case R.id.layout_prize:
                Intent pintent = new Intent(mActivity, ShopptActivity.class);
                startActivity(pintent);
                break;
            case R.id.layout_coupon:
                Intent cintent = new Intent(mActivity, ShopcpActivity.class);
                startActivity(cintent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
