package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.login.LoginActivity;
import com.arabbit.adapter.SeeGiftAdapter;
import com.arabbit.adapter.SeeGiftAdapter.Callback;
import com.arabbit.entity.GetUserCardEntity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.GiftListEntity;
import com.arabbit.entity.UserHadgiftEntity;
import com.arabbit.entity.UserJoingiftEntity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/8/3.
 */

public class SeeGiftActivity  extends BaseActivity implements AdapterView.OnItemClickListener,Callback {


    @InjectView(R.id.lv_gift)
    ListView lvGift;
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
    @InjectView(R.id.title_value)
    TextView titleValue;

    SocialModel model;
    String target_user_id = "";
    String term_id = "";
    String b_id = "";
    GetUserCardEntity.ListsBean accord_entity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seegift);
        ButterKnife.inject(this);
        target_user_id = getIntent().getStringExtra("user_id");
        b_id = getIntent().getStringExtra("b_id");
        model = new SocialModel(this);
        getUserInfo();
        initList();
        giftList();
        initTitle();
        String user_id = SPUtils.getString("user_id", "");
        getUserCard(user_id);
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
    List<GiftListEntity.ListsBean> mList = new ArrayList();

    public void giftList() {
        term_id = getIntent().getStringExtra("term_id");
        try {
            model.getGiftList(target_user_id, term_id, new IModelResult<GiftListEntity>() {
                @Override
                public void OnSuccess(GiftListEntity giftListEntities) {
                    mList.clear();
                    if (!CommonUtils.isNull(giftListEntities)) {
                        List<GiftListEntity.ListsBean> newList = giftListEntities.getLists();
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

    SeeGiftAdapter mAdapter;

    private void initList() {
        mAdapter = new SeeGiftAdapter(mActivity, mList,this);
        lvGift.setAdapter(mAdapter);
        lvGift.setOnItemClickListener(this);
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
//            ImgLoaderUtils.setImageloader(Config.BASE_URL + url, ivImage);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);
        }
    }

    private void initTitle() {
        titleValue.setText("展会礼品");
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


    //响应ListView中item的点击事件
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        Log.e("aaa","展会礼品onItemClick");
        Intent intent = new Intent(mActivity,SeeGiftInfoActivity.class);
        intent.putExtra("gift_id",CommonUtils.formatNull(mList.get(position).getGift_id()));
        intent.putExtra("user_id",target_user_id);
        intent.putExtra("term_id",term_id);
        startActivity(intent);

    }

    //接口方法，响应ListView按钮点击事件
    @Override
    public void click(View v,final int gift_id,int ava_num) {
        int position;
        position = (Integer) v.getTag();
        String role= SPUtils.getString("role", "");
        String user_id = SPUtils.getString("user_id", "");
        switch (v.getId()) {
            case R.id.gf_hit:

                int type = SPUtils.getInt("type", 1);
                if (type == 2) {
                    showTip();
                    return;
                }
                if (role.equals("2") ){
                    String real_name = accord_entity.getReal_name();
                    if(accord_entity != null && !CommonUtils.isNull(real_name)){

                    }else{
                        ToastUtils.showToastShort("您还没有设置名片,不能领取哦！");
                        return;
                    }
                    userJoinGift(user_id, CommonUtils.formatNull(gift_id));
                    ava_num+=1;
                    userHadGift(user_id, gift_id+"",ava_num);

                }else  {
                    ToastUtils.showToastShort("非个人用户不能领取哦！");
                }





//                int type = SPUtils.getInt("type", 1);
//                if (type == 2) {
//                    showTip();
//                    return;
//                }
//                Intent intent = new Intent(mActivity,SeeGiftInfoActivity.class);
//                intent.putExtra("gift_id",CommonUtils.formatNull(mList.get(position).getGift_id()));
//                intent.putExtra("user_id",target_user_id);
//                startActivity(intent);
                break;

        }

    }

    public void userHadGift(final String user_id, final String gift_id,int ava_num) {
        try {
            model.userHadGift(user_id, gift_id,term_id,
                    new IModelResult<UserHadgiftEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(UserHadgiftEntity.ListsBean entity) {
                            ToastUtils.showToastShort("恭喜您成功领取礼品");
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

    public void userJoinGift(final String user_id, final String gift_id) {
//        Log.e("aaa","服务器返回的的领取礼品的人数："+ava_num);
//        Log.e("aaa","服务器返回的展会id："+term_id);
        try {
            model.userJoinGift(user_id, gift_id,term_id,
                    new IModelResult<UserJoingiftEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(UserJoingiftEntity.ListsBean entity) {
//                            ToastUtils.showToastShort("恭喜您成功领取礼品");
//                            finish();
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


//
//        final DialogYesOrNoUtil yesOrNoUtil = new DialogYesOrNoUtil(mActivity);
//        yesOrNoUtil.setDialogTitle("提示");
//        yesOrNoUtil.setContent("尚未登录，是否先登录?");
//        yesOrNoUtil.setYesText("是", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        yesOrNoUtil.setNoText("否", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                yesOrNoUtil.dismiss();
//            }
//        });
//        yesOrNoUtil.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }


}
