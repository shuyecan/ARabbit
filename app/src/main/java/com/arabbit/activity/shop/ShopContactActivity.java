package com.arabbit.activity.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.arabbit.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ShopContactActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shop_contact);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.store_shop, R.id.near_shop, R.id.miaob_sales, R.id.layout_prize, R.id.layout_coupon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.store_shop:
                Intent sintent = new Intent(getApplicationContext(), StoreShopActivity.class);
                startActivity(sintent);
                break;
            case R.id.near_shop:
                Intent nintent = new Intent(getApplicationContext(), StoreNearShopActivity.class);
                startActivity(nintent);
                break;
            case R.id.miaob_sales:
                Intent mintent = new Intent(getApplicationContext(), StorembShopActivity.class);
                startActivity(mintent);
                break;
            case R.id.layout_prize:
                Intent pintent = new Intent(getApplicationContext(), StoreptShopActivity.class);
                startActivity(pintent);
                break;
            case R.id.layout_coupon:
                Intent cintent = new Intent(getApplicationContext(), StorecpShopActivity.class);
                startActivity(cintent);
                break;

        }
    }
}
