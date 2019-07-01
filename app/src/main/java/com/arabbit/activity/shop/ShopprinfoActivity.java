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
 * Created by Administrator on 2018/11/8.
 */

public class ShopprinfoActivity extends BaseActivity {

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
    @InjectView(R.id.pr_rate)
    TextView prRate;
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
        setContentView(R.layout.activity_shopprinfo);
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
        String rate = CommonUtils.formatNull(entity.getPrrate());
        String create_time = entity.getCreate_time();

        prName.setText(prname);
        prInfo.setText(info);
        prNum.setText(num);
        prContent.setText(content);
//        prRank.setText(rank);
        prRate.setText(rate);
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
    public void delPrize() {
        String pr_id = getIntent().getStringExtra("pr_id");
        try {
            model.delShoppr(pr_id, new IModelResult<List<ShopprListEntity.ListsBean>>() {
                @Override
                public void OnSuccess(List<ShopprListEntity.ListsBean> entity) {
                    ToastUtils.showToastShort("操作成功");
                    Intent intent = new Intent();
                    intent.putExtra("pt_id",pt_id);
                    setResult(2,intent);
                    finish();
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

    @OnClick({R.id.iv_back, R.id.pr_ed, R.id.pr_del, R.id.close, R.id.pr_user})
    public void onClick(View view) {
        String pr_id = getIntent().getStringExtra("pr_id");
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.pr_ed:
                try {
                    String endtimetamp = getIntent().getStringExtra("timestamp");
                    long endTimeStamp = Long.parseLong(endtimetamp);

                    long currentTimeMillis = System.currentTimeMillis();
                    if(endTimeStamp >= currentTimeMillis){
                        Intent intent = new Intent(mActivity, UpdateShopprActivity.class);
                        intent.putExtra("pr_id", pr_id);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }else{
                        ToastUtils.showToastShort("活动已结束，不可再编辑");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToastShort("活动已结束，不可再编辑");
                }


                break;
            case R.id.pr_del:
                try {
                    String endtimetamp = getIntent().getStringExtra("timestamp");
                    long endTimeStamp = Long.parseLong(endtimetamp);

                    long currentTimeMillis = System.currentTimeMillis();
                    if(endTimeStamp >= currentTimeMillis){
                        delPrize();
                    }else{
                        ToastUtils.showToastShort("活动已结束，不可删除");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToastShort("活动已结束，不可删除");
                }

                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity,MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
            case R.id.pr_user:
                Intent intent1 = new Intent(mActivity, ShopprUserActivity.class);
                intent1.putExtra("pr_id", pr_id);
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
