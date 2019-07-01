package com.arabbit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.arabbit.R;
import com.arabbit.activity.SeatDeatilActivity;
import com.arabbit.adapter.TeachingBuildingAdapter;
import com.arabbit.entity.GetBuildingListEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;


/**
 * 教学楼
 * 获取教学楼列表
 */
public class MoreBuildingFragment extends BaseFragment {

    @InjectView(R.id.gv_teacher)
    GridView gvTeacher;
    String school_id;
    String b_id;
    SeatDeatilActivity seatDeatilActivity;
    String school = "";
    SocialModel model;
    String build_name = "";

    String termId = "";

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teaching_building, container, false);
        ButterKnife.inject(this, view);
        seatDeatilActivity = (SeatDeatilActivity) mActivity;
        Bundle bundle = getArguments();
        school_id = bundle.getString("school_id");
        school = bundle.getString("school");
        b_id = bundle.getString("b_id");
        if (CommonUtils.isNull(b_id)) {
            b_id = "0";
        }
        termId = (seatDeatilActivity.termId) + "";
        model = new SocialModel(this);
        build_name = bundle.getString("build_name");


        if (!CommonUtils.isNull(build_name)) {
            String buildName = "";
            if (build_name.indexOf(",") > -1) {
                buildName = build_name.replaceAll(",", "");
            } else {
                buildName = build_name;
            }
            seatDeatilActivity.titleValue.setText(buildName);
        }
        initEmptyList();
        bid = seatDeatilActivity.getBid();
        getBuildingList(b_id);
        return view;
    }

    String bid = "";


    private void getBuildingList(String b_id) {
        try {
            model.getBuildingList(school_id, b_id, termId, new IModelResult<List<GetBuildingListEntity>>() {
                @Override
                public void OnSuccess(List<GetBuildingListEntity> getBuildingListEntity) {
                    if (!CommonUtils.isNull(getBuildingListEntity)) {
                        mList.clear();
                        mList.addAll(getBuildingListEntity);
                        mAdapter.notifyDataSetChanged();
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

    TeachingBuildingAdapter mAdapter;
    List<GetBuildingListEntity> mList = new ArrayList<>();

    private void initEmptyList() {
        mAdapter = new TeachingBuildingAdapter(getActivity(), mList);
        gvTeacher.setAdapter(mAdapter);
        gvTeacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int isSeat = mList.get(position).getIs_seat();
                String b_id = String.valueOf(mList.get(position).getB_id());
                String buildName = String.valueOf(mList.get(position).getBuilding_name());
                String[] str = build_name.split(",");
                if (str.length > 1) {
                    build_name = str[0] + "..." + buildName;
                } else {
                    build_name = str[0] + "," + buildName;
                }

//                seatDeatilActivity.addBid(1 + "");
                if (isSeat == 1) {
                    seatDeatilActivity.changeFragment(4, school + "..." + buildName, b_id);
                } else {
                    seatDeatilActivity.changeFragment(1, build_name, b_id);
                }

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}