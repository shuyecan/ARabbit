package com.arabbit.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arabbit.R;
import com.arabbit.activity.address.AddressActivity;
import com.arabbit.activity.persional.PhoneActivity;
import com.arabbit.activity.shop.ShopalbumActivity;
import com.arabbit.activity.shop.ShopcpActivity;
import com.arabbit.activity.shop.ShopdetailsActivity;
import com.arabbit.activity.shop.ShopmbActivity;
import com.arabbit.activity.shop.ShopptActivity;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.yanzhenjie.permission.AndPermission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

import static android.app.Activity.RESULT_OK;
import static com.arabbit.activity.shop.ShopdetailsActivity.UPDATE_ADDRESS;
import static com.arabbit.activity.shop.ShopdetailsActivity.UPDATE_PHONE;

/**
 * Created by Administrator on 2018/10/22.
 */

public class ShopDataFragment extends BaseFragment {

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
    @InjectView(R.id.shop_address)
    LinearLayout layoutAddress;
    @InjectView(R.id.opening)
    LinearLayout layoutOpening;
    @InjectView(R.id.line_phone)
    LinearLayout layoutPhone;
    @InjectView(R.id.layout_album)
    LinearLayout layout_album;
    @InjectView(R.id.tv_stat)
    TextView tv_stat;
    @InjectView(R.id.tv_opening)
    TextView tv_opening;
    SocialModel model;
    @InjectView(R.id.tv_shop_address)
    TextView tvShopAddress;
    @InjectView(R.id.tv_phone)
    TextView tv_phone;
    String phone,address_detail,address;
    GetUserInfoEntity mentity;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_data, container, false);
        ButterKnife.inject(this, view);
        model = new SocialModel(this);
        getUserInfo();
        return view;
    }

    public void getUserInfo() {
        String user_id = SPUtils.getString("user_id", "");
        try {
            model.getUserInfo(user_id, new IModelResult<GetUserInfoEntity>() {
                @Override
                public void OnSuccess(GetUserInfoEntity entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initUserInfo(entity);
                        mentity = entity;
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
         address = entity.getAddress();
        phone= entity.getPhone();
        address_detail = entity.getDetail_address();
        tvName.setText(name);
        if (!CommonUtils.isNull(url)) {
//            ImgLoaderUtils.setImageloader(Config.BASE_URL + url, ivImage);
            ImgLoaderUtils.setImageloader(Config.IMG_URL + url, ivImage);
        }
        tvAddress.setText(address);
        tvAccount.setText(account);
        tvShopAddress.setText(address_detail);
        tv_phone.setText(phone);
        if (type.equals("1")) {
            tvType.setText("公司");
        } else if (type.equals("2")) {
            tvType.setText("个人");
        } else if (type.equals("3")) {
            tvType.setText("店铺");
        }
    }

    @OnClick({R.id.iv_image, R.id.tv_more, R.id.miaob_sales, R.id.layout_prize, R.id.layout_coupon,R.id.tv_shop_address,R.id.tv_phone
    ,R.id.shop_stat
    ,R.id.opening,R.id.layout_album})
    public void onClick(View view) {
        String user_id = SPUtils.getString("user_id", "");
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_image:
                Intent infointent = new Intent(mActivity, ShopdetailsActivity.class);
                infointent.putExtra("user_id", user_id);
                startActivityForResult(infointent, 701);
                break;
            case R.id.tv_more:
                intent = new Intent(mActivity, ShopdetailsActivity.class);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent, 702);
                break;
            case R.id.miaob_sales:
                Intent mintent = new Intent(mActivity, ShopmbActivity.class);
                startActivity(mintent);
                break;
            case R.id.layout_prize:
                Intent pintent = new Intent(mActivity, ShopptActivity.class);
                startActivity(pintent);
                break;
            case R.id.layout_coupon:
                Intent cintent = new Intent(mActivity, ShopcpActivity.class);
                startActivity(cintent);
                break;
            case R.id.tv_shop_address:
                intent.setClass(mActivity, AddressActivity.class);
                intent.putExtra("address", address_detail);
//                intent.putExtra("postal_code", postal_code);
                intent.putExtra("area", address);
                startActivityForResult(intent, UPDATE_ADDRESS);
                break;

            case R.id.tv_phone:
                intent.setClass(mActivity, PhoneActivity.class);
                intent.putExtra("phone", phone);
                startActivityForResult(intent, UPDATE_PHONE);
                break;
            case R.id.shop_stat:
                List<String> statlist = new ArrayList<>();
                statlist.add("正常营业");
                statlist.add("暂停营业");
                new MaterialDialog.Builder(getActivity())
                        .title("请选择营业状态")
                        .positiveText("确认")
                        .items(statlist)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(text!=null&&!"".equals(text.toString())) {
                                    tv_stat.setText(text.toString());
                                }
                                return true;
                            }
                        })
                        .show();
                break;
            case R.id.opening:
                List<String> opentypelist = new ArrayList<>();
                opentypelist.add("周六，周日不休息");
                opentypelist.add("周六，周日休息");
                opentypelist.add("24小时营业，节假日不休");
                new MaterialDialog.Builder(getActivity())
                        .title("请选择营业时间")
                        .positiveText("确认")
                        .items(opentypelist)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which){
                                    case 0:
                                        setMorningtime(text.toString(),tv_opening);
                                        break;
                                    case 1:
                                        setMorningtime(text.toString(),tv_opening);
                                        break;

                                    case 2:
                                        tv_opening.setText(text.toString());
                                        break;
                                }
                                return true;
                            }
                        })
                        .show();
                break;
            case R.id.layout_album:
                intent.setClass(mActivity, ShopalbumActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setMorningtime(final String text, final TextView tv){
        DatePickDialog dialog = new DatePickDialog(getActivity());
        dialog.setYearLimt(5);
        dialog.setTitle("早上营业时间");
        dialog.setType(DateType.TYPE_HM);
        dialog.setMessageFormat("HH:mm");
        dialog.setOnChangeLisener(null);
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                long time = date.getTime();
                SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm");
                String format = simpledateformat.format(new Date(time));
               setNghttime(text,format,time,tv);
            }
        });
        dialog.show();
    }

    private void setNghttime(final String text, final String morningTime , final long morntime, final TextView tv){
        DatePickDialog dialog = new DatePickDialog(getActivity());
        dialog.setYearLimt(5);
        dialog.setTitle("晚上营业时间");
        dialog.setType(DateType.TYPE_HM);
        dialog.setMessageFormat("HH:mm");
        dialog.setOnChangeLisener(null);
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                long time = date.getTime();
                SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm");
                String format = simpledateformat.format(new Date(time));
                if(morntime>time){
                    Toast.makeText(mActivity, "请选择正确的区间", Toast.LENGTH_SHORT).show();
                    return;
                }
                tv.setText(morningTime+"-"+format+" "+text);
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String value = data.getStringExtra("value");
            switch (requestCode){
                case 701:
                    mentity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
                    if (CommonUtils.isNull(mentity)) {
                        return;
                    }
                    initUserInfo(mentity);
                    break;
                case 702:
                    mentity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
                    if (CommonUtils.isNull(mentity)) {
                        return;
                    }
                    initUserInfo(mentity);
                    break;
                case  UPDATE_PHONE:
                    phone = value;
                    tv_phone.setText(value);
                    mentity.setPhone(value);
                    break;
                case UPDATE_ADDRESS:
                    address_detail = value;
                    tvShopAddress.setText(value);
                    mentity.setDetail_address(value);
                    break;
            }


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
