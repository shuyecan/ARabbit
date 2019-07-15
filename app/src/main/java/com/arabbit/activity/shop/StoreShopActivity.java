package com.arabbit.activity.shop;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.adapter.StoreShopAdapter;
import com.arabbit.entity.ShopInfoEntity;
import com.arabbit.entity.UserInfoEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/11.
 * 收藏店铺
 */

public class StoreShopActivity extends BaseActivity {

    @InjectView(R.id.lv_shop)
    RecyclerView lvShop;
    SocialModel model;
    List<UserInfoEntity> userEntitys;
    @InjectView(R.id.title_value)
    TextView titleValue;
    private StoreShopAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeshop_list);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initView();
        initData();
    }

    private void initData() {
        model.getFocusShopList(new IModelResult<List<ShopInfoEntity>>() {
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

    private void initView() {
        titleValue.setText("收藏店铺");
    }


}
