package com.arabbit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arabbit.activity.Userpage.SearchUserActivity;
import android.widget.ImageView;
import com.arabbit.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/5/12.
 */

public class ContactFragment extends BaseFragment {

    @InjectView(R.id.us_photo)
    ImageView ivImage;
    @InjectView(R.id.mfri)
    ImageView mfrip;
    @InjectView(R.id.mcom)
    ImageView mcomp;
    @InjectView(R.id.mshop)
    ImageView mshop;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.inject(this, view);
        showMyView();
        return view;
    }
    @OnClick({R.id.mfri, R.id.mcom,R.id.mshop, R.id.us_photo, R.id.search })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mfri:
                showMyView();
                break;
            case R.id.mcom:
                showMarkView();
                break;
            case R.id.mshop:
                showshopView();
                break;
            case R.id.search:
                Intent intent = new Intent(mActivity, SearchUserActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 显示好友列表页面
     */
    private void showMyView(){

        mfrip.setImageResource(R.mipmap.mfriend);
        mcomp.setImageResource(R.mipmap.mcom_d);
        mshop.setImageResource(R.mipmap.mshop_d);
    }

    /**
     * 显示公司列表页面
     */
    private void showMarkView(){

        mcomp.setImageResource(R.mipmap.mcom);
        mfrip.setImageResource(R.mipmap.mfriend_d);
        mshop.setImageResource(R.mipmap.mshop_d);
    }

    /**
     * 显示店铺列表页面
     */
    private void showshopView(){
        mshop.setImageResource(R.mipmap.mshop);
        mfrip.setImageResource(R.mipmap.mfriend_d);
        mcomp.setImageResource(R.mipmap.mcom_d);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }


    @Override
    public void onResume() {
        super.onResume();
    }



}
