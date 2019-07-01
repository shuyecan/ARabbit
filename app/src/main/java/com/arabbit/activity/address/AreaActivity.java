package com.arabbit.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.adapter.AreaAdapter;
import com.arabbit.entity.GetCityListEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

public class AreaActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_country)
    TextView tvCountry;
    @InjectView(R.id.tv_province)
    TextView tvProvince;
    @InjectView(R.id.tv_city)
    TextView tvCity;
    @InjectView(R.id.lv_all)
    ListView lvAll;

    AreaAdapter mAdapter;
    SocialModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        ButterKnife.inject(this);
        titleValue.setText(R.string.region);
        initEmptyList();
        model = new SocialModel(this);
        getCityList();
    }

    private void getCityList() {
        try {
            model.getCityList(new IModelResult<List<GetCityListEntity>>() {
                @Override
                public void OnSuccess(List<GetCityListEntity> getCityListEntity) {
                    if (!CommonUtils.isNull(getCityListEntity)) {
                        list = getCityListEntity;
                        initEmptyList();
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

    List<GetCityListEntity> list = new ArrayList<>();

    private void initEmptyList() {
        mAdapter = new AreaAdapter(mActivity, list);
        lvAll.setAdapter(mAdapter);
        lvAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("value", list.get(position).getCity());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
