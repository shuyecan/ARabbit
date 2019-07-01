package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.promotion.AddprzieActivity;
import com.arabbit.activity.promotion.PrizeUserActivity;
import com.arabbit.activity.promotion.ProRuleActivity;
import com.arabbit.activity.promotion.ProtionUserActivity;
import com.arabbit.adapter.SeeShopmbgoodAdapter;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.ShopmbListEntity;
import com.arabbit.entity.ShopmbgoodListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/2.
 */

public class SeeShopmbListActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.iv_image)
    ImageView ivImage;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_account)
    TextView tvAccount;

    @InjectView(R.id.bt_editor)
    ImageView Imged;
    @InjectView(R.id.btn_add)
    ImageView Imgadd;
    @InjectView(R.id.mb_count)
    TextView Mbcount;
    @InjectView(R.id.mb_far)
    TextView Mbfar;
    @InjectView(R.id.rule)
    TextView Rule;
    @InjectView(R.id.mb_title)
    TextView Mbtitle;
    @InjectView(R.id.lv_mbgood)
    ListViewForScrollView lvMbgood;

    SocialModel model;
    String target_user_id = "";
    String mb_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_mblist);
        ButterKnife.inject(this);
        target_user_id = getIntent().getStringExtra("user_id");
        mb_id = getIntent().getStringExtra("mb_id");
        model = new SocialModel(this);
        initTitle();
        getUserInfo();
        getShopmb();
        initList();
        shopmbgoodList();
        View layoutoper=findViewById(R.id.mb_oper);
        layoutoper.setVisibility(View.GONE);

    }

    public void getUserInfo() {

        try {
            model.getUserInfo(target_user_id, new IModelResult<GetUserInfoEntity>() {
                @Override
                public void OnSuccess(GetUserInfoEntity entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initUserInfo(entity);

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

    private void initUserInfo(GetUserInfoEntity entity) {
        String url = entity.getAvatar_img();
        String name = entity.getNickname();
        String account = entity.getAccount();
        String type = CommonUtils.formatNull(entity.getRole());
        String address = entity.getAddress();
        tvName.setText(name);
        if (!CommonUtils.isNull(url)) {
            ImgLoaderUtils.setImageloader(Config.IMG_URL + url, ivImage);
        }
        tvAddress.setText(address);
        tvAccount.setText(account);
        if (type.equals("1")){
            tvType.setText("公司");
        }else if(type.equals("2")){
            tvType.setText("个人");
        }else if(type.equals("3")){
            tvType.setText("店铺");
        }
    }

    private void initTitle() {
        titleValue.setText("秒爆促销活动");
    }

    public void getShopmb() {
        try {
            model.getShopmb(mb_id, new IModelResult<ShopmbListEntity.ListsBean>() {
                @Override
                public void OnSuccess(ShopmbListEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initShopmb(entity);

//这里
//                        initList();
//                        prizeList();
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

        String title = entity.getTitle();
        String buy_count = CommonUtils.formatNull(entity.getBuy_count());
        String far = entity.getFar();
        Mbtitle.setText(title);
        Mbcount.setText(buy_count);
        Mbfar.setText(far);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);
            ShopmbListEntity.ListsBean pentity = (ShopmbListEntity.ListsBean) data.getSerializableExtra("Shopmb");
            if (CommonUtils.isNull(pentity)) {
                return;
            }
            initShopmb(pentity);
        } else if (resultCode == 2) {
            mb_id = getIntent().getStringExtra("mb_id");
            getUserInfo();
            getShopmb();
            shopmbgoodList();
        }


        super.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick({R.id.btn_add, R.id.iv_back, R.id.close, R.id.rule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;

            case R.id.rule:
                Intent intent3 = new Intent(mActivity, ShopmbRuleActivity.class);
                intent3.putExtra("mb_id", mb_id);
                startActivity(intent3);
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity, MainActivity.class);
                startActivity(mintent);
                break;
        }
    }

    List<ShopmbgoodListEntity.ListsBean> mList = new ArrayList();

    public void shopmbgoodList() {

        try {
            model.getShopmbgoodList(mb_id, new IModelResult<ShopmbgoodListEntity>() {
                @Override
                public void OnSuccess(ShopmbgoodListEntity shopmbgoodListEntitys) {
                    mList.clear();
                    if (!CommonUtils.isNull(shopmbgoodListEntitys)) {
                        List<ShopmbgoodListEntity.ListsBean> newList = shopmbgoodListEntitys.getLists();
                        if (!CommonUtils.isNull(newList)) {
                            mList.addAll(newList);
                        }
                        mAdapter.notifyDataSetChanged();

                    } else {
                        ToastUtils.showToastShort(
                                "空数据"
                        );
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

    SeeShopmbgoodAdapter mAdapter;

    private void initList() {
        mAdapter = new SeeShopmbgoodAdapter(mActivity, mList);
        lvMbgood.setAdapter(mAdapter);
        lvMbgood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, SeeShopmbgoodActivity.class);
                intent.putExtra("user_id",target_user_id);
                intent.putExtra("mbgood_id", CommonUtils.formatNull(mList.get(position).getMbgood_id()));
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }




}
