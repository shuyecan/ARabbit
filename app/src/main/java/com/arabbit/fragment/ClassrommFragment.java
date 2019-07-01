package com.arabbit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.arabbit.R;
import com.arabbit.activity.SeatDeatilActivity;
import com.arabbit.adapter.ClassAdapter;
import com.arabbit.entity.GetClassListEntity;
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
 * 获取课室列表
 */
public class ClassrommFragment extends BaseFragment {

    @InjectView(R.id.gv_classroom)
    GridView gvClassroom;

    SeatDeatilActivity seatDeatilActivity;

    SocialModel model;
    String floor_id = "";

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classroom, container, false);
        ButterKnife.inject(this, view);
        seatDeatilActivity = (SeatDeatilActivity) mActivity;
        Bundle bundle = getArguments();
        floor_id = bundle.getString("floor_id");
        model = new SocialModel(this);
        initEmptyList();
        getClassList();
        return view;
    }

    private void getClassList() {
        try {

            model.getClassList(floor_id, new IModelResult<List<GetClassListEntity>>() {
                @Override
                public void OnSuccess(List<GetClassListEntity> getClassListEntity) {
                    if (!CommonUtils.isNull(getClassListEntity)) {
                        mList.clear();
                        mList.addAll(getClassListEntity);
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

    ClassAdapter mAdapter;
    List<GetClassListEntity> mList = new ArrayList();

    private void initEmptyList() {

        mAdapter = new ClassAdapter(getActivity(), mList);
        gvClassroom.setAdapter(mAdapter);
        gvClassroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SeatDeatilActivity.classId = mList.get(position).getClass_id();
//                seatDeatilActivity.changeFragment(4, SeatDeatilActivity.classId + "", SeatDeatilActivity.termId + "");
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}