package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.PrizeListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/8/19.
 */

public class SeePrizeInfoActivity extends BaseActivity {

    @InjectView(R.id.iv_image)
    ImageView ivImage;
    @InjectView(R.id.pr_image)
    ImageView pr_image;
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

    @InjectView(R.id.pr_count)
    TextView prCount;
    @InjectView(R.id.pr_name)
    TextView  prName;
    @InjectView(R.id.pr_info)
    TextView prInfo;
    @InjectView(R.id.pr_rank)
    TextView prRank;
    @InjectView(R.id.pr_num)
    TextView prNum;
    @InjectView(R.id.pr_content)
    TextView prContent;

    SocialModel model;
    String pr_id = "";
    String target_user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeprizeinfo);
        ButterKnife.inject(this);
        target_user_id = getIntent().getStringExtra("user_id");
        model = new SocialModel(this);
        getUserInfo();
        initTitle();
        getPrizeInfo();

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
        titleValue.setText("展会奖品");
    }

    public void getPrizeInfo() {
        pr_id = getIntent().getStringExtra("pr_id");
        try {
            model.getPrizeInfo(pr_id, new IModelResult<PrizeListEntity.ListsBean>() {
                @Override
                public void OnSuccess(PrizeListEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initPrizeInfo(entity);

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

    private void initPrizeInfo(PrizeListEntity.ListsBean entity) {
        String prname = entity.getPrname();
        String info = entity.getInfo();
        String num = entity.getContent();
        String content = entity.getContent();
        String Prrank = entity.getRank();
        String create_time = entity.getCreate_time();
        prName.setText(prname);
        prInfo.setText(info);
        prNum.setText(num);
        prContent.setText(content);
//        prRank.setText(rank);
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
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);

            PrizeListEntity.ListsBean pentity = (PrizeListEntity.ListsBean) data.getSerializableExtra("PrizeInfo");
            if (CommonUtils.isNull(pentity)) {
                return;
            }
            initPrizeInfo(pentity);
        }
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }


}
