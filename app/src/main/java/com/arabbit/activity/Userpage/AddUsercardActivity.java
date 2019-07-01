package com.arabbit.activity.Userpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.promotion.ProtioninfoActivity;
import com.arabbit.entity.GetUserCardEntity;
import com.arabbit.entity.PrizeListEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/7/29.
 */

public class AddUsercardActivity extends BaseActivity {


    @InjectView(R.id.real_name)
    EditText tvReal_name;
    @InjectView(R.id.user_com)
    EditText tvUser_com;
    @InjectView(R.id.user_job)
    EditText tvUser_job;
    @InjectView(R.id.com_address)
    EditText tvCom_address;
    @InjectView(R.id.com_page)
    EditText tvCom_page;
    @InjectView(R.id.user_phone)
    EditText tvUser_phone;
    @InjectView(R.id.user_tel)
    EditText tvUser_tel;
    @InjectView(R.id.user_fax)
    EditText tvUser_fax;
    @InjectView(R.id.user_email)
    EditText tvUser_email;
    @InjectView(R.id.user_qq)
    EditText tvUser_qq;
    @InjectView(R.id.wechat)
    EditText tvWechat;
    @InjectView(R.id.post_address)
    EditText tvPost_address;
    @InjectView(R.id.com_intro)
    EditText tvCom_intro;


    SocialModel model;
    String user_id = "";
    GetUserCardEntity.ListsBean initentity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addusercard);
        ButterKnife.inject(this);
        initentity = (GetUserCardEntity.ListsBean)getIntent().getSerializableExtra("item");
        user_id = SPUtils.getString("user_id", "");
        model = new SocialModel(AddUsercardActivity.this);
        if(initentity != null){
            initUserCardInfo(initentity);
        }

    }
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
//        Log.e("aaa",user_fax+",获得的电话："+user_tel);
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

    @OnClick({R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit:
                user_id = SPUtils.getString("user_id", "");
                String Real_name = UIUtils.getStrEditView(tvReal_name);
                if (CommonUtils.isNull(Real_name)) {
                    ToastUtils.showToastShort("姓名不能为空");
                    return;
                }
                String User_com = UIUtils.getStrEditView(tvUser_com);
                if (CommonUtils.isNull(User_com)) {
                    ToastUtils.showToastShort("公司名称不能为空");
                    return;
                }
                String User_job = UIUtils.getStrEditView(tvUser_job);
                if (CommonUtils.isNull(User_job)) {
                    ToastUtils.showToastShort("职务不能为空");
                    return;
                }
                String Com_address = UIUtils.getStrEditView(tvCom_address);
                if (CommonUtils.isNull(Com_address)) {
                    ToastUtils.showToastShort("公司地址不能为空");
                    return;
                }
                String Com_page = UIUtils.getStrEditView(tvCom_page);
                if (CommonUtils.isNull(Com_page)) {
                    ToastUtils.showToastShort("公司网址不能为空");
                    return;
                }
                String User_phone = UIUtils.getStrEditView(tvUser_phone);
                if (CommonUtils.isNull(User_phone)) {
                    ToastUtils.showToastShort("手机不能为空");
                    return;
                }
                String User_tel = UIUtils.getStrEditView(tvUser_tel);
                String User_fax = UIUtils.getStrEditView(tvUser_fax);
                String User_email = UIUtils.getStrEditView(tvUser_email);
                String User_qq = UIUtils.getStrEditView(tvUser_qq);
                String Wechat = UIUtils.getStrEditView(tvWechat);
                String Post_address = UIUtils.getStrEditView(tvPost_address);
                if (CommonUtils.isNull(Post_address)) {
                    ToastUtils.showToastShort("奖品寄送地址不能为空");
                    return;
                }
                String Com_intro = UIUtils.getStrEditView(tvCom_intro);


                addusercard(user_id, Real_name, User_com, User_job, Com_address, Com_page, User_phone, User_tel
                        , User_fax, User_email, User_qq, Wechat, Post_address, Com_intro);
                break;
        }
    }


    public void addusercard(final String user_id, final String real_name, final String company, final String job,final String com_address, final String com_page,
                            final String user_phone, final String user_tel, final String user_fax, final String user_email,
                            final String user_qq, final String wechat, final String post_address, final String com_intro) {
        try {
            if(initentity.getReal_name() == null){
                model.addUserCard(user_id, real_name, company, job,com_address, com_page, user_phone, user_tel, user_fax,
                        user_email, user_qq, wechat, post_address, com_intro,
                        new IModelResult<GetUserCardEntity.ListsBean>() {
                            @Override
                            public void OnSuccess(GetUserCardEntity.ListsBean entity) {
                                ToastUtils.showToastShort(R.string.add_success);
                                Intent intent = new Intent(mActivity, UserCardActivity.class);
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
            }else{
                model.updateUserCard(real_name, company, job, com_address,com_page, user_phone, user_tel, user_fax, user_email,
                        user_qq, wechat, post_address, com_intro,
                        new IModelResult<GetUserCardEntity.ListsBean>() {
                            @Override
                            public void OnSuccess(GetUserCardEntity.ListsBean entity) {
                                ToastUtils.showToastShort(R.string.update_success);
                                Intent intent = new Intent(mActivity, UserCardActivity.class);
                                setResult(RESULT_OK, intent);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void OnError(ApiException e) {
                                Intent intent = new Intent(mActivity, UserCardActivity.class);
                                setResult(RESULT_OK, intent);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
//                                Log.e("aaa","异常："+e.message);
//                                e.printStackTrace();
//                                ToastUtils.showToastShort(e.message);
                            }

                            @Override
                            public void AddSubscription(Subscription subscription) {
                                addSubscription(subscription);
                            }
                        });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
