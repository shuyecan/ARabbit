package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.adapter.MyShopcpAdapter;
import com.arabbit.entity.HadShopcpEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/14.
 */

public class MyshopcpActivity extends BaseActivity {

    @InjectView(R.id.lv_prize)
    ListView lvShopcp;
    @InjectView(R.id.title_value)
    TextView titleValue;

    SocialModel model;
    String user_id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprize);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initList();
        shopcpList();
        initTitle();
    }

    List<HadShopcpEntity.ListsBean> mList = new ArrayList();

    public void shopcpList() {
        user_id = SPUtils.getString("user_id", "");
        try {
            model.getMyShopcpList(user_id, new IModelResult<HadShopcpEntity>() {
                @Override
                public void OnSuccess(HadShopcpEntity hadShopcpEntities) {
                    mList.clear();
                    if (!CommonUtils.isNull(hadShopcpEntities)) {
                        List<HadShopcpEntity.ListsBean> newList = hadShopcpEntities.getLists();
                        if (!CommonUtils.isNull(newList)) {
                            mList.addAll(newList);
                        }
                        mAdapter.notifyDataSetChanged();

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

    MyShopcpAdapter mAdapter;

    private void initList() {
        mAdapter = new MyShopcpAdapter(mActivity, mList);
        lvShopcp.setAdapter(mAdapter);
        lvShopcp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                               Intent intent = new Intent(mActivity,MyshopcpGoodActivity.class);
                                               intent.putExtra("cp_id",CommonUtils.formatNull(mList.get(position).getCp_id()));
//
                                               startActivity(intent);
                                           }
                                       }
        );
    }


    private void initTitle() {
//        titleValue.setText(R.string.perional_data);
        titleValue.setText("买到的打折商品");
    }

    @OnClick({R.id.iv_back, R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity,MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }



}
