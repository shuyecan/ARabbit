package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.SeatDeatilActivity;
import com.arabbit.activity.persional.StudentDataActivity;
import com.arabbit.entity.GiftListEntity;
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

import static android.app.Activity.RESULT_OK;
import static com.arabbit.R.string.postal_code;
//import static com.arabbit.activity.Userpage.MyGiftInfoActivity.SENDUSER;

/**
 * Created by Administrator on 2018/8/19.
 */

public class MyGiftInfoActivity extends BaseActivity {

    @InjectView(R.id.gf_name)
    TextView gfName;
    @InjectView(R.id.gf_info)
    TextView gfInfo;
    @InjectView(R.id.gf_content)
    TextView gfContent;
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.gf_image)
    ImageView gf_image;
    @InjectView(R.id.senduser_name)
    TextView sendUser_name;
    @InjectView(R.id.term_name)
    TextView term_Name;
    @InjectView(R.id.had_time)
    TextView Had_time;

    SocialModel model;
    String gift_id = "";
    String senduser_id = "";
    int school_id ;
    String school_name = "";
    int term_id ;
    boolean isLoadHistoryTermSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygiftinfo);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initTitle();
        getGiftInfo();

    }

    private void initTitle() {
//        titleValue.setText(R.string.perional_data);
        titleValue.setText("我的礼品信息");
    }

    public void getGiftInfo() {
        gift_id = getIntent().getStringExtra("gift_id");
        try {
            model.getmyGiftInfo(gift_id, new IModelResult<GiftListEntity.ListsBean>() {
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
        String gfname = entity.getGfname();
        String gfinfo = entity.getGfinfo();
        String gfcontent = entity.getGfcontent();
        String senduser_name = entity.getSenduser_name();
        term_id=entity.getTerm_id();
        String term_name = entity.getTerm_name();
        senduser_id =CommonUtils.formatNull( entity.getSenduser_id());
        String had_time = CommonUtils.formatNull(entity.getHad_time());
        String time = DateUtils.getSystemTime(had_time);
        school_id = entity.getSchool_id();
        school_name = entity.getSchool_name();
        gfName.setText(gfname);
        gfInfo.setText(gfinfo);
        gfContent.setText(gfcontent);
        String primg = entity.getGfimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + primg, gf_image);
        sendUser_name.setText(senduser_name);
        term_Name.setText(term_name);
        Had_time.setText(time);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GiftListEntity.ListsBean gentity = (GiftListEntity.ListsBean) data.getSerializableExtra("giftInfo");
            if (CommonUtils.isNull(gentity)) {
                return;
            }
            initGiftInfo(gentity);
        }
    }


    @OnClick({R.id.iv_back, R.id.close, R.id.senduser_name})
    public void onClick(View view) {
        gift_id = getIntent().getStringExtra("gift_id");
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
            case R.id.term_name:
                Intent tintent = new Intent(mActivity,SeatDeatilActivity.class);
                tintent.putExtra("school_id", school_id+"");
                tintent.putExtra("school", school_name);
                tintent.putExtra("term_id", term_id);
                startActivity(tintent);
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }


}
