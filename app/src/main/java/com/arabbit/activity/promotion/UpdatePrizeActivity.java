package com.arabbit.activity.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
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
 * Created by Administrator on 2018/8/24.
 */

public class UpdatePrizeActivity extends BaseActivity implements CustomSpinner.OnSpinnerListener {
    private static final int UPDATE_GIFT_IMAGE = 321;
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_img)
    TextView tvImg;
    @InjectView(R.id.pr_name)
    EditText prName;
    @InjectView(R.id.pr_info)
    EditText prInfo;
    @InjectView(R.id.pr_type)
    CustomSpinner ptType;
//    @InjectView(R.id.pr_rank)
//    EditText prRank;
    @InjectView(R.id.pr_num)
    EditText prNum;
    @InjectView(R.id.pr_rate)
    EditText prRate;
    @InjectView(R.id.pr_content)
    EditText prContent;
    @InjectView(R.id.pr_up)
    Button tvPrup;
    @InjectView(R.id.pr_image)
    ImageView pr_image;
    SocialModel model;
    String user_id = "";
    String pr_id = "";
    String term_id = "";
    private String gfimg;
    String type ="";
    private int selectPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprize);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        user_id = SPUtils.getString("user_id", "");
        pr_id = getIntent().getStringExtra("pr_id");
        type =getIntent().getStringExtra("type");
        initTitle();
        initSpinner();
        getPrizeInfo();
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

    private void initTitle() {
        titleValue.setText("修改奖品");
        tvImg.setText("修改图片");
        tvPrup.setText("修改");
    }

    public void getPrizeInfo() {
//        String pr_id = getIntent().getStringExtra("pr_id");
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
        String prname1 = entity.getPrname();
        String info1 = entity.getInfo();
        String num1 = CommonUtils.formatNull(entity.getPrnum());
        String content1 = entity.getContent();
        String rank1 = entity.getRank();
        String rate1 = CommonUtils.formatNull(entity.getPrrate());
        String create_time = entity.getCreate_time();
        prName.setText(prname1);
        prInfo.setText(info1);
        prNum.setText(num1);
        prContent.setText(content1);
        if(TextUtils.equals(rank1,"0")){
            ptType.setSelection(0);
        }else if(TextUtils.equals(rank1,"1")){
            ptType.setSelection(1);
        }else if(TextUtils.equals(rank1,"2")){
            ptType.setSelection(2);
        }else if(TextUtils.equals(rank1,"3")){
            ptType.setSelection(3);
        }
//        prRank.setText(rank1);
        prRate.setText(rate1);
        gfimg = entity.getPrimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + gfimg, pr_image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(requestCode == UPDATE_GIFT_IMAGE){
                String value = data.getStringExtra("value");
//                Log.e("aaa","添加奖品："+value);
                gfimg = value;
                ImgLoaderUtils.setImageloader(Config.IMG_URL + value, pr_image);
            }else{
                PrizeListEntity.ListsBean pentity = (PrizeListEntity.ListsBean) data.getSerializableExtra("PrizeInfo");
                if (CommonUtils.isNull(pentity)) {
                    return;
                }
                initPrizeInfo(pentity);
            }

        }
    }

    @OnClick({R.id.iv_back, R.id.pr_up, R.id.close,R.id.pr_image})
    public void onClick(View view) {
        pr_id = getIntent().getStringExtra("pr_id");
        switch (view.getId()) {
            case R.id.pr_image:
                Intent intent1 = new Intent();
                intent1.setClass(mActivity, PersonalAvatarActivity.class);
                intent1.putExtra("type", "upload_img");
                startActivityForResult(intent1, UPDATE_GIFT_IMAGE);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.pr_up:
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
//                String Prrank = UIUtils.getStrEditView(prRank);
//                if (CommonUtils.isNull(Prrank)) {
//                    ToastUtils.showToastShort("奖品等级不能为空");
//                    return;
//                }
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
                String trim = prRate.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    ToastUtils.showToastShort("中奖率不能为空");
                    return;
                }
                String Prrate = UIUtils.getStrEditView(prRate);
                if (CommonUtils.isNull(Prrate)) {
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
                if(TextUtils.isEmpty(gfimg)){
                    ToastUtils.showToastShort("请先选择奖品图片");
                    return;
                }

                updatePrize(pr_id,gfimg, Prname, Prinfo, Prcontent, selectPosition+"", Prnum, Prrate);

                ToastUtils.showToastShort("修改成功");
                Intent intent = new Intent(mActivity, PrizeInfoActivity.class);
                intent.putExtra("pr_id", pr_id);
//                            intent.putExtra("term_id", term_id);
                setResult(RESULT_OK, intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity, MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
        }
    }

    public void updatePrize(final String pr_id,final String pr_img, final String prname, final String info, final String content, final String rank,
                            final String num, final String rate) {
        try {
            model.updatePrize(pr_id,pr_img, prname, info, content, rank, num, rate,
                    new IModelResult<PrizeListEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(PrizeListEntity.ListsBean entity) {
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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }


}
