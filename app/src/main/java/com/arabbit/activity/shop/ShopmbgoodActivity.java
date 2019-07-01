package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.promotion.PrizeUserActivity;
import com.arabbit.activity.promotion.UpdatePrizeActivity;
import com.arabbit.entity.ShopmbgoodListEntity;
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
 * Created by Administrator on 2018/11/1.
 */

public class ShopmbgoodActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.mb_image)
    ImageView mb_image;
    @InjectView(R.id.mb_count)
    TextView mbCount;
    @InjectView(R.id.far)
    TextView mbFar;
    @InjectView(R.id.mb_good)
    TextView mbName;
    @InjectView(R.id.mb_title)
    TextView mbTitle;
    @InjectView(R.id.mb_price)
    TextView mbPrice;
    @InjectView(R.id.mb_preprice)
    TextView mbPreprice;
    @InjectView(R.id.mb_info)
    TextView mbInfo;
    @InjectView(R.id.mb_num)
    TextView mbNum;
    @InjectView(R.id.start_time)
    TextView Starttime;
    @InjectView(R.id.end_time)
    TextView Endtime;
    @InjectView(R.id.mb_content)
    TextView mbContent;

    SocialModel model;
    String user_id = "";
    String mbgood_id = "";
    String mb_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopmbgood);
        ButterKnife.inject(this);
        mbgood_id = getIntent().getStringExtra("mbgood_id");
        model = new SocialModel(this);
        initTitle();
        getMbgoodinfo();

    }


    private void initTitle() {

        titleValue.setText("秒爆促销商品信息");
    }

    public void getMbgoodinfo() {
        String mbgood_id = getIntent().getStringExtra("mbgood_id");
        try {
            model.getMbgoodinfo(mbgood_id, new IModelResult<ShopmbgoodListEntity.ListsBean>() {
                @Override
                public void OnSuccess(ShopmbgoodListEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initMbgoodinfo(entity);

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

    private void initMbgoodinfo(ShopmbgoodListEntity.ListsBean entity) {
        String buy_count = entity.getBuy_count();
        String far = entity.getFar();
        String mbname = entity.getMbname();
        String title = CommonUtils.formatNull(entity.getTitle());
        String content = entity.getContent();
        String mb_price = CommonUtils.formatNull(entity.getMb_price());
        String preprice = CommonUtils.formatNull(entity.getPreprice());
        String info = CommonUtils.formatNull(entity.getInfo());
        String num = CommonUtils.formatNull(entity.getNum());
        String starttime = CommonUtils.formatNull(entity.getStarttime());
        String endtime = CommonUtils.formatNull(entity.getEndtime());
        String create_time = entity.getCreate_time();
         mb_id = CommonUtils.formatNull(entity.getMb_id());

        mbCount.setText(buy_count);
        mbFar.setText(far);
        mbName.setText(mbname);
        mbTitle.setText(title);
        mbPrice.setText(mb_price);
        mbPreprice.setText(preprice);
        mbInfo.setText(info);
        mbNum.setText(num);
        Starttime.setText(starttime);
        Endtime.setText(endtime);
        mbContent.setText(content);
        String mbimg = entity.getMbimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + mbimg, mb_image);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ShopmbgoodListEntity.ListsBean shopentity = (ShopmbgoodListEntity.ListsBean) data.getSerializableExtra("Mbgoodinfo");
            if (CommonUtils.isNull(shopentity)) {
                return;
            }
            initMbgoodinfo(shopentity);
        }
    }
    public void delShopmbgood() {
        mbgood_id = getIntent().getStringExtra("mbgood_id");
        try {
            model.delShopmbgood(mbgood_id, new IModelResult<ShopmbgoodListEntity.ListsBean>() {
                @Override
                public void OnSuccess(ShopmbgoodListEntity.ListsBean entity) {

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

    @OnClick({R.id.iv_back, R.id.mb_ed, R.id.mb_del, R.id.close, R.id.mb_user, R.id.mb_rule})
    public void onClick(View view) {
        String mbgood_id = getIntent().getStringExtra("mbgood_id");
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.mb_ed:
                try {
                    String endtimetamp = getIntent().getStringExtra("timestamp");
                    long endTimeStamp = Long.parseLong(endtimetamp);

                    long currentTimeMillis = System.currentTimeMillis();
                    if(endTimeStamp >= currentTimeMillis){
                        Intent intent = new Intent(mActivity, UpdateShopmbgoodActivity.class);
                        intent.putExtra("mbgood_id", mbgood_id);
                        startActivity(intent);
                    }else{
                        ToastUtils.showToastShort("活动已结束，不可再编辑");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToastShort("活动已结束，不可再编辑");
                }


                break;
            case R.id.mb_del:
                try {
                    String endtimetamp = getIntent().getStringExtra("timestamp");
                    long endTimeStamp = Long.parseLong(endtimetamp);

                    long currentTimeMillis = System.currentTimeMillis();
                    if(endTimeStamp >= currentTimeMillis){
                        delShopmbgood();
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
            case R.id.mb_user:
                Intent intent1 = new Intent(mActivity, ShopmbUserActivity.class);
                intent1.putExtra("mbgood_id", mbgood_id);
                startActivity(intent1);
                break;
            case R.id.mb_rule:
                Intent intent2 = new Intent(mActivity, ShopmbRuleActivity.class);
                intent2.putExtra("mb_id", mb_id);
                startActivity(intent2);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}
