package com.arabbit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.arabbit.R;
import com.arabbit.activity.SeatDeatilActivity;
import com.arabbit.adapter.FloorAdapter;
import com.arabbit.entity.GetFloorListEntity;
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
 * 获取子类
 */
public class FloorFragment extends BaseFragment {

    @InjectView(R.id.gv_floor)
    GridView gvFloor;

    SeatDeatilActivity seatDeatilActivity;
    SocialModel model;
    String school_id = "";
    String building = "";

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor, container, false);
        ButterKnife.inject(this, view);
        seatDeatilActivity = (SeatDeatilActivity) mActivity;
        Bundle bundle = getArguments();
        school_id = bundle.getString("school_id");
        building = bundle.getString("building");
        model = new SocialModel(this);
        initEmptyList();
        getFloorList();
        return view;
    }

    private void getFloorList() {
        try {

            model.getFloorList(school_id, building, new IModelResult<List<GetFloorListEntity>>() {
                @Override
                public void OnSuccess(List<GetFloorListEntity> getFloorListEntity) {
                    if (!CommonUtils.isNull(getFloorListEntity)) {
                        mList.clear();
                        mList.addAll(getFloorListEntity);
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

    FloorAdapter mAdapter;
    List<GetFloorListEntity> mList = new ArrayList();

    private void initEmptyList() {

        mAdapter = new FloorAdapter(getActivity(), mList);
        gvFloor.setAdapter(mAdapter);
        gvFloor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seatDeatilActivity.changeFragment(2, String.valueOf(mList.get(position).getFloor_id()),"");
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}