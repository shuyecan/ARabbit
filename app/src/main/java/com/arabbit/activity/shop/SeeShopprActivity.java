package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.Userpage.SeePrizeInfoActivity;
import com.arabbit.activity.login.LoginActivity;
import com.arabbit.activity.promotion.ProRuleActivity;
import com.arabbit.adapter.SeeShopprListAdapter;
import com.arabbit.entity.GetUserCardEntity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.PrizeListEntity;
import com.arabbit.entity.ProtionListEntity;
import com.arabbit.entity.ShopprListEntity;
import com.arabbit.entity.ShopptListEntity;
import com.arabbit.entity.UserHadprizeEntity;
import com.arabbit.entity.UserHadproEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.dialog.DialogWithContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/2.
 */

public class SeeShopprActivity extends BaseActivity {

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
    @InjectView(R.id.user_join)
    TextView user_join;
    @InjectView(R.id.user_win)
    TextView user_win;

    @InjectView(R.id.pt_type)
    TextView Pttype;
    @InjectView(R.id.pt_title)
    TextView Pttitle;
    @InjectView(R.id.pt_far)
    TextView  Ptfar;
    @InjectView(R.id.rule)
    TextView Rule;
    @InjectView(R.id.start_time)
    TextView Starttime;
    @InjectView(R.id.end_time)
    TextView Endtime;
    @InjectView(R.id.pro_time)
    TextView Protime;
    @InjectView(R.id.lv_prize)
    ListView lvPrize;
    private SimpleDateFormat simpledateformat;
    SocialModel model;
    String target_user_id = "";
    String pt_id = "";
    int pttype =1;

