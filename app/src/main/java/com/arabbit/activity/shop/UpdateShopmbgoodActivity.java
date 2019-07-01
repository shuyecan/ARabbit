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
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.entity.ShopmbgoodListEntity;
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
 * Created by Administrator on 2018/11/13.
 */

public class UpdateShopmbgoodActivity  extends BaseActivity {

    private static final int UPDATE_GIFT_IMAGE = 321;
    SocialModel model;
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.mb_name)
    EditText tvMbname;
    @InjectView(R.id.mb_info)
    EditText tvMbinfo;
    @InjectView(R.id.mb_price)
    EditText tvMbprice;
    @InjectView(R.id.mb_preprice)
    EditText tvMbpreprice;
    @InjectView(R.id.mb_num)
    EditText tvMbnum;
    @InjectView(R.id.mb_start)
    TextView tvMbstart;
    @InjectView(R.id.mb_end)
    TextView tvMbend;
    @InjectView(R.id.mb_content)
    EditText tvMbcontent;
    @InjectView(R.id.mb_image)
    ImageView mb_image;

    @InjectView(R.id.ll_start)
    LinearLayout ll_start;
    @InjectView(R.id.ll_end)
    LinearLayout ll_end;

    String user_id = "";
    String mbgood_id = "";
    private DatePickDialog startdialog;
    private DatePickDialog enddialog;
    private SimpleDateFormat simpledateformat;
    private String startTime = "";
    private String endTime = "";
    private String mbimg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopmb_good);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        user_id = SPUtils.getString("user_id", "");
        mbgood_id = getIntent().getStringExtra("mbgood_id");
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
                tvMbstart.setText(format);

            }
        });


    }


    public void UpdateShopmbGood(final String mbgood_id,final String mbname,final String info, final String num,
                              final String mb_price, final String preprice, final String starttime,
                              final String endtime, final String content, final String mbimg) {
        try {

            model.updateShopmbGood(mbgood_id,mbname, info, num, mb_price, preprice, starttime, endtime,
                    content,mbimg,  new IModelResult<ShopmbgoodListEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(ShopmbgoodListEntity.ListsBean entity) {
                            ToastUtils.showToastShort(R.string.update_success);
                            Intent intent = new Intent(mActivity, ShopmbgoodActivity.class);
                            intent.putExtra("mbgood_id", mbgood_id);
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
        titleValue.setText("修改秒爆商品");

    }

    @OnClick({R.id.mb_image,R.id.iv_back,R.id.close,R.id.commit,R.id.ll_start,R.id.ll_end})
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
            case R.id.mb_image:
                Intent intent = new Intent();
                intent.setClass(mActivity, PersonalAvatarActivity.class);
                intent.putExtra("title", "添加商品图片");
                intent.putExtra("type", "upload_img");
                startActivityForResult(intent, UPDATE_GIFT_IMAGE);
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity, MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
            case R.id.commit:
                String mbName = UIUtils.getStrEditView(tvMbname);
                if (CommonUtils.isNull(mbName)) {
                    ToastUtils.showToastShort("商品名称不能为空");
                    return;
                }
                String mbInfo = UIUtils.getStrEditView(tvMbinfo);
                if (CommonUtils.isNull(mbInfo)) {
                    ToastUtils.showToastShort("介绍标签不能为空");
                    return;
                }
                String mbNum = UIUtils.getStrEditView(tvMbnum);
                if (CommonUtils.isNull(mbNum)) {
                    ToastUtils.showToastShort("商品数量不能为空");
                    return;
                }
                int i = Integer.parseInt(mbNum);
                if(i<=0){
                    ToastUtils.showToastShort("商品数量要大于0");
                    return;
                }
                String mbPrice = UIUtils.getStrEditView(tvMbprice);
                if (CommonUtils.isNull(mbPrice)) {
                    ToastUtils.showToastShort("秒爆价格不能为空");
                    return;
                }
                String mbPreprice = UIUtils.getStrEditView(tvMbpreprice);
                if (CommonUtils.isNull(mbPreprice)) {
                    ToastUtils.showToastShort("市场价格不能为空");
                    return;
                }

                String mbContent = UIUtils.getStrEditView(tvMbcontent);
                if (CommonUtils.isNull(mbContent)) {
                    ToastUtils.showToastShort("内容说明不能为空");
                    return;
                }
                String mbStarttime = UIUtils.getStrTextView(tvMbstart);
                if (CommonUtils.isNull(mbStarttime)) {
                    ToastUtils.showToastShort("活动开始时间不能为空");
                    return;
                }
                mbStarttime = startTime;
                String mbEnd = UIUtils.getStrTextView(tvMbend);
                if (CommonUtils.isNull(mbEnd)) {
                    ToastUtils.showToastShort("活动结束时间不能为空");
                    return;
                }
                mbEnd = endTime;

                if(TextUtils.isEmpty(mbimg)){
                    ToastUtils.showToastShort("请先选择商品图片");
                    return;
                }
                UpdateShopmbGood(mbgood_id,mbName, mbInfo, mbNum, mbPrice, mbPreprice,
                        mbStarttime, mbEnd, mbContent, mbimg);
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
                    mbimg = value;
                    ImgLoaderUtils.setImageloader(Config.IMG_URL + value, mb_image);
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
