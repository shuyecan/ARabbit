package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.shop.ShopdataActivity;
import com.arabbit.entity.HadShopcpEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/14.
 */

public class MyshopcpGoodActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.cp_image)
    ImageView cp_image;
    @InjectView(R.id.cp_count)
    TextView cpCount;
    @InjectView(R.id.cp_good)
    TextView cpName;
    @InjectView(R.id.cp_price)
    TextView cpPrice;
    @InjectView(R.id.cp_shop)
    TextView cpShop;
    @InjectView(R.id.cp_info)
    TextView cpInfo;
    @InjectView(R.id.cp_content)
    TextView cpContent;

    SocialModel model;
    String user_id = "";
    String cp_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshop_cpgood);
        ButterKnife.inject(this);
        cp_id = getIntent().getStringExtra("cp_id");
        model = new SocialModel(this);
        initTitle();
        getShopcpinfo();

    }


    private void initTitle() {

        titleValue.setText("打折促销商品信息");
    }

    public void getShopcpinfo() {
        cp_id = getIntent().getStringExtra("cp_id");
        try {
            model.getMyshopcpGood(cp_id, new IModelResult<HadShopcpEntity.ListsBean>() {
                @Override
                public void OnSuccess(HadShopcpEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initShopcpinfo(entity);

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

    private void initShopcpinfo(HadShopcpEntity.ListsBean entity) {
        String buy_count = entity.getBuy_count();
        String cpname = entity.getCpname();
        String content = entity.getContent();
        String cp_price = CommonUtils.formatNull(entity.getCp_price());
        String shopname = CommonUtils.formatNull(entity.getSenduser_name());
        String info = CommonUtils.formatNull(entity.getInfo());
        String create_time = entity.getCreate_time();

        cpCount.setText(buy_count);
        cpName.setText(cpname);
        cpPrice.setText(cp_price);
        cpInfo.setText(info);
        cpShop.setText(shopname);
        cpContent.setText(content);
        String cpimg = entity.getCpimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + cpimg, cp_image);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            HadShopcpEntity.ListsBean shopentity = (HadShopcpEntity.ListsBean) data.getSerializableExtra("Shopcpgoodinfo");
            if (CommonUtils.isNull(shopentity)) {
                return;
            }
            initShopcpinfo(shopentity);
        }
    }


    @OnClick({R.id.iv_back, R.id.close, R.id.cp_shop})
    public void onClick(View view) {
        String cp_id = getIntent().getStringExtra("cp_id");
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
            case R.id.cp_shop:
                Intent intent1 = new Intent(mActivity, ShopdataActivity.class);
                intent1.putExtra("cp_id", cp_id);
                startActivityForResult(intent1, 707);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}

