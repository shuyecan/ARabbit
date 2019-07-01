package com.arabbit.activity.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.entity.AddgiftEntity;
import com.arabbit.entity.GiftListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.model.IModelResult;
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
 * Created by Administrator on 2018/6/18.
 */

public class AddgiftActivity extends BaseActivity {

    private static final int UPDATE_GIFT_IMAGE = 321;
    SocialModel model;
    @InjectView(R.id.title_value)
    TextView titleValue;
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
    @InjectView(R.id.gf_image)
    ImageView gf_image;

    @InjectView(R.id.ll_start)
    LinearLayout ll_start;
    @InjectView(R.id.ll_end)
    LinearLayout ll_end;

    String user_id = "";
    String term_id = "";
    String school_id = "";
    private DatePickDialog startdialog;
    private DatePickDialog enddialog;
    private SimpleDateFormat simpledateformat;
    private String startTime = "";
    private String endTime = "";
    private String gfimg = "";
//    private String b_id = "";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgift);
        ButterKnife.inject(this);
        if (!CacheActivity.activityList.contains(AddgiftActivity.this)) {
            CacheActivity.addActivity(AddgiftActivity.this);
        }
        model = new SocialModel(AddgiftActivity.this);
        user_id = SPUtils.getString("user_id", "");
        term_id = getIntent().getStringExtra("term_id");
        school_id = getIntent().getStringExtra("school_id");
//        b_id = getIntent().getStringExtra("b_id");
        Log.e("aaa","提交的参数term_id："+term_id+",school_id："+school_id+"，b_id：");
        initTitle();
        initStartPicker();
        initEndPicker();
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
                Log.e("aaa","enddialog时间戳："+time);
                endTime = time+"";
                String format = simpledateformat.format(new Date(time));
                Log.e("aaa","enddialog格式化后的日期："+format);
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
                startTime = time+"";
                Log.e("aaa","时间戳："+time);
                String format = simpledateformat.format(new Date(time));
                Log.e("aaa","格式化后的日期："+format);
                tvGfstart.setText(format);

            }
        });


    }


    public void Addgift(final String gfname,final String gfimg1, final String gfinfo, final String gfnum, final String gfcontent,
                        final String gfstart, final String gfend, final String gffar,
                        final String user_id, final String school_id, final String term_id) {
        try {

            model.addGift(gfname,gfimg1, gfinfo, gfnum, gfcontent, gfstart, gfend, gffar,
                    user_id, school_id, term_id, new IModelResult<GiftListEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(GiftListEntity.ListsBean entity) {
                            ToastUtils.showToastShort(R.string.update_success);
                            Intent intent = new Intent(mActivity, UpgiftActivity.class);
                            intent.putExtra("school_id", school_id);
                            intent.putExtra("term_id", term_id);
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


    private void initTitle() {
        titleValue.setText("添加礼品");

    }

    @OnClick({R.id.gf_image,R.id.iv_back,R.id.close,R.id.gf_up,R.id.ll_start,R.id.ll_end})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_start:
                startdialog.show();
                break;
            case R.id.ll_end:
                enddialog.show();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.gf_image:
                Intent intent = new Intent();
                intent.setClass(mActivity, PersonalAvatarActivity.class);
                intent.putExtra("title", "添加礼品图片");
                intent.putExtra("type", "upload_img");
                startActivityForResult(intent, UPDATE_GIFT_IMAGE);
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity, MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
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
                int i = Integer.parseInt(gfNum);
                if(i<=0){
                    ToastUtils.showToastShort("礼品数量要大于0");
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
                gfStart = startTime;
                String gfEnd = UIUtils.getStrTextView(tvGfend);
                if (CommonUtils.isNull(gfEnd)) {
                    ToastUtils.showToastShort("活动结束时间不能为空");
                    return;
                }
                gfEnd = endTime;
                String gfFar = UIUtils.getStrEditView(tvGffar);
                if (CommonUtils.isNull(gfFar)) {
                    ToastUtils.showToastShort("领取距离不能为空");
                    return;
                }
                if(TextUtils.isEmpty(gfimg)){
                    ToastUtils.showToastShort("请先选择礼物图片");
                    return;
                }
                Addgift(gfName,gfimg, gfInfo, gfNum, gfContent, gfStart,
                        gfEnd, gfFar, user_id, school_id, term_id);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String value = data.getStringExtra("value");
            switch (requestCode) {
                case UPDATE_GIFT_IMAGE:
                    Log.e("aaa","时间戳："+value);
                    gfimg = value;
                    ImgLoaderUtils.setImageloader(Config.IMG_URL + value, gf_image);
                    break;

            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}