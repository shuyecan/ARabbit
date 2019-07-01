package com.arabbit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.arabbit.R;
import com.arabbit.activity.persional.StudentDataActivity;
import com.arabbit.adapter.SeatInfoAdapter;
import com.arabbit.entity.GetSeatListEntity;
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

import static android.app.Activity.RESULT_OK;


/**
 * 获取座位表
 */
public class SeatInfoFragment extends BaseFragment {

    @InjectView(R.id.gv_seat_info)
    GridView gvSeatInfo;

    SocialModel model;

    String class_id = "";
    String term_id = "";
    int seat_id = 0;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seat_info, container, false);
        ButterKnife.inject(this, view);
        Bundle bundle = getArguments();
        class_id = bundle.getString("class_id");
        term_id = bundle.getString("term_id");
        model = new SocialModel(this);
        initEmptyList();
        getSeatList();
        return view;
    }

    private void getSeatList() {
        try {
            model.getSeatList(class_id, term_id, new IModelResult<GetSeatListEntity>() {
                @Override
                public void OnSuccess(GetSeatListEntity getSeatListEntity) {
                    if (!CommonUtils.isNull(getSeatListEntity)) {
                        mList.clear();
                        mList.addAll(getSeatListEntity.getLists());
                        if (!CommonUtils.isNull(seat_id)) {
                            for (int i = 0; i < mList.size(); i++) {
                                GetSeatListEntity.ListsBean bean = mList.get(i);
                                int seatId = bean.getSeat_id();
                                if (seatId == seat_id) {
                                }

                            }
                        }

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

    SeatInfoAdapter mAdapter;
    List<GetSeatListEntity.ListsBean> mList = new ArrayList<>();

    private void initEmptyList() {


        mAdapter = new SeatInfoAdapter(getActivity(), mList);
        gvSeatInfo.setAdapter(mAdapter);
        gvSeatInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, StudentDataActivity.class);
                intent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
                startActivityForResult(intent, 456);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            seat_id = 0;
            class_id = data.getStringExtra("class_id");
            term_id = data.getStringExtra("term_id");
//            seat_id = data.getStringExtra("seat_id");
            getSeatList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}