package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
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

/**
 * Created by Administrator on 2018/11/2.
 */

public class ShopdataActivity extends BaseActivity {

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
    String target_user_id = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdata);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        target_user_id = getIntent().getStringExtra("user_id");
        model = new SocialModel(this);
        getUserInfo();

    }

    public void getUserInfo() {

        try {
            model.getUserInfo(target_user_id, new IModelResult<GetUserInfoEntity>() {
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
        if (type.equals("1")){
            tvType.setText("公司");
        }else if(type.equals("2")){
            tvType.setText("个人");
        }else if(type.equals("3")){
            tvType.setText("店铺");
        }
        tvAddress.setText(address);
        tvAccount.setText(account);
//
    }

    @OnClick({R.id.iv_image, R.id.tv_more, R.id.miaob_sales, R.id.layout_prize, R.id.layout_coupon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_image:
                Intent infointent = new Intent(mActivity, ShopinfoActivity.class);
                infointent.putExtra("user_id", target_user_id);
                startActivity(infointent);
            case R.id.tv_more:
                Intent intent = new Intent(mActivity, ShopinfoActivity.class);
                intent.putExtra("user_id", target_user_id);
                startActivity(intent);
                break;
            case R.id.miaob_sales:
                Intent mintent = new Intent(mActivity, SeeShopmbActivity.class);
                mintent.putExtra("user_id", target_user_id);
                startActivity(mintent);
                break;
            case R.id.layout_prize:
                Intent pintent = new Intent(mActivity, SeeShopptActivity.class);
                pintent.putExtra("user_id", target_user_id);
                startActivity(pintent);
                break;
            case R.id.layout_coupon:
                Intent cintent = new Intent(mActivity, SeeShopcpActivity.class);
                cintent.putExtra("user_id", target_user_id);
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

}
