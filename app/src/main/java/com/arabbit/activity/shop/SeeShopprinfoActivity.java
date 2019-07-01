package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.promotion.PrizeUserActivity;
import com.arabbit.activity.promotion.UpdatePrizeActivity;
import com.arabbit.entity.PrizeListEntity;
import com.arabbit.entity.ShopprListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/10.
 */

public class SeeShopprinfoActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.pr_count)
    TextView prCount;
    @InjectView(R.id.pr_name)
    TextView prName;
    @InjectView(R.id.pr_info)
    TextView prInfo;
    @InjectView(R.id.pr_rank)
    TextView prRank;
    @InjectView(R.id.pr_num)
    TextView prNum;
    @InjectView(R.id.pr_content)
    TextView prContent;
    @InjectView(R.id.pr_image)
    ImageView pr_image;
    @InjectView(R.id.pr_price)
    TextView prPrice;

    SocialModel model;
    String user_id = "";
    String pt_id = "";
    String type ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_shopprinfo);
        ButterKnife.inject(this);
        pt_id = getIntent().getStringExtra("pt_id");
        type =getIntent().getStringExtra("type");
        model = new SocialModel(this);
        initTitle();
        getShopprInfo();

    }


    private void initTitle() {

        titleValue.setText("抽奖促销奖品");
    }

    public void getShopprInfo() {
        String pr_id = getIntent().getStringExtra("pr_id");
        try {
            model.getShopprInfo(pr_id, new IModelResult<ShopprListEntity.ListsBean>() {
                @Override
                public void OnSuccess(ShopprListEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initShopprInfo(entity);

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

    private void initShopprInfo(ShopprListEntity.ListsBean entity) {
        String prname = entity.getPrname();
        String info = entity.getInfo();
        String num = CommonUtils.formatNull(entity.getPrnum());
        String content = entity.getContent();
        String Prrank = entity.getRank();
        String create_time = entity.getCreate_time();

        prName.setText(prname);
        prInfo.setText(info);
        prNum.setText(num);
        prContent.setText(content);
        String prized_count = entity.getPrized_count();
        prCount.setText(prized_count);
        String primg = entity.getPrimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + primg, pr_image);


        if(TextUtils.equals(Prrank,"0")){
            prRank.setText("一等奖");
        }else if(TextUtils.equals(Prrank,"1")){
            prRank.setText("二等奖");
        }else if(TextUtils.equals(Prrank,"2")){
            prRank.setText("三等奖");
        }else{
            prRank.setText("鼓励奖");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ShopprListEntity.ListsBean pentity = (ShopprListEntity.ListsBean) data.getSerializableExtra("ShopprInfo");
            if (CommonUtils.isNull(pentity)) {
                return;
            }
            initShopprInfo(pentity);
        }
    }

    @OnClick({R.id.iv_back,  R.id.close})
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