    GetUserCardEntity.ListsBean accord_entity = null;
    private String ptstart;
    private String ptend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_shoppr);
        ButterKnife.inject(this);
        target_user_id = getIntent().getStringExtra("user_id");
        pt_id = getIntent().getStringExtra("pt_id");
        model = new SocialModel(this);
        String user_id = SPUtils.getString("user_id", "");
        getUserCard(user_id);
        initTitle();
        getUserInfo();
        getProtionInfo();
        initList();
        prizeList();

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
        titleValue.setText("抽奖促销活动");
    }

    public void getProtionInfo() {

        try {
            model.getShopptInfo(pt_id, new IModelResult<ShopptListEntity.ListsBean>() {
                @Override
                public void OnSuccess(ShopptListEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initProtionInfo(entity);

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

    private void initProtionInfo(ShopptListEntity.ListsBean entity) {
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        pttype = entity.getType();
        String ptfar = entity.getFar();
        ptstart = entity.getStarttime();
        String title = entity.getTitle();
        ptend = entity.getEndtime();
        showlife();
        Ptfar.setText(ptfar);
        Pttitle.setText(title);
        String startformat = simpledateformat.format(new Date(Long.parseLong(ptstart)));
        Starttime.setText(startformat);
        String endformat = simpledateformat.format(new Date(Long.parseLong(ptend)));
        Endtime.setText(endformat);
        Protime.setText(endformat);
        int count2 = entity.getCount2();
        user_join.setText(count2+"");
        int count3 = entity.getCount3();
        user_win.setText(count3+"");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);
            ShopptListEntity.ListsBean pentity = (ShopptListEntity.ListsBean) data.getSerializableExtra("protionInfo");
            if (CommonUtils.isNull(pentity)) {
                return;
            }
            initProtionInfo(pentity);
        }


        super.onActivityResult(requestCode, resultCode, data);

    }


    public void showlife() {
        View layoutcust=findViewById(R.id.line_cust);
        View layoutbao=findViewById(R.id.line_bao);

        if(pttype==1){
            Pttype.setText("普通抽奖");
            layoutcust.setVisibility(View.VISIBLE);
            layoutbao.setVisibility(View.GONE);

        }else if (pttype==2){
            Pttype.setText("秒爆抽奖");
            layoutcust.setVisibility(View.GONE);
            layoutbao.setVisibility(View.VISIBLE);
        }

    }

    public void userHadPro(final String user_id, final String pt_id) {
        try {
            model.userHadPro(user_id, pt_id,
                    new IModelResult<UserHadproEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(UserHadproEntity.ListsBean entity) {
                            Log.e("aaa","执行userHadPro");
                            ToastUtils.showToastShort(""+entity.getMessage());
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

    public void userHadPrize(final String user_id, final String pr_id) {
        try {
            model.userHadPrize(user_id, pr_id,
                    new IModelResult<UserHadprizeEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(UserHadprizeEntity.ListsBean entity) {
                            Log.e("aaa","执行userHadPrize");
                            ToastUtils.showToastShort("恭喜您中了一等奖！");
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
    public void getUserCard(String user_id) {
//        String user_id = SPUtils.getString("user_id", "");
        try {
            model.getUserCard(user_id, new IModelResult<GetUserCardEntity.ListsBean>() {
                @Override
                public void OnSuccess(GetUserCardEntity.ListsBean entity) {

                    if(entity != null){
                        accord_entity = entity;
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

    @OnClick({R.id.iv_back,R.id.pro_image, R.id.close, R.id.rule})
    public void onClick(View view) {
        pt_id = getIntent().getStringExtra("pt_id");
        String role= SPUtils.getString("role", "");
        String user_id = SPUtils.getString("user_id", "");
//        String pr_id ="2";
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.pro_image:
                long currntTimeStamp = System.currentTimeMillis();
                if(!TextUtils.isEmpty(ptstart) && !TextUtils.equals(ptstart,null)){
                    long startTimeStampl = Long.parseLong(ptstart);
                    if(startTimeStampl >currntTimeStamp){
                        ToastUtils.showToastShort("活动尚未开始，不能抽奖！");
                        return;
                    }
                }else{
                    ToastUtils.showToastShort("活动尚未开始，不能抽奖！");
                    return;
                }

                if(!TextUtils.isEmpty(ptend) && !TextUtils.equals(ptend,null)){
                    long endTimeStampl = Long.parseLong(ptend);
                    Log.e("aaa","SeeGiftAdapter结束时间："+endTimeStampl+"，当前时间："+currntTimeStamp);
                    if(currntTimeStamp >endTimeStampl){
                        ToastUtils.showToastShort("活动已结束");
                        return;
                    }
                }


                int type = SPUtils.getInt("type", 1);
                if (type == 2) {
                    showTip();
                    return;
                }
                Log.e("aaa","集合数量：："+mList.size());
                if(mList == null){
                    ToastUtils.showToastShort("尚未添加奖品,不能参与抽奖哦！");
                    return;
                }
                Log.e("aaa","集合数1量：："+mList.size());
                if(mList != null && mList.size()<=0){
                    ToastUtils.showToastShort("尚未添加奖品,不能参与抽奖哦！");
                    return;
                }
                Log.e("aaa","集合数2量：："+mList.size());

                if (role.equals("2") ){
                    String real_name = accord_entity.getReal_name();
                    if(accord_entity != null && !CommonUtils.isNull(real_name)){

                    }else{
                        ToastUtils.showToastShort("您还没有设置名片,不能参与抽奖哦！");
                        return;
                    }
                    userHadPro(user_id, pt_id);
//                    userHadPrize(user_id, pt_id);
                }else  {
                    ToastUtils.showToastShort("非个人用户不能参与抽奖哦！");
                }
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity,MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
            case R.id.rule:
                Intent intent = new Intent(mActivity, ShopptRuleActivity.class);
                intent.putExtra("pt_id", pt_id);
                startActivity(intent);
                break;
        }
    }

    List<ShopprListEntity.ListsBean> mList = new ArrayList();

    public void prizeList() {
        Log.e("aaa","抽奖活动id："+pt_id);

        try {
            model.getShopprList(pt_id, new IModelResult<ShopprListEntity>() {
                @Override
                public void OnSuccess(ShopprListEntity shopprListEntitys) {
                    mList.clear();
                    if (!CommonUtils.isNull(shopprListEntitys)) {
                        List<ShopprListEntity.ListsBean> newList =shopprListEntitys.getLists();
                        if (!CommonUtils.isNull(newList)) {
                            mList.addAll(newList);
                        }
                        Log.e("aaa","集合数prizeList量：："+mList.size());
                        mAdapter.notifyDataSetChanged();

                    }else {
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

    SeeShopprListAdapter mAdapter;

    private void initList() {
        mAdapter = new SeeShopprListAdapter(mActivity, mList);
        lvPrize.setAdapter(mAdapter);
        lvPrize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, SeeShopprinfoActivity.class);
                intent.putExtra("user_id",target_user_id);
                intent.putExtra("pr_id", CommonUtils.formatNull(mList.get(position).getPr_id()));
                startActivity(intent);
            }
        });
    }

    DialogWithContent mDialogWithContent;
    public void showTip() {
        mDialogWithContent = new DialogWithContent(mActivity);
        mDialogWithContent.setContent(getString(R.string.unsignal));
        mDialogWithContent.setYesText(getString(R.string.confirm), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                mDialogWithContent.dismiss();
            }
        });
        mDialogWithContent.show();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}

