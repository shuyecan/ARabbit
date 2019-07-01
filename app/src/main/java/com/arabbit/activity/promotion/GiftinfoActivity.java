package com.arabbit.activity.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.entity.GiftListEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/7/8.
 */

public class GiftinfoActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.jo_count)
    TextView joCount;
    @InjectView(R.id.gf_count)
    TextView gfCount;
    @InjectView(R.id.gf_name)
    TextView  gfName;
    @InjectView(R.id.gf_info)
    TextView gfInfo;
    @InjectView(R.id.gf_num)
    TextView gfNum;
    @InjectView(R.id.gf_far)
    TextView gfFar;
    @InjectView(R.id.gf_start)
    TextView gfStart;
    @InjectView(R.id.gf_end)
    TextView gfEnd;
    @InjectView(R.id.gf_content)
    TextView gfContent;
    @InjectView(R.id.gf_image)
    ImageView gf_image;

    SocialModel model;
    String gift_id = "";
    String term_id =  "";
    private SimpleDateFormat simpledateformat;
    private String endStamp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftinfo);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initTitle();
        getGiftInfo();

    }



    private void initTitle() {
        titleValue.setText("礼品信息");
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
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String gfname = entity.getGfname();
        String gfinfo = entity.getGfinfo();
        String gfnum = entity.getGfnum();
        String gfcontent = entity.getGfcontent();
        String gfstart = entity.getGfstart();
        String gfend = entity.getGfend();
        endStamp = gfend;
        String gffar = entity.getGffar();
        gfName.setText(gfname);
        gfInfo.setText(gfinfo);
        gfNum.setText(gfnum);
        gfContent.setText(gfcontent);
        gfFar.setText(gffar);
        int ava_num = entity.getAva_num();
        gfCount.setText(ava_num+"");
        int ava_num2 = entity.getAva_num2();
        joCount.setText(ava_num2+"");
        try {
            String startformat = simpledateformat.format(new Date(Long.parseLong(gfstart)));
            gfStart.setText(startformat);
            String endformat = simpledateformat.format(new Date(Long.parseLong(gfend)));
            gfEnd.setText(endformat);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String gfimg = entity.getGfimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + gfimg, gf_image);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GiftListEntity.ListsBean entity = (GiftListEntity.ListsBean) data.getSerializableExtra("giftInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initGiftInfo(entity);
        }
    }

    public void delGift() {
        gift_id = getIntent().getStringExtra("gift_id");
        try {
            model.delGift(gift_id, new IModelResult<List<GiftListEntity.ListsBean>>() {
                @Override
                public void OnSuccess(List<GiftListEntity.ListsBean> entity) {

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

    @OnClick({R.id.gf_del,R.id.iv_back,R.id.gf_ed, R.id.close, R.id.gf_user, R.id.jo_user})
    public void onClick(View view) {
        gift_id = getIntent().getStringExtra("gift_id");
        term_id = getIntent().getStringExtra("term_id");
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.gf_del:
                long currentTimeStamp2 = System.currentTimeMillis();
                Log.e("aaa","时间戳"+endStamp);
                long endtimeStamp1 = Long.parseLong(endStamp);


                if(endtimeStamp1 < currentTimeStamp2){
                    ToastUtils.showToastShort("活动已结束，不能再修改");
                    return;
                }
                delGift();
                ToastUtils.showToastShort(  " 删除成功" );
                Intent intent = new Intent(mActivity,UpgiftActivity.class);
                intent.putExtra("term_id",term_id);
                startActivity(intent);
                finish();
                break;
            case R.id.gf_ed:
                try {
                    long currentTimeStamp = System.currentTimeMillis();
                    Log.e("aaa","时间戳"+endStamp);
                    long endtimeStamp = Long.parseLong(endStamp);


                    if(endtimeStamp < currentTimeStamp){
                        ToastUtils.showToastShort("活动已结束，不能再修改");
                        return;
                    }
                    Intent intent1 = new Intent(mActivity,UpdateGiftActivity.class);
                    intent1.putExtra("gift_id",gift_id);
                    intent1.putExtra("term_id",term_id);
                    startActivity(intent1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity,MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
            case R.id.gf_user:
                Intent intent2 = new Intent(mActivity, GiftUserActivity.class);
                intent2.putExtra("gift_id",gift_id);
                startActivityForResult(intent2, 708);
                break;
            case R.id.jo_user:
                Intent intent3 = new Intent(mActivity, JoinUserActivity.class);
                intent3.putExtra("gift_id",gift_id);
                startActivityForResult(intent3, 709);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}
