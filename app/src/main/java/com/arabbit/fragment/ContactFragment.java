package com.arabbit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arabbit.R;
import com.arabbit.activity.Userpage.SearchUserActivity;
import com.arabbit.activity.shop.ShopContactActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/5/12.
 */

public class ContactFragment extends BaseFragment {

    @InjectView(R.id.mfri)
    ImageView mfrip;
    @InjectView(R.id.mcom)
    ImageView mcomp;
    @InjectView(R.id.mshop)
    ImageView mshop;
    @InjectView(R.id.viewpager_contcat)
    ViewPager viewpagerContcat;
    private FragmentPagerAdapter adapter;
    List<Fragment> fragments = new ArrayList<>();
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.inject(this, view);
        showMyView();
        init();
        return view;
    }

    private void init() {
        fragments.add(new FriendFragment());
        fragments.add(new CompanyFragment());
        fragments.add(new ShopContactFragment());
        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewpagerContcat.setAdapter(adapter);
        viewpagerContcat.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
               switch (i){
                   case 0:
                       showMyView();
                       break;

                   case 1:
                       showMarkView();
                       break;

                   case 2:
                       showshopView();
                       break;
               }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @OnClick({R.id.mfri, R.id.mcom, R.id.mshop, R.id.search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mfri:
                showMyView();
                viewpagerContcat.setCurrentItem(0,false);
                break;
            case R.id.mcom:
                showMarkView();
                viewpagerContcat.setCurrentItem(1,false);
                break;
            case R.id.mshop:
                showshopView();
                viewpagerContcat.setCurrentItem(2,false);
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
    private void showMyView() {
        mfrip.setImageResource(R.mipmap.mfriend);
        mcomp.setImageResource(R.mipmap.mcom_d);
        mshop.setImageResource(R.mipmap.mshop_d);
    }

    /**
     * 显示公司列表页面
     */
    private void showMarkView() {
        mcomp.setImageResource(R.mipmap.mcom);
        mfrip.setImageResource(R.mipmap.mfriend_d);
        mshop.setImageResource(R.mipmap.mshop_d);
    }

    /**
     * 显示店铺列表页面
     */
    private void showshopView() {
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
