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
import com.arabbit.entity.HadShopmbEntity;
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
 * Created by Administrator on 2018/11/14.
 */

public class MyshopmbGoodActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.mb_image)
    ImageView mb_image;
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
    @InjectView(R.id.mb_shop)
    TextView mbShop;
    @InjectView(R.id.mb_content)
    TextView mbContent;

    SocialModel model;
    String user_id = "";
    String mbgood_id = "";
    String senduser_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshopmbgood);
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
         mbgood_id = getIntent().getStringExtra("mbgood_id");
        try {
            model.getMyshopmbGood(mbgood_id, new IModelResult<HadShopmbEntity.ListsBean>() {
                @Override
                public void OnSuccess(HadShopmbEntity.ListsBean entity) {
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

    private void initMbgoodinfo(HadShopmbEntity.ListsBean entity) {
        senduser_id =  CommonUtils.formatNull(entity.getSenduser_id());
        String senduser_name = entity.getSenduser_name();
        String mbname = entity.getMbname();
        String title = CommonUtils.formatNull(entity.getTitle());
        String content = entity.getContent();
        String mb_price = CommonUtils.formatNull(entity.getMb_price());
        String preprice = CommonUtils.formatNull(entity.getPreprice());
        String info = CommonUtils.formatNull(entity.getInfo());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());

        mbShop.setText(senduser_name);
        mbName.setText(mbname);
        mbTitle.setText(title);
        mbPrice.setText(mb_price);
        mbPreprice.setText(preprice);
        mbInfo.setText(info);
        mbContent.setText(content);
        String mbimg = entity.getMbimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + mbimg, mb_image);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            HadShopmbEntity.ListsBean shopentity = (HadShopmbEntity.ListsBean) data.getSerializableExtra("Mbgoodinfo");
            if (CommonUtils.isNull(shopentity)) {
                return;
            }
            initMbgoodinfo(shopentity);
        }
    }

    @OnClick({R.id.iv_back, R.id.pr_ed, R.id.close, R.id.layout_shop})
    public void onClick(View view) {
        String mbgood_id = getIntent().getStringExtra("mbgood_id");
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
            case R.id.layout_shop:
                Intent intent1 = new Intent(mActivity, ShopdataActivity.class);
                intent1.putExtra("senduser_id", user_id);
                startActivity(intent1);
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}
