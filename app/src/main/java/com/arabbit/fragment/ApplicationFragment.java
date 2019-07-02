package com.arabbit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.arabbit.R;
import com.arabbit.activity.StudentSeatInformationActivity;
import com.arabbit.activity.webview.BaseWebViewActivity;
import com.arabbit.adapter.SchoolAdapter;
import com.arabbit.entity.ApplistEntity;
import com.just.agentweb.AgentWeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 应用
 */
public class ApplicationFragment extends BaseFragment {

    @InjectView(R.id.gv_applcation)
    GridView gvApplcation;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applcation, container, false);
        ButterKnife.inject(this, view);
        initEmptyList();
        return view;
    }

    List list;

    SchoolAdapter mAdapter;

    private void initEmptyList() {
        list = new ArrayList();
        //通过实体类添加列表名字和图片
        ApplistEntity applist = new ApplistEntity();
        applist.setAppName(getString(R.string.exhibition_system));
        applist.setUrlId(R.mipmap.btn_zuoweixitong);
        list.add(applist);

        ApplistEntity applist2 = new ApplistEntity();
        applist2.setAppName(getString(R.string.liuliu_shop));
        applist2.setUrlId(R.mipmap.ic_liuliushop);
        list.add(applist2);

        ApplistEntity applist3 = new ApplistEntity();
        applist3.setAppName(getString(R.string.garment_mall));
        applist3.setUrlId(R.mipmap.ic_garment_mall);
        list.add(applist3);

        ApplistEntity applist4 = new ApplistEntity();
        applist4.setAppName(getString(R.string.youku));
        applist4.setUrlId(R.mipmap.ic_youku);
        list.add(applist4);

        mAdapter = new SchoolAdapter(getActivity(), list);
        gvApplcation.setAdapter(mAdapter);
        gvApplcation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
              switch (position){
                  case 0:
                      startActivity(new Intent(mActivity, StudentSeatInformationActivity.class));
                      break;
                  case 1:
                      intent = new Intent(mActivity,BaseWebViewActivity.class);
                      intent.putExtra("title_name",getString(R.string.liuliu_shop));
                      intent.putExtra("url","http://www.liuliut.com/");
                      startActivity(intent);
                      break;

                  case 2:
                      intent = new Intent(mActivity,BaseWebViewActivity.class);
                      intent.putExtra("title_name",getString(R.string.garment_mall));
                      intent.putExtra("url","http://www.liuliut.com/");
                      startActivity(intent);
                      break;

                  case 3:
                      intent = new Intent(mActivity,BaseWebViewActivity.class);
                      intent.putExtra("title_name",getString(R.string.youku));
                      intent.putExtra("url","http://www.youku.com/");
                      startActivity(intent);
                      break;

              }
//                startActivity(new Intent(mActivity, SeatDeatilActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}