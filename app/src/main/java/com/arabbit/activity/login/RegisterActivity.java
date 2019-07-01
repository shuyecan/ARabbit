package com.arabbit.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.entity.RegisterFromPhoneEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.request.UploadPicEntity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DialogHelper;
import com.arabbit.utils.FileUtils;
import com.arabbit.utils.HttpClientUtil;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.MD5;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;
import com.arabbit.view.dialog.address.AddressDialog;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Subscription;

import static com.arabbit.activity.persional.StudentInfoActivity.UPDATE_HEAD_IMAGE;
import static com.arabbit.activity.persional.StudentInfoActivity.UPDATE_TYPE;


/**
 * 注册（改版）
 */
public class RegisterActivity extends BaseActivity {


    @InjectView(R.id.title_value)
    TextView mTitleValue;


    SocialModel model;
    @InjectView(R.id.et_account)
    EditText etAccount;
    @InjectView(R.id.et_pwd)
    EditText etPwd;
    @InjectView(R.id.et_confirm_pwd)
    EditText etConfirmPwd;
    @InjectView(R.id.et_nickname)
    EditText etNickname;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_hint)
    TextView tvHint;
    @InjectView(R.id.iv_head_image)
    ImageView ivHeadImage;
    @InjectView(R.id.tv_region)
    TextView tvRegion;
//    @InjectView(R.id.tv_school)
//    TextView tvSchool;

    private AddressDialog addressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        model = new SocialModel(RegisterActivity.this);
        myHandler = new MyHandler(this);
        mTitleValue.setText(R.string.register_new);
        initAddress();
    }

    String region = "";
    String school_id = "";

    private void initAddress() {
        addressDialog = new AddressDialog(this);
        addressDialog.setOnClick(new AddressDialog.OnClick() {
            @Override
            public void onConfirm(String province, String city, String area, String address) {
                region = province + city;
                tvRegion.setText(region);
            }
        });


        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (CommonUtils.isNull(s.toString())) {
                    tvHint.setVisibility(View.VISIBLE);
                } else {
                    tvHint.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.iv_back,
            R.id.tv_type,
            R.id.iv_head_image,
            R.id.tv_region,
//            R.id.tv_school,
            R.id.btn_register})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_region://地区
                addressDialog.open();
//                intent.setClass(mActivity, AreaActivity.class);
//                intent.putExtra("area", area);
//                startActivityForResult(intent, UPDATE_AREA);
                break;
            case R.id.tv_type:
                String type = UIUtils.getStrTextView(tvType);
//               String roles = getIntent().getStringExtra("roles");
                intent.setClass(mActivity, TypeActivity.class);
                intent.putExtra("type", type);
//                intent.putExtra("roles", roles);
                startActivityForResult(intent, UPDATE_TYPE);
 //               System.out.println("roles:"+roles);
                break;
        //        break;
//            case R.id.tv_school:
//                String school = UIUtils.getStrTextView(tvSchool);
//                intent.setClass(mActivity, SchoolActivity.class);
//                intent.putExtra("school", school);
//                intent.putExtra("isRegister", true);
//                startActivityForResult(intent, UPDATE_SCHOOL);
//                break;
            case R.id.btn_register:
                String account = UIUtils.getStrEditView(etAccount);
                if (CommonUtils.isNull(account)) {
                    ToastUtils.showToastShort(R.string.account_empty);
                    return;
                }
                if (account.length() < 4) {
                    ToastUtils.showToastShort(R.string.account_length);
                    return;
                }
                String password = UIUtils.getStrEditView(etPwd);
                if (CommonUtils.isNull(password)) {
                    ToastUtils.showToastShort(R.string.pwd_empty);
                    return;
                }
                if (password.length() < 6) {
                    ToastUtils.showToastShort(R.string.pwd_length);
                    return;
                }
                String passConfirm = UIUtils.getStrEditView(etConfirmPwd);
                if (!password.equals(passConfirm)) {
                    ToastUtils.showToastShort(R.string.pwd_inconsistent);
                    return;
                }
                String nickName = UIUtils.getStrTextView(etNickname);
                if (CommonUtils.isNull(nickName)) {
                    ToastUtils.showToastShort(R.string.nickname_empty);
                    return;
                }

                String roletype = UIUtils.getStrTextView(tvType);
                if (CommonUtils.isNull(roletype)) {
                    ToastUtils.showToastShort(R.string.type_empty);
                    return;
                }
                String role = "";
                if(roletype.equals(getString(R.string.student))){
                    role = "1";
                }else if (roletype.equals(getString(R.string.average_user))){
                    role = "2";
                }
                else if (roletype.equals(getString(R.string.shop))){
                    role = "3";
                }

                //地区
                if (CommonUtils.isNull(region)) {
                    ToastUtils.showToastShort(R.string.region_empty);
                    return;
                }
//
//                if (CommonUtils.isNull(school_id)) {
//                    ToastUtils.showToastShort(R.string.please_choose_school);
//                    return;
//                }

                password = MD5.getMd5(password);


                if (CommonUtils.isNull(avatar_img) || isUploadSuccess) {
                    register(account, password, nickName, role, avatar_img,
                            region, school_id);
                } else {
                    uploadImage(avatar_img);
                }


                break;

            case R.id.iv_head_image:
                intent.setClass(mActivity, PersonalAvatarActivity.class);
                intent.putExtra("url", avatar_img);
                intent.putExtra("type", "register");
                startActivityForResult(intent, UPDATE_HEAD_IMAGE);
                break;
        }
    }

    boolean isUploadSuccess = false;

    public void goToRegister() {
        String account = UIUtils.getStrEditView(etAccount);
        if (CommonUtils.isNull(account)) {
            ToastUtils.showToastShort(R.string.account_empty);
            return;
        }

        if (account.length() < 4) {
            ToastUtils.showToastShort(R.string.account_length);
            return;
        }


        String password = UIUtils.getStrEditView(etPwd);
        if (CommonUtils.isNull(password)) {
            ToastUtils.showToastShort(R.string.pwd_empty);
            return;
        }

        if (password.length() < 6) {
            ToastUtils.showToastShort(R.string.pwd_length);
            return;
        }
        String passConfirm = UIUtils.getStrEditView(etConfirmPwd);
        if (!password.equals(passConfirm)) {
            ToastUtils.showToastShort(R.string.pwd_inconsistent);
            return;
        }
        String nickName = UIUtils.getStrTextView(etNickname);
        if (CommonUtils.isNull(nickName)) {
            ToastUtils.showToastShort(R.string.nickname_empty);
            return;
        }



        String roletype = UIUtils.getStrTextView(tvType);
//        String role = getIntent().getStringExtra("roles");
        if (CommonUtils.isNull(roletype)) {
            ToastUtils.showToastShort(R.string.type_empty);
            return;
        }
        String role = "";
        if(roletype.equals(getString(R.string.student))){
            role = "1";
        }else if (roletype.equals(getString(R.string.average_user))){
            role = "2";
        }
        else if (roletype.equals(getString(R.string.shop))){
            role = "3";
        }
        System.out.println("role:"+role);
        //地区
        if (CommonUtils.isNull(region)) {
            ToastUtils.showToastShort(R.string.region_empty);
            return;
        }
//
//                if (CommonUtils.isNull(school_id)) {
//                    ToastUtils.showToastShort(R.string.please_choose_school);
//                    return;
//                }

        password = MD5.getMd5(password);
        register(account, password, nickName, role, avatar_img,
                region, school_id);
    }

    String avatar_img = "";

    private void register(final String account, final String password, final String nickname, final String role,
                            final String avatar_img, String address, String school_id) {

        try {
            model.registerFromPhone(account, password, nickname, role, avatar_img, address, school_id, new IModelResult<RegisterFromPhoneEntity>() {
                @Override
                public void OnSuccess(RegisterFromPhoneEntity entity) {
                    if (!CommonUtils.isNull(entity)) {

                        SPUtils.setString("user_id", "" + entity.getUser_id());
                        SPUtils.setString("token", "" + entity.getToken());
                        SPUtils.setString("avatar_img", avatar_img);
                        SPUtils.setString("account", account);
                        SPUtils.setString("nickname", nickname);
                        SPUtils.setString("pass", password);
                        SPUtils.setString("role",role);
                        //改变登录状态
                        SPUtils.setString("isLogin", "1");//已经登录，下次自动登录  退出登录的时候改变为2
                        SPUtils.setInt("type", 1);//正常登陆
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String value = data.getStringExtra("value");
            switch (requestCode) {
                case UPDATE_HEAD_IMAGE:
                    avatar_img = value;
                    ImgLoaderUtils.setImageloader("file://" + value, ivHeadImage);
                    break;
                case UPDATE_TYPE:
                    tvType.setText(value);
                    school_id = data.getStringExtra("school_id");
                    break;
//                case UPDATE_SCHOOL:
//                    tvSchool.setText(value);
//                    break;
            }
        }
    }

    DialogHelper dialogHelper;
    public UploadPicEntity result = null;

    public void uploadImage(final String urlImage) {
        try {
            if (dialogHelper != null) {
                dialogHelper.dismissProgressDialog(mActivity);
                dialogHelper = null;
            }
            dialogHelper = new DialogHelper();
            dialogHelper.showProgressDialog(mActivity, R.string.upload_pictures, false);
            HttpClientUtil.upload(new File(urlImage), "avatar_img", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    myHandler.sendEmptyMessage(2);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    result = new Gson().fromJson(response.body().string(), UploadPicEntity.class);
                    FileUtils.deleteFile(urlImage);
                    myHandler.sendEmptyMessage(1);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastShort(R.string.clipping_error);
        }
    }

    MyHandler myHandler;

    static class MyHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyHandler(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RegisterActivity avatarActivity = (RegisterActivity) activityReference.get();
            avatarActivity.dialogHelper.dismissProgressDialog(avatarActivity);
            switch (msg.what) {
                case 1:
                    String code = avatarActivity.result.getCode();
                    if (code.equals("1")) {
                        avatarActivity.avatar_img = avatarActivity.result.getData().getUrl();
                        avatarActivity.isUploadSuccess = true;
                        avatarActivity.goToRegister();
                    } else {
                        ToastUtils.showToastShort(R.string.upload_failed);
                    }
                    break;
                case 2:
                    ToastUtils.showToastShort(R.string.upload_failed);
                    break;
            }
        }

    }


}
