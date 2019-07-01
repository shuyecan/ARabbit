package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.entity.ShopmbListEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/18.
 */

public class ShopmbRuleActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.pr_rule)
    TextView tvRule;


    SocialModel model;
    String mb_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prorule);
        ButterKnife.inject(this);
        mb_id = getIntent().getStringExtra("mb_id");
        model = new SocialModel(this);
        initTitle();
        getShopmb();

    }

    public void getShopmb() {
        try {
            model.getShopmb(mb_id, new IModelResult<ShopmbListEntity.ListsBean>() {
                @Override
                public void OnSuccess(ShopmbListEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initShopmb(entity);

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

    private void initShopmb(ShopmbListEntity.ListsBean entity) {

        String rule = entity.getRule();
        tvRule.setText(rule);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            ShopmbListEntity.ListsBean pentity = (ShopmbListEntity.ListsBean) data.getSerializableExtra("protionInfo");
            if (CommonUtils.isNull(pentity)) {
                return;
            }
            initShopmb(pentity);
        }


        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initTitle() {
        titleValue.setText("活动规则");
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

