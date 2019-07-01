package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.entity.HadShopcpEntity;
import com.arabbit.entity.ShopcpListEntity;
import com.arabbit.entity.HadShopcpEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/25.
 */

public class SeeShopcpgoodActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.cp_image)
    ImageView cp_image;
    @InjectView(R.id.cp_count)
    TextView cpCount;
    @InjectView(R.id.cp_comment)
    TextView cpComment;
    @InjectView(R.id.cp_good)
    TextView cpName;
    @InjectView(R.id.cp_price)
    TextView cpPrice;
    @InjectView(R.id.cp_preprice)
    TextView cpPreprice;
    @InjectView(R.id.cp_info)
    TextView cpInfo;
    @InjectView(R.id.cp_content)
    TextView cpContent;
    @InjectView(R.id.buy_num)
    EditText tvBuy_num;

    SocialModel model;
    String user_id = "";
    String target_user_id = "";
    String  cp_id= "";
    String cpname ="";
    String content ="";
    String cp_price ="";
    String preprice ="";
    String info ="";
    String cpimg ="";
    String buynum ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_shop_cpgood);
        ButterKnife.inject(this);
        cp_id = getIntent().getStringExtra("cp_id");
        target_user_id = getIntent().getStringExtra("user_id");
        model = new SocialModel(this);
        initTitle();
        getShopcpgood();

    }


    private void initTitle() {

        titleValue.setText("打折促销商品信息");
    }

    public void getShopcpgood() {
        cp_id = getIntent().getStringExtra("cp_id");
        try {
            model.getShopcpgood(cp_id, new IModelResult<ShopcpListEntity.ListsBean>() {
                @Override
                public void OnSuccess(ShopcpListEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initShopcpgood(entity);

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

    private void initShopcpgood(ShopcpListEntity.ListsBean entity) {
        String buy_count = entity.getBuy_count();
         cpname = entity.getCpname();
         content = entity.getContent();
         cp_price = CommonUtils.formatNull(entity.getCp_price());
         preprice = CommonUtils.formatNull(entity.getPreprice());
         info = CommonUtils.formatNull(entity.getInfo());
        String create_time = entity.getCreate_time();

        cpCount.setText(buy_count);
        cpName.setText(cpname);
        cpPrice.setText(cp_price);
        cpPreprice.setText(preprice);
        cpInfo.setText(info);
        cpContent.setText(content);
        cpimg = entity.getCpimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + cpimg, cp_image);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ShopcpListEntity.ListsBean shopentity = (ShopcpListEntity.ListsBean) data.getSerializableExtra("Shopcpgoodinfo");
            if (CommonUtils.isNull(shopentity)) {
                return;
            }
            initShopcpgood(shopentity);
        }
    }

    public void UserShopcp(final String user_id,final String cp_id,final String cpname, final String cpimg,final String info,
                              final String cp_price, final String preprice,final String buynum,
                              final String content,final String send_user_id) {
        try {

            model.userShopcp(user_id,cp_id,cpname, cpimg,info, cp_price, preprice, buynum,
                    content,send_user_id, new IModelResult<HadShopcpEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(HadShopcpEntity.ListsBean entity) {
                            ToastUtils.showToastShort(R.string.add_success);
                            Intent intent = new Intent(mActivity, SeeShopcpActivity.class);
                            setResult(RESULT_OK, intent);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
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

    @OnClick({R.id.iv_back, R.id.close, R.id.cp_count, R.id.cp_comment, R.id.cp_buy})
    public void onClick(View view) {
        target_user_id = getIntent().getStringExtra("user_id");
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
            case R.id.cp_comment:
                Intent intentr = new Intent(mActivity, ShopmbRuleActivity.class);
                intentr.putExtra("cp_id", cp_id);
                intentr.putExtra("user_id", target_user_id);
                startActivity(intentr);
                break;
            case R.id.cp_count:
                Intent intent1 = new Intent(mActivity, SeeShopmbListActivity.class);
                intent1.putExtra("cp_id", cp_id);
                intent1.putExtra("user_id", target_user_id);
                startActivity(intent1);
                break;
            case R.id.cp_buy:
                buynum = UIUtils.getStrEditView(tvBuy_num);
                if (CommonUtils.isNull(buynum)) {
                    ToastUtils.showToastShort("订购数量不能为空");
                    return;
                }
                UserShopcp(user_id,cp_id,cpname, cpimg, info, cp_price, preprice,
                        buynum,content, target_user_id);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}

