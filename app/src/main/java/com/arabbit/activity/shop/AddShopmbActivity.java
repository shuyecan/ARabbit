package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.entity.ShopmbListEntity;
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
 * Created by Administrator on 2018/11/3.
 */

public class AddShopmbActivity extends BaseActivity {

//    private static final int UPDATE_GIFT_IMAGE = 521;
    SocialModel model;
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.mb_title)
    EditText tvMbtitle;
    @InjectView(R.id.mb_far)
    EditText tvMbfar;
    @InjectView(R.id.mb_start)
    TextView tvMbstart;
    @InjectView(R.id.mb_end)
    TextView tvMbend;
    @InjectView(R.id.mb_rule)
    EditText tvRule;

    @InjectView(R.id.ll_start)
    LinearLayout ll_start;
    @InjectView(R.id.ll_end)
    LinearLayout ll_end;

    String user_id = "";
    private DatePickDialog startdialog;
    private DatePickDialog enddialog;
    private SimpleDateFormat simpledateformat;
    private String startTime = "";
    private String endTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopmb);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        user_id = SPUtils.getString("user_id", "");
        initTitle();
        initStartPicker();
        initEndPicker();
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd");

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
        enddialog.setMessageFormat("yyyy-MM-dd");
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
                tvMbend.setText(format);

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
        startdialog.setMessageFormat("yyyy-MM-dd");
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
                tvMbstart.setText(format);

            }
        });


    }


    public void AddShopmb(final String user_id, final String title,final String far, final String starttime,
                        final String endtime, final String rule
                        ) {
        try {

            model.addShopmb(user_id,title, far, starttime, endtime, rule,
                    new IModelResult<ShopmbListEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(ShopmbListEntity.ListsBean entity) {
                            ToastUtils.showToastShort(R.string.add_success);
                            Intent intent = new Intent(mActivity, ShopmbListActivity.class);
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
        titleValue.setText("添加秒爆促销活动");

    }

    @OnClick({R.id.iv_back,R.id.close,R.id.commit,R.id.ll_start,R.id.ll_end})
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
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity, MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
            case R.id.commit:
                String Mbtitle = UIUtils.getStrEditView(tvMbtitle);
                if (CommonUtils.isNull(Mbtitle)) {
                    ToastUtils.showToastShort("活动主题不能为空");
                    return;
                }
                String Mbfar = UIUtils.getStrEditView(tvMbfar);
                if (CommonUtils.isNull(Mbfar)) {
                    ToastUtils.showToastShort("活动距离不能为空");
                    return;
                }

                String Rule = UIUtils.getStrEditView(tvRule);
                if (CommonUtils.isNull(Rule)) {
                    ToastUtils.showToastShort("活动规则不能为空");
                    return;
                }
                String mbStart = UIUtils.getStrTextView(tvMbstart);
                if (CommonUtils.isNull(mbStart)) {
                    ToastUtils.showToastShort("活动开始时间不能为空");
                    return;
                }
                mbStart = startTime;
                String mbEnd = UIUtils.getStrTextView(tvMbend);
                if (CommonUtils.isNull(mbEnd)) {
                    ToastUtils.showToastShort("活动结束时间不能为空");
                    return;
                }
                mbEnd = endTime;


                AddShopmb(user_id,Mbtitle,Mbfar, Rule, mbStart, mbEnd);
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            String value = data.getStringExtra("value");
//            switch (requestCode) {
//                case UPDATE_GIFT_IMAGE:
//                    Log.e("aaa","时间戳："+value);
//                    break;
//
//            }
//        }
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }


}
