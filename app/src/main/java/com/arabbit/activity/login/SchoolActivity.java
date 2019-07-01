package com.arabbit.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.adapter.SchoolListAdapter;
import com.arabbit.bean.GetSchoolListBean;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.entity.GetSchoolListEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ReflectGetValueUtils;
import com.arabbit.utils.ToastUtils;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

public class SchoolActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;


    @InjectView(R.id.lv_school)
    ListView lvSchool;
    @InjectView(R.id.pl_school)
    PullToRefreshLayout plSchool;

    String school = "";
    String school_id = "";

    boolean isRegister = false;

    SocialModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initTitle();

        school = CommonUtils.formatNull(getIntent().getStringExtra("school"));
        school_id = CommonUtils.formatNull(getIntent().getStringExtra("school_id"));
        isRegister = getIntent().getBooleanExtra("isRegister", false);
        initEmptyList();
        getSchoolList(1);
    }

    private void initTitle() {
        titleValue.setText(R.string.school);
        tvAdd.setText(R.string.save);
        tvAdd.setVisibility(View.VISIBLE);
        tvAdd.setTextColor(Color.YELLOW);
        tvAdd.setBackground(null);
    }

    void initEmptyList() {
        mAdapter = new SchoolListAdapter(this, schoolListContent);
        if (lvSchool != null) {
            lvSchool.setAdapter(mAdapter);
            lvSchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < schoolListContent.size(); i++) {
                        GetSchoolListBean bean = schoolListContent.get(i);
                        if (i == position) {
                            school = bean.getSchool_name();
                            school_id = bean.getSchool_id() + "";
                            bean.setStatus(1);
                        } else {
                            bean.setStatus(0);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        if (plSchool != null) {
            plSchool.setCanLoadMore(false);
            plSchool.setRefreshListener(new BaseRefreshListener() {
                @Override
                public void refresh() {
                    refreshNew();
                }

                @Override
                public void loadMore() {
                    getMore();
                }
            });
        }
    }

    SchoolListAdapter mAdapter;
    List<GetSchoolListEntity> schoolList;
    List<GetSchoolListBean> schoolListContent = new ArrayList<>();

    private void getSchoolList(int currentPage) {
//        plSchool.setCanLoadMore(true);
        try {
            model.getSchoolList("0", new IModelResult<List<GetSchoolListEntity>>() {
                @Override
                public void OnSuccess(List<GetSchoolListEntity> schoolListEntities) {
                    if (!CommonUtils.isNull(schoolListEntities)) {
                        schoolList = schoolListEntities;
                        if (schoolList.size() < 10) {
                            isNoMore = true;
                            plSchool.setCanLoadMore(false);
                        }
                    } else {
                        schoolList = null;
                        isNoMore = true;
                        plSchool.setCanLoadMore(false);
                    }
                    addDataList();
                    if (plSchool != null) {
                        plSchool.finishRefresh();
                        plSchool.finishLoadMore();
                    }

                }

                @Override
                public void OnError(ApiException e) {
                    ToastUtils.showToastShort(e.message);
                    if (plSchool != null) {
                        plSchool.finishRefresh();
                        plSchool.finishLoadMore();
                    }
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

    private void addDataList() {
        if (page == 1) {
            schoolListContent.clear();
        }
        if (!CommonUtils.isNull(schoolList)) {
            Map<Object, Object> map;
            for (GetSchoolListEntity entity : schoolList) {
                GetSchoolListBean beanDefault = new GetSchoolListBean();
                map = ReflectGetValueUtils.getFileValue(entity);
                ReflectGetValueUtils.setFieldValue(map, beanDefault);
                String name = entity.getSchool_name();
                if (name.equals(school)) {
                    beanDefault.setStatus(1);
                } else {
                    beanDefault.setStatus(0);
                }
                schoolListContent.add(beanDefault);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    int page = 1;

    private void getMore() {
        if (!isNoMore) {
            page++;
        }
        getSchoolList(page);
    }


    private void refreshNew() {
        page = 1;
        isNoMore = false;
        getSchoolList(page);
    }

    boolean isNoMore;


    @OnClick({R.id.iv_back, R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add:

                if (CommonUtils.isNull(school)) {
                    ToastUtils.showToastShort(R.string.please_choose_school);
                    return;
                }
                if (isRegister) {
                    reBackValue();
                    return;
                }
                updateUserInfoSchool();
                break;
        }
    }

    public void reBackValue() {
        Intent intent = new Intent();
        intent.putExtra("value", school);
        intent.putExtra("school_id", school_id);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void updateUserInfoSchool() {
        try {
            model.updateUserInfoSchool(school_id, new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity loginEntity) {
                    ToastUtils.showToastShort(R.string.update_success);
                    reBackValue();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
