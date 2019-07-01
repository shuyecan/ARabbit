package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.login.LoginActivity;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/8/5.
 */

public class SeeGiftInfoActivity extends BaseActivity {

    @InjectView(R.id.iv_image)
    ImageView ivImage;

    @InjectView(R.id.gf_image)
    ImageView gf_image;

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

    @InjectView(R.id.gf_count)
    TextView gfCount;
    @InjectView(R.id.gf_name)
    TextView  gfName;
    @InjectView(R.id.gf_info)
    TextView gfInfo;
    @InjectView(R.id.gf_far)
    TextView gfFar;
    @InjectView(R.id.gf_start)
    TextView gfStart;
    @InjectView(R.id.gf_end)
    TextView gfEnd;
    @InjectView(R.id.gf_content)
    TextView gfContent;

    SocialModel model;
    String gift_id = "";
    String target_user_id = "";
    private int ava_num = 0;
    private String gfnum;
    private String term_id = "";
    private String gfend = "";
    GetUserCardEntity.ListsBean accord_entity = null;
    private String gfstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seegiftinfo);
        ButterKnife.inject(this);
        target_user_id = getIntent().getStringExtra("user_id");
        term_id = getIntent().getStringExtra("term_id");
        model = new SocialModel(this);
        getUserInfo();
        initTitle();
        getGiftInfo();
        String user_id = SPUtils.getString("user_id", "");
        getUserCard(user_id);

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

    private void initTitle() {
//        titleValue.setText(R.string.perional_data);
        titleValue.setText("展会礼品");
    }

    public void getGiftInfo() {
        gift_id = getIntent().getStringExtra("gift_id");
        try {
            model.getGiftInfo(gift_id, new IModelResult<GiftListEntity.ListsBean>() {
                @Override
                public void OnSuccess(GiftListEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initGiftInfo(entity);

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

    private void initGiftInfo(GiftListEntity.ListsBean entity) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String gfname = entity.getGfname();
        String gfinfo = entity.getGfinfo();
        String gfcontent = entity.getGfcontent();
        gfstart = entity.getGfstart();
        gfend = entity.getGfend();
        String gffar = entity.getGffar();
        gfName.setText(gfname);
        gfInfo.setText(gfinfo);
        gfContent.setText(gfcontent);
        gfFar.setText(gffar);
        String startformat = simpledateformat.format(new Date(Long.parseLong(gfstart)));
        gfStart.setText(startformat);
        String endformat = simpledateformat.format(new Date(Long.parseLong(gfend)));
        gfEnd.setText(endformat);


        gfnum = entity.getGfnum();

        ava_num = entity.getAva_num();
        gfCount.setText(ava_num+"");
        String gfimg = entity.getGfimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + gfimg, gf_image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);

            GiftListEntity.ListsBean gentity = (GiftListEntity.ListsBean) data.getSerializableExtra("giftInfo");
            if (CommonUtils.isNull(gentity)) {
                return;
            }
            initGiftInfo(gentity);
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
    @OnClick({R.id.iv_back,R.id.gf_hit, R.id.close})
    public void onClick(View view) {
        gift_id = getIntent().getStringExtra("gift_id");
        String role= SPUtils.getString("role", "");
        String user_id = SPUtils.getString("user_id", "");
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.gf_hit:
                long currntTimeStamp = System.currentTimeMillis();
                if(!TextUtils.isEmpty(gfstart) && !TextUtils.equals(gfstart,null)){
                    long startTimeStampl = Long.parseLong(gfstart);
                    if(startTimeStampl >currntTimeStamp){
                        ToastUtils.showToastShort("活动尚未开始，不能领取礼品");
                        return;
                    }
                }else{
                    ToastUtils.showToastShort("活动尚未开始，不能领取礼品");
                    return;
                }
                if(!TextUtils.isEmpty(gfend) && !TextUtils.equals(gfend,null)){
                    long endTimeStampl = Long.parseLong(gfend);

                    Log.e("aaa","结束时间："+endTimeStampl+"，当前时间："+currntTimeStamp);
                    if(currntTimeStamp >endTimeStampl){
                        Log.e("aaa","提示：");
                        ToastUtils.showToastShort("活动已结束");
                        return;
                    }else{
                        Log.e("aaa","正在进行：");
                    }
                }else{
                    ToastUtils.showToastShort("活动已结束");
                    return;
                }

                int type = SPUtils.getInt("type", 1);
                if (type == 2) {
                    showTip();
                    return;
                }
                if (role.equals("2") ){
                    if(accord_entity == null){
                        ToastUtils.showToastShort("您还没有设置名片,不能参加活动哦！");
                        return;
                    }
                    userJoinGift(user_id, gift_id);
                    int gftotalnum = Integer.parseInt(gfnum);
                    if(gftotalnum > ava_num){
                        userHadGift(user_id, gift_id);
                    }else{
                        ToastUtils.showToastShort("礼品已经被领取完了，谢谢参与活动");
                    }


                }else  {
                    ToastUtils.showToastShort("非个人用户不能领取哦！");
                }
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity,MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
        }
    }

    public void userHadGift(final String user_id, final String gift_id) {
        Log.e("aaa","服务器返回的的领取礼品的人数："+ava_num);
        Log.e("aaa","服务器返回的展会id："+term_id);
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
