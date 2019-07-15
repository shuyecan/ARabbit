package com.arabbit.activity.shop;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.adapter.StoreShopAdapter;
import com.arabbit.entity.ShopInfoEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.LocationUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/11.
 * 打折促销的店铺
 */

public class StorecpShopActivity extends BaseActivity {

    SocialModel model;
    @InjectView(R.id.sch_adress)
    EditText schAdress;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.close)
    ImageView close;
    private StoreShopAdapter adapter;
    private double londe, latud;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initDate();
    }


    private void initDate() {
        titleValue.setText("打折店铺");
        LocationUtils.getLocation(new LocationUtils.MyLocationListener() {
            @Override
            public void result(AMapLocation location) {
                latud = location.getLatitude();//获取纬度
                londe = location.getLongitude();//获取经度
                schAdress.setText(location.getAddress());
                getNearbyCpList(20,"");
            }
        });
    }


    private void  getNearbyCpList(int far,String type){
        model.getNearbyCpList(latud+"",londe+"",far,type,new IModelResult<List<ShopInfoEntity>>() {
            @Override
            public void OnSuccess(List<ShopInfoEntity> shopInfoEntities) {

            }

            @Override
            public void OnError(ApiException e) {

            }

            @Override
            public void AddSubscription(Subscription subscription) {

            }
        });
    }


    @OnClick({R.id.iv_back, R.id.close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.close:
                finish();
                break;
        }
    }
}
