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
import com.arabbit.activity.persional.StudentDataActivity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.PrizeListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/8/19.
 */

public class MyPrizeInfoActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;

    @InjectView(R.id.pr_name)
    TextView prName;
    @InjectView(R.id.pr_info)
    TextView prInfo;
    @InjectView(R.id.pr_rank)
    TextView prRank;
    @InjectView(R.id.pr_content)
    TextView prContent;
    @InjectView(R.id.pr_image)
    ImageView pr_image;
    @InjectView(R.id.senduser_name)
    TextView sendUser_name;
    @InjectView(R.id.term_name)
    TextView term_Name;
    @InjectView(R.id.had_time)
    TextView Had_time;

    SocialModel model;
    String pr_id = "";
    String senduser_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprizeinfo);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initTitle();
        getPrizeInfo();

    }

    private void initTitle() {
//        titleValue.setText(R.string.perional_data);
        titleValue.setText("我的奖品信息");
    }

    public void getPrizeInfo() {
        pr_id = getIntent().getStringExtra("pr_id");
        try {
            model.getmyPrizeInfo(pr_id, new IModelResult<PrizeListEntity.ListsBean>() {
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
        String content = entity.getContent();
        String rank = entity.getRank();
        String create_time = entity.getCreate_time();
        String senduser_name = entity.getSenduser_name();
        String term_name = entity.getTerm_name();
        senduser_id =CommonUtils.formatNull( entity.getSenduser_id());
        String had_time = CommonUtils.formatNull(entity.getHad_time());
        String time = DateUtils.getSystemTime(had_time);
        prName.setText(prname);
        prInfo.setText(info);
        prContent.setText(content);
//        prRank.setText(rank);
        if(TextUtils.equals(rank,"0")){
            prRank.setText("一等奖");
        }else if(TextUtils.equals(rank,"1")){
            prRank.setText("二等奖");
        }else if(TextUtils.equals(rank,"2")){
            prRank.setText("三等奖");
        }else{
            prRank.setText("鼓励奖");
        }
        String primg = entity.getPrimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + primg,pr_image);
        sendUser_name.setText(senduser_name);
        term_Name.setText(term_name);
        Had_time.setText(time);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            PrizeListEntity.ListsBean pentity = (PrizeListEntity.ListsBean) data.getSerializableExtra("giftInfo");
            if (CommonUtils.isNull(pentity)) {
                return;
            }
            initPrizeInfo(pentity);
        }
    }

    @OnClick({R.id.iv_back, R.id.close, R.id.senduser_name})
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
            case R.id.senduser_name:
                Intent sintent = new Intent(mActivity,StudentDataActivity.class);
                sintent.putExtra("user_id", senduser_id);
                sintent.putExtra("seat_user_id", senduser_id);
                startActivity(sintent);
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }



}
