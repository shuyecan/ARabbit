package com.arabbit.activity.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.entity.ProtionListEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;
import com.arabbit.view.spinner.widget.CustomSpinner;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/7/9.
 */

public class AddprotionActivity extends BaseActivity implements CustomSpinner.OnSpinnerListener {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.pr_far)
    EditText prFar;
    @InjectView(R.id.pr_start)
    TextView prStart;
    @InjectView(R.id.pr_end)
    TextView prEnd;
    @InjectView(R.id.pr_rule)
    EditText prRule;
    @InjectView(R.id.pr_type)
    CustomSpinner ptType;

    SocialModel model;
    String pttype_text = "1";
    @InjectView(R.id.ll_start)
    LinearLayout ll_start;
    @InjectView(R.id.ll_end)
    LinearLayout ll_end;

    private DatePickDialog startdialog;
    private DatePickDialog enddialog;
    private SimpleDateFormat simpledateformat;
    private String startTime = "";
    private String endTime = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_addprotion);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initSpinner();
        initTitle();
        initStartPicker();
        initEndPicker();
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }
    private void initTitle() {
        titleValue.setText("添加抽奖活动");

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
                prEnd.setText(format);

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
                prStart.setText(format);
                if(pttype_text.equals("2")){

                    endTime = (time+600000)+"";
                    String format1 = simpledateformat.format(new Date(time+600000));
                    prEnd.setText(format1);
                }

            }
        });


    }
    @OnClick({R.id.iv_back,R.id.pr_up, R.id.close,R.id.ll_start,R.id.ll_end})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_start:
                startdialog.show();
                break;
            case R.id.ll_end:
                if(pttype_text.equals("1")){
                    enddialog.show();
                }

                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.pr_up:
                String term_id = getIntent().getStringExtra("term_id");
                String school_id = getIntent().getStringExtra("school_id");
                String user_id = SPUtils.getString("user_id", "");
                String Far = UIUtils.getStrEditView(prFar);
                if (CommonUtils.isNull(Far)) {
                    ToastUtils.showToastShort("活动距离不能为空");
                    return;
                }
                String Starttime = UIUtils.getStrTextView(prStart);
                if (CommonUtils.isNull(Starttime)) {
                    ToastUtils.showToastShort("开始时间不能为空");
                    return;
                }
                Starttime = startTime;
                String Endtime = UIUtils.getStrTextView(prEnd);
                if (CommonUtils.isNull(Endtime)) {
                    ToastUtils.showToastShort("结束时间不能为空");
                    return;
                }
                Endtime = endTime;
                String Rule = UIUtils.getStrEditView(prRule);
//                if (CommonUtils.isNull(Rule)) {
//                    ToastUtils.showToastShort("规则说明不能为空");
//                    return;
//                }
                Log.e("aaa",",活动类型选择addProtion:"+pttype_text);
                addProtion(pttype_text, Far, Starttime, Endtime, Rule, user_id, school_id, term_id);
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity,MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
        }
    }

    public void addProtion(final String type, final String far, final String starttime, final String endtime,
                           final String rule,
                           final String user_id, final String school_id, final String term_id) {
        try {
            Log.e("aaa","活动类型:"+type);
            model.addProtion(type, far, starttime, endtime, rule,
                    user_id, school_id, term_id, new IModelResult<ProtionListEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(ProtionListEntity.ListsBean entity) {
                            ToastUtils.showToastShort(R.string.add_success);
                            Intent intent = new Intent(mActivity, UpprizeActivity.class);
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

    private void initSpinner() {
        List<String> list = new ArrayList<>();

        String[] formats = getResources().getStringArray(R.array.przie_type);

        Collections.addAll(list, formats);

        ptType.setResource(list, null);
        ptType.setSelection(0);
        ptType.setOnSpinnerListener(this);
    }

    String IdentityType;

    @Override
    public void onSpinnerItemSelected(String selected, View view) {
        switch (view.getId()) {
            case R.id.pr_type:
                IdentityType = ptType.getSelectedItem();
                int selectPosition = ptType.getSelectPosition();
                Log.e("aaa",selectPosition+",活动类型选择文字:"+IdentityType);
                if (IdentityType.equals(getString(R.string.const_przie))) {
                    pttype_text = "1";
                } else if (IdentityType.equals(getString(R.string.miaob_przie))) {
                    pttype_text = "2";
                }
                Log.e("aaa",",活动类型选择:"+pttype_text);
                break;
        }
    }

    @Override
    public void onSpinnerItemChanged(String changed, View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
