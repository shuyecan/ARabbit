package com.arabbit.activity.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.entity.GiftListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/8/20.
 */

public class UpdateGiftActivity extends BaseActivity {
    private static final int UPDATE_GIFT_IMAGE = 321;
    SocialModel model;
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.gf_bt)
    TextView gfBttext;
    @InjectView(R.id.gf_name)
    EditText tvGfname;
    @InjectView(R.id.gf_info)
    EditText tvGfinfo;
    @InjectView(R.id.gf_num)
    EditText tvGfnum;
    @InjectView(R.id.gf_content)
    EditText tvGfcontent;
    @InjectView(R.id.gf_start)
    TextView tvGfstart;
    @InjectView(R.id.gf_end)
    TextView tvGfend;
    @InjectView(R.id.gf_far)
    EditText tvGffar;
    @InjectView(R.id.gf_up)
    Button tvGfup;
    @InjectView(R.id.gf_image)
    ImageView gf_image;
    String gift_id = "";
    private String headurl = "";
    private String starttime;
    private String endtime;
    @InjectView(R.id.ll_start)
    LinearLayout ll_start;
    @InjectView(R.id.ll_end)
    LinearLayout ll_end;
    private DatePickDialog startdialog;
    private DatePickDialog enddialog;
    private SimpleDateFormat simpledateformat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgift);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        gift_id = getIntent().getStringExtra("gift_id");
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        initTitle();
        getGiftInfo();
        initStartPicker();
        initEndPicker();

    }
    private void initEndPicker() {
        enddialog = new DatePickDialog(this);
        //设置上下年分限制
        enddialog.setYearLimt(5);
        //设置标题
        enddialog.setTitle("选择时间");
        //设置类型
        enddialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        enddialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        enddialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        enddialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                long time = date.getTime();
//                Log.e("aaa","enddialog时间戳："+time);
                endtime = time+"";
                String format = simpledateformat.format(new Date(time));
//                Log.e("aaa","enddialog格式化后的日期："+format);
                tvGfend.setText(format);

            }
        });
    }

    private void initStartPicker() {
        startdialog = new DatePickDialog(this);
        //设置上下年分限制
        startdialog.setYearLimt(5);
        //设置标题
        startdialog.setTitle("选择时间");
        //设置类型
        startdialog.setType(DateType.TYPE_ALL);
        //设置消息体的显示格式，日期格式
        startdialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        startdialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        startdialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                long time = date.getTime();
                starttime = time+"";
//                Log.e("aaa","时间戳："+time);
                String format = simpledateformat.format(new Date(time));
//                Log.e("aaa","格式化后的日期："+format);
                tvGfstart.setText(format);

            }
        });


    }

    public void updateGift(final String gift_id,final String gift_img, final String gfname, final String gfinfo, final String gfnum, final String gfcontent,
                           final String gfstart, final String gfend, final String gffar) {
        try {
            model.updateGift(gift_id,gift_img, gfname, gfinfo, gfnum, gfcontent, gfstart, gfend, gffar,
                    new IModelResult<GiftListEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(GiftListEntity.ListsBean entity) {
                            ToastUtils.showToastShort(R.string.update_success);
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


    private void initTitle() {
        titleValue.setText("修改礼品");
        gfBttext.setText("修改图片");
        tvGfup.setText("修改");
    }

    public void getGiftInfo() {
//        gift_id = getIntent().getStringExtra("gift_id");
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
//        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String gfname1 = entity.getGfname();
        String gfinfo1 = entity.getGfinfo();
        String gfnum1 = entity.getGfnum();
        String gfcontent1 = entity.getGfcontent();
        String gfstart1 = entity.getGfstart();
        String gfend1 = entity.getGfend();
        starttime = gfstart1;
        endtime = gfstart1;
        String gffar1 = entity.getGffar();
        tvGfname.setText(gfname1);
        tvGfinfo.setText(gfinfo1);
        tvGfnum.setText(gfnum1);
        tvGfcontent.setText(gfcontent1);
//        tvGfstart.setText(gfstart1);
//        tvGfend.setText(gfend1);
        tvGffar.setText(gffar1);
        try {
            String startformat = simpledateformat.format(new Date(Long.parseLong(gfstart1)));
            tvGfstart.setText(startformat);
            String endformat = simpledateformat.format(new Date(Long.parseLong(gfend1)));
            tvGfend.setText(endformat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String gfimg = entity.getGfimg();
        headurl = gfimg;
        Log.e("aaa","礼品图片链接："+gfimg);
        ImgLoaderUtils.setImageloader(Config.IMG_URL + gfimg, gf_image);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(requestCode == UPDATE_GIFT_IMAGE){
                String value = data.getStringExtra("value");
                Log.e("aaa","时间戳："+value);
                headurl = value;
                ImgLoaderUtils.setImageloader(Config.IMG_URL + value, gf_image);
            }else{
                GiftListEntity.ListsBean entity = (GiftListEntity.ListsBean) data.getSerializableExtra("giftInfo");
                if (CommonUtils.isNull(entity)) {
                    return;
                }
                initGiftInfo(entity);
            }






        }
    }

    @OnClick({R.id.gf_image,R.id.iv_back, R.id.gf_up, R.id.close,R.id.ll_start,R.id.ll_end})
    public void onClick(View view) {
        gift_id = getIntent().getStringExtra("gift_id");
        String term_id = getIntent().getStringExtra("term_id");
        switch (view.getId()) {
            case R.id.ll_start:
                startdialog.show();
                break;
            case R.id.ll_end:
                enddialog.show();
                break;
            case R.id.gf_image:
                Intent intent1 = new Intent();
                intent1.setClass(mActivity, PersonalAvatarActivity.class);
                intent1.putExtra("url", headurl);
                intent1.putExtra("title", "选择礼品图片");
                intent1.putExtra("type", "upload_img");
                startActivityForResult(intent1, UPDATE_GIFT_IMAGE);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.gf_up:
                String gfName = UIUtils.getStrEditView(tvGfname);
                if (CommonUtils.isNull(gfName)) {
                    ToastUtils.showToastShort("礼品名称不能为空");
                    return;
                }
                String gfInfo = UIUtils.getStrEditView(tvGfinfo);
                if (CommonUtils.isNull(gfInfo)) {
                    ToastUtils.showToastShort("介绍标签不能为空");
                    return;
                }
                String gfNum = UIUtils.getStrEditView(tvGfnum);
                if (CommonUtils.isNull(gfNum)) {
                    ToastUtils.showToastShort("礼品数量不能为空");
                    return;
                }
                String gfContent = UIUtils.getStrEditView(tvGfcontent);
                if (CommonUtils.isNull(gfContent)) {
                    ToastUtils.showToastShort("内容说明不能为空");
                    return;
                }
                String gfStart = UIUtils.getStrTextView(tvGfstart);
                if (CommonUtils.isNull(gfStart)) {
                    ToastUtils.showToastShort("活动开始时间不能为空");
                    return;
                }
                gfStart = starttime;
                String gfEnd = UIUtils.getStrTextView(tvGfend);
                if (CommonUtils.isNull(gfEnd)) {
                    ToastUtils.showToastShort("活动结束时间不能为空");
                    return;
                }
                gfEnd = endtime;
                String gfFar = UIUtils.getStrEditView(tvGffar);
                if (CommonUtils.isNull(gfFar)) {
                    ToastUtils.showToastShort("领取距离不能为空");
                    return;
                }
                updateGift(gift_id,headurl, gfName, gfInfo, gfNum, gfContent, gfStart,
                        gfEnd, gfFar);
                Intent intent = new Intent(mActivity, GiftinfoActivity.class);
                intent.putExtra("gift_id", gift_id);
                intent.putExtra("term_id", term_id);
                setResult(RESULT_OK, intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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
