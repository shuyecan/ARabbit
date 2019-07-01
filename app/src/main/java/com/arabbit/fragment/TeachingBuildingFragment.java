package com.arabbit.fragment;

import android.os.Bundle;
import android.util.Log;
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
public class TeachingBuildingFragment extends BaseFragment {

    @InjectView(R.id.gv_teacher)
    GridView gvTeacher;

    String school_id = "";
    String school = "";
    String b_id;
    String termId = "";
    SeatDeatilActivity seatDeatilActivity;

    SocialModel model;

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
        if (!CommonUtils.isNull(school)) {
            seatDeatilActivity.titleValue.setText(school);
        }
        termId = (seatDeatilActivity.termId) + "";
        model = new SocialModel(this);
        Log.e("aaa","TeachingBuildingFragment:"+termId);
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
//                        if(getBuildingListEntity != null && getBuildingListEntity.size()>0){
//                            for(int i=0;i<getBuildingListEntity.size();i++){
//                                GetBuildingListEntity getBuildingListEntity1 = getBuildingListEntity.get(i);
//
//                            }
//
//                        }

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
                String buildName = CommonUtils.formatNull(mList.get(position).getBuilding_name());
                Log.e("aaa","TeachingBuildingFragment" +
                        "是否还有下级:"+isSeat);
                if (isSeat == 1) {
                    seatDeatilActivity.changeFragment(4, school + buildName, b_id);
                } else {
                    seatDeatilActivity.changeFragment(1, school + "," + buildName, b_id);
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