package com.arabbit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arabbit.R;

import butterknife.ButterKnife;


/**
 * 社区
 */
public class CommunityFragment extends BaseFragment {

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coumity, container, false);
        ButterKnife.inject(this, view);

        return view;
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