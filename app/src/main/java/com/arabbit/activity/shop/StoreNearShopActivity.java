package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.adapter.StoreShopAdapter;
import com.arabbit.entity.UserInfoEntity;
import com.arabbit.model.SocialModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/11/11.
 */

public class StoreNearShopActivity extends BaseActivity {

    @InjectView(R.id.lv_shop)
    ListView lvShop;
    SocialModel model;
    List<UserInfoEntity> userEntitys;
    private StoreShopAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        userEntitys = new ArrayList<>();
        adapter = new StoreShopAdapter(userEntitys,StoreNearShopActivity.this);
        lvShop.setAdapter(adapter);
        lvShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserInfoEntity userinfoEntity = (UserInfoEntity)adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(StoreNearShopActivity.this, ShopdataActivity.class);
                int user_id = userinfoEntity.getUser_id();
                intent.putExtra("user_id",user_id+"");
                startActivity(intent);



            }
        });

    }




}

