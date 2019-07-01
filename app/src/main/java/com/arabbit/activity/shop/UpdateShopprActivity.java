package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.entity.ShopprListEntity;
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
import com.arabbit.view.spinner.widget.CustomSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/13.
 */

public class UpdateShopprActivity extends BaseActivity implements CustomSpinner.OnSpinnerListener{

    private static final int UPDATE_GIFT_IMAGE = 321;
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.pr_name)
    EditText prName;
    @InjectView(R.id.pr_info)
    EditText prInfo;
    @InjectView(R.id.pr_type)
    CustomSpinner ptType;
    @InjectView(R.id.pr_num)
    EditText prNum;
    @InjectView(R.id.pr_rate)
    EditText prRate;
    @InjectView(R.id.pr_content)
    EditText prContent;
    @InjectView(R.id.pr_price)
    EditText prPrice;
    @InjectView(R.id.pr_image)
    ImageView pr_image;
    SocialModel model;
    String user_id = "";
    String pr_id ="";
    String type ="";
    private String primg = "";
    private int selectPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shoppr);
        ButterKnife.inject(this);
        user_id = SPUtils.getString("user_id", "");
        pr_id =getIntent().getStringExtra("pr_id");
        type =getIntent().getStringExtra("type");
        model = new SocialModel(this);
        initSpinner();
        initTitle();
    }


    private void initTitle() {
        titleValue.setText("修改奖品");
//        prRank.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });

    }
    private void initSpinner() {
        List<String> list = new ArrayList<>();

        String[] formats = getResources().getStringArray(R.array.przie_rank);

        Collections.addAll(list, formats);

        ptType.setResource(list, null);
        ptType.setSelection(0);
        ptType.setOnSpinnerListener(this);
    }

    @Override
    public void onSpinnerItemSelected(String selected, View view) {
        switch (view.getId()) {
            case R.id.pr_type:
                String rank_text = "";
                selectPosition = ptType.getSelectPosition();
                String[] formats = getResources().getStringArray(R.array.przie_rank);
                String ranktext = formats[selectPosition];
                Log.e("aaa",",活动类型选择:"+ ranktext);
                break;
        }
    }

    @Override
    public void onSpinnerItemChanged(String changed, View view) {

    }

    @OnClick({R.id.iv_back,R.id.commit, R.id.close, R.id.pr_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pr_image:
                Intent intent = new Intent();
                intent.setClass(mActivity, PersonalAvatarActivity.class);
                intent.putExtra("title", "修改奖品图片");
                intent.putExtra("type", "upload_img");
                startActivityForResult(intent, UPDATE_GIFT_IMAGE);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.commit:
                pr_id = getIntent().getStringExtra("pr_id");
                String Prname = UIUtils.getStrEditView(prName);
                if (CommonUtils.isNull(Prname)) {
                    ToastUtils.showToastShort("奖品名称不能为空");
                    return;
                }
                String Prinfo = UIUtils.getStrEditView(prInfo);
                if (CommonUtils.isNull(Prinfo)) {
                    ToastUtils.showToastShort("标签介绍不能为空");
                    return;
                }
                String Prcontent = UIUtils.getStrEditView(prContent);
                if (CommonUtils.isNull(Prcontent)) {
                    ToastUtils.showToastShort("详细说明不能为空");
                    return;
                }
                String Prprice = UIUtils.getStrTextView(prPrice);
                if (CommonUtils.isNull(Prprice)) {
                    ToastUtils.showToastShort("市场价不能为空");
                    return;
                }
                String Prnum = UIUtils.getStrEditView(prNum);
                if (CommonUtils.isNull(Prnum)) {
                    ToastUtils.showToastShort("奖品数量不能为空");
                    return;
                }
                int i1 = Integer.parseInt(Prnum);
                if(i1<=0){
                    ToastUtils.showToastShort("奖品数量要大于0");
                    return;
                }
                String Prrate = UIUtils.getStrEditView(prRate);
                if (CommonUtils.isNull(Prrate)) {
                    ToastUtils.showToastShort("中奖率不能为空");
                    return;
                }
                String trim = prRate.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    ToastUtils.showToastShort("中奖率不能为空");
                    return;
                }
                int i = Integer.parseInt(trim);
                if(TextUtils.equals(type,"1")){//普通抽奖
                    if(5<i&&i<6001){

                    }else{
                        ToastUtils.showToastShort("普通抽奖活动，中奖率不得超过6000，小于5");
                        return;
                    }

                }else{//秒爆抽奖
                    if(5<i&&i<1501){

                    }else{
                        ToastUtils.showToastShort("秒爆抽奖活动，中奖率不得超过1500，小于5");
                        return;
                    }

                }


                if(TextUtils.isEmpty(primg)){
                    ToastUtils.showToastShort("请先上传奖品图片");
                    return;
                }

                UpdatePrize(Prname,primg, Prinfo, Prcontent, selectPosition+"", Prnum, Prrate, Prprice , pr_id);
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity,MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
        }
    }


    public void UpdatePrize(final String prname,final String gfimgs, final String info, final String content, final String rank,
                         final String num, final String rate, final String price, final String pr_id) {
        try {
            model.updateShopPrize(prname,gfimgs, info, content, rank, num,rate, price,pr_id,
                    new IModelResult<ShopprListEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(ShopprListEntity.ListsBean entity) {
                            ToastUtils.showToastShort(R.string.add_success);
                            Intent intent = new Intent(mActivity, ShopprinfoActivity.class);
                            intent.putExtra("pr_id", pr_id);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String value = data.getStringExtra("value");
            switch (requestCode) {
                case UPDATE_GIFT_IMAGE:
                    Log.e("aaa","修改奖品："+value);
                    primg = value;
                    ImgLoaderUtils.setImageloader(Config.IMG_URL + value, pr_image);
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

