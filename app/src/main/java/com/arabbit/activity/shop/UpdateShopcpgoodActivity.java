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
import com.arabbit.entity.ShopcpListEntity;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/13.
 */

public class UpdateShopcpgoodActivity extends BaseActivity {

    private static final int UPDATE_GIFT_IMAGE = 321;

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.cp_name)
    EditText tvCpname;
    @InjectView(R.id.cp_info)
    EditText tvCpinfo;
    @InjectView(R.id.cp_price)
    EditText tvCpprice;
    @InjectView(R.id.cp_preprice)
    EditText tvCppreprice;
    @InjectView(R.id.cp_num)
    EditText tvCpnum;
    @InjectView(R.id.cp_content)
    EditText tvCpcontent;
    @InjectView(R.id.cp_image)
    ImageView cp_image;

    String user_id = "";
    String cp_id = "";
    private String cpimg = "";
    SocialModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopcp_good);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        user_id = SPUtils.getString("user_id", "");
        initTitle();
    }



    public void UpdateShopcpGood(final String cp_id,final String cpname,final String info, final String num,
                              final String cp_price, final String preprice,final String pullon,
                              final String content, final String cpimg) {
        try {

            model.updateShopcpGood(cp_id,cpname, info, num, cp_price, preprice,pullon,
                    content,cpimg, new IModelResult<ShopcpListEntity.ListsBean>() {
                        @Override
                        public void OnSuccess(ShopcpListEntity.ListsBean entity) {
                            ToastUtils.showToastShort(R.string.update_success);
                            Intent intent = new Intent(mActivity, ShopcpActivity.class);
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
        titleValue.setText("修改打折商品");

    }

    @OnClick({R.id.cp_image,R.id.iv_back,R.id.close,R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.cp_image:
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
                user_id = SPUtils.getString("user_id", "");
                String cpName = UIUtils.getStrEditView(tvCpname);
                if (CommonUtils.isNull(cpName)) {
                    ToastUtils.showToastShort("商品名称不能为空");
                    return;
                }
                String cpInfo = UIUtils.getStrEditView(tvCpinfo);
                if (CommonUtils.isNull(cpInfo)) {
                    ToastUtils.showToastShort("介绍标签不能为空");
                    return;
                }
                String cpNum = UIUtils.getStrEditView(tvCpnum);
                if (CommonUtils.isNull(cpNum)) {
                    ToastUtils.showToastShort("商品数量不能为空");
                    return;
                }
                int i = Integer.parseInt(cpNum);
                if(i<=0){
                    ToastUtils.showToastShort("商品数量要大于0");
                    return;
                }
                String cpPrice = UIUtils.getStrEditView(tvCpprice);
                if (CommonUtils.isNull(cpPrice)) {
                    ToastUtils.showToastShort("价格不能为空");
                    return;
                }
                String cpPreprice = UIUtils.getStrEditView(tvCppreprice);
                if (CommonUtils.isNull(cpPreprice)) {
                    ToastUtils.showToastShort("市场价格不能为空");
                    return;
                }
                //是否上架
                String cpPullon = "是";

                String cpContent = UIUtils.getStrEditView(tvCpcontent);
                if (CommonUtils.isNull(cpContent)) {
                    ToastUtils.showToastShort("内容说明不能为空");
                    return;
                }


                if(TextUtils.isEmpty(cpimg)){
                    ToastUtils.showToastShort("请先选择商品图片");
                    return;
                }
                UpdateShopcpGood(cp_id,cpName, cpInfo, cpNum, cpPrice, cpPreprice,cpPullon,
                        cpContent, cpimg);
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
                    cpimg = value;
                    ImgLoaderUtils.setImageloader(Config.IMG_URL + value, cp_image);
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
