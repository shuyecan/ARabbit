package com.arabbit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arabbit.R;
import com.arabbit.activity.shop.StoreShopActivity;
import com.arabbit.activity.shop.StorembShopActivity;
import com.arabbit.activity.shop.StoreptShopActivity;
import com.arabbit.activity.shop.StorecpShopActivity;
import com.arabbit.activity.shop.StoreNearShopActivity;
import com.arabbit.model.SocialModel;
import com.arabbit.utils.SPUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/11.
 */

public class ShopContactFragment extends BaseFragment{

    SocialModel model;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_contact, container, false);
        ButterKnife.inject(this, view);
        model = new SocialModel(this);
        return view;
    }
    @OnClick({R.id.store_shop,R.id.near_shop, R.id.miaob_sales, R.id.layout_prize, R.id.layout_coupon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.store_shop:
                Intent sintent = new Intent(mActivity, StoreShopActivity.class);
                startActivity(sintent);
            case R.id.near_shop:
                Intent nintent = new Intent(mActivity, StoreNearShopActivity.class);
                startActivity(nintent);
                break;
            case R.id.miaob_sales:
                Intent mintent = new Intent(mActivity, StorembShopActivity.class);
                startActivity(mintent);
                break;
            case R.id.layout_prize:
                Intent pintent = new Intent(mActivity, StoreptShopActivity.class);
                startActivity(pintent);
                break;
            case R.id.layout_coupon:
                Intent cintent = new Intent(mActivity, StorecpShopActivity.class);
                startActivity(cintent);
                break;

        }
    }



}
