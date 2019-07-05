package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.adapter.StoreShopAdapter;
import com.arabbit.entity.UserInfoEntity;
import com.arabbit.model.SocialModel;
import com.arabbit.utils.LocationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/11/11.
 * 抽奖活动的店铺
 */

public class StoreptShopActivity extends BaseActivity {

    SocialModel model;
    List<UserInfoEntity> userEntitys;
    @InjectView(R.id.sch_adress)
    EditText schAdress;
    private StoreShopAdapter adapter;
    private double londe,latud;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initDate();
    }


    private void initDate() {
        LocationUtils.getLocation(new LocationUtils.MyLocationListener() {
            @Override
            public void result(AMapLocation location) {
                latud = location.getLatitude();//获取纬度
                londe = location.getLongitude();//获取经度
                schAdress.setText(londe+","+latud);
            }
        });
    }




}

