package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.entity.GetUserCardEntity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/7/29.
 */

public class UserCardActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.iv_image)
    ImageView ivImage;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_account)
    TextView tvAccount;

    @InjectView(R.id.real_name)
    TextView tvReal_name;
    @InjectView(R.id.user_com)
    TextView tvUser_com;
    @InjectView(R.id.user_job)
    TextView tvUser_job;
    @InjectView(R.id.com_address)
    TextView tvCom_address;
    @InjectView(R.id.com_page)
    TextView tvCom_page;
    @InjectView(R.id.user_phone)
    TextView tvUser_phone;
    @InjectView(R.id.user_tel)
    TextView tvUser_tel;
    @InjectView(R.id.user_fax)
    TextView tvUser_fax;
    @InjectView(R.id.user_email)
    TextView tvUser_email;
    @InjectView(R.id.user_qq)
    TextView tvUser_qq;
    @InjectView(R.id.wechat)
    TextView tvWechat;
    @InjectView(R.id.post_address)
    TextView tvPost_address;
    @InjectView(R.id.com_intro)
    TextView tvCom_intro;

    SocialModel model;
    String user_id = SPUtils.getString("user_id", "");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_card);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initTitle();
        getUserInfo();
        getUserCard();

    }


    public void getUserInfo() {
//        String user_id = SPUtils.getString("user_id", "");
        try {
            model.getUserInfo(user_id, new IModelResult<GetUserInfoEntity>() {
                @Override
                public void OnSuccess(GetUserInfoEntity entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initUserInfo(entity);

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

    private void initUserInfo(GetUserInfoEntity entity) {
        String url = entity.getAvatar_img();
        String name = entity.getNickname();
        String account = entity.getAccount();
        String type = CommonUtils.formatNull(entity.getRole());
        String address = entity.getAddress();
        String school = entity.getSchool();
        tvName.setText(name);
        if (!CommonUtils.isNull(url)) {
            ImgLoaderUtils.setImageloader(Config.IMG_URL + url, ivImage);
        }
        tvAddress.setText(address);
        tvAccount.setText(account);
        if (type.equals("1")){
            tvType.setText("公司");
        }else if(type.equals("2")){
            tvType.setText("个人");
        }else if(type.equals("3")){
            tvType.setText("店铺");
        }
    }

    private void initTitle() {
//        titleValue.setText(R.string.perional_data);
        titleValue.setText("我的名片");
    }



    public void getUserCard() {
//        String user_id = SPUtils.getString("user_id", "");
        try {
            model.getUserCard(user_id, new IModelResult<GetUserCardEntity.ListsBean>() {
                @Override
                public void OnSuccess(GetUserCardEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initUserCardInfo(entity);

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
    GetUserCardEntity.ListsBean initentity = null;
    private void initUserCardInfo(GetUserCardEntity.ListsBean entity) {
        this.initentity = entity;
        String real_name = entity.getReal_name();
        String company = entity.getCompany();
        String job = entity.getJob();
        String com_address = entity.getCom_address();
        String com_page = entity.getCom_page();
        String user_phone = entity.getUser_phone();
        String user_tel = entity.getUser_tel();

        String user_fax = entity.getUser_fax();
        Log.e("aaa",user_fax+",获得的电话："+user_tel);
        String user_email = entity.getUser_email();
        String user_qq = entity.getUser_qq();
        String wechat = entity.getWechat();
        String post_address = entity.getPost_address();
        String com_intro = entity.getCom_intro();
        tvReal_name.setText(real_name);
        tvUser_com.setText(company);
        tvUser_job.setText(job);
        tvCom_address.setText(com_address);
        tvCom_page.setText(com_page);
        tvUser_phone.setText(user_phone);
        tvUser_tel.setText(user_tel);
        tvUser_fax.setText(user_fax);
        tvUser_email.setText(user_email);
        tvUser_qq.setText(user_qq);
        tvWechat.setText(wechat);
        tvPost_address.setText(post_address);
        tvCom_intro.setText(com_intro);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);

            GetUserCardEntity.ListsBean Centity = (GetUserCardEntity.ListsBean) data.getSerializableExtra("userCardInfo");
            if (CommonUtils.isNull(Centity)) {
                return;
            }
            initUserCardInfo(Centity);

        }


        super.onActivityResult(requestCode, resultCode, data);

    }


    @OnClick({R.id.btn_add,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_add:
                Intent intent = new Intent(mActivity, AddUsercardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if(initentity != null){
                    intent.putExtra("item",initentity);
                }

                startActivity(intent);
                finish();
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }



}
