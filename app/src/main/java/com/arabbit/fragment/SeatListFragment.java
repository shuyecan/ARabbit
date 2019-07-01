package com.arabbit.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.SeatDeatilActivity;
import com.arabbit.adapter.SeatAdapter;
import com.arabbit.bean.GetTermListBean;
import com.arabbit.entity.GetTermListEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

import static com.arabbit.activity.SeatDeatilActivity.selectTermId;


/**
 * 获取学期列表
 */
public class SeatListFragment extends BaseFragment {
    @InjectView(R.id.lv_seat_info)
    ListView lvSeatInfo;
    @InjectView(R.id.ed_num)
    EditText edNum;
    @InjectView(R.id.tv_count)
    TextView tvCount;

    SeatDeatilActivity seatDeatilActivity;
    String school_id;
    String school = "";
    SocialModel model;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seat_list, container, false);
        ButterKnife.inject(this, view);
        seatDeatilActivity = (SeatDeatilActivity) mActivity;
        Bundle bundle = getArguments();
        school_id = bundle.getString("school_id");
        school = bundle.getString("school");
        model = new SocialModel(this);
        seatDeatilActivity.titleValue.setText(school + getString(R.string.exhibition_list));
        initEdit();
        initEmptyList();
        getTermList(page);

        return view;
    }

    private void initEdit() {
        edNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String value = UIUtils.getStrEditView(edNum);
                if (!CommonUtils.isNull(value)) {
                    int newPage = Integer.parseInt(value);
                    if (newPage > accountPage) {
                        ToastUtils.showToastShort(R.string.search_page_too_big);
                        return false;
                    }
                    if (newPage < 1) {
                        ToastUtils.showToastShort(R.string.search_page_musu_be_1);
                        return false;
                    }
                    getTermList(newPage);
                }
                return false;
            }
        });
    }

    int page = 1;
    int accountPage = 1;
    List<GetTermListEntity.ListsBean> mList;

    private void getTermList(final int currentPage) {
        this.page = currentPage;
        try {
            model.getTermList(school_id, currentPage + "", new IModelResult<GetTermListEntity>() {
                @Override
                public void OnSuccess(GetTermListEntity getTermListEntity) {
                    if (!CommonUtils.isNull(getTermListEntity)) {
                        list.clear();
                        GetTermListBean bean;
                        mList = getTermListEntity.getLists();

                        if (!CommonUtils.isNull(mList)) {
                            boolean isSetSelect = false;
                            for (int i = 0; i < mList.size(); i++) {
                                GetTermListEntity.ListsBean entity = mList.get(i);
                                bean = new GetTermListBean();
                                String term_name = entity.getTerm_name();
                                int id = entity.getTerm_id();
                                int createTime = entity.getCreate_time();
                                int starttime = entity.getStarttime();
                                int isMain = entity.getIs_main();
                                bean.setTerm_name(term_name);
                                bean.setTerm_id(id);
                                bean.setIs_main(isMain);
                                bean.setStarttime(starttime);
                                if (selectTermId == id) {
                                    seatDeatilActivity.itemXuanQiSelected = i;
                                    isSetSelect = true;
                                    bean.setSelect(1);
                                } else {
                                    bean.setSelect(0);
                                }
                                list.add(bean);
                            }
                            if (isSetSelect) {
                                seatDeatilActivity.termList = mList;
                            }
                            accountPage = getTermListEntity.getTotal_page();
                        }
                        tvCount.setText("/" + accountPage);
                        edNum.setText(currentPage + "");
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

    SeatAdapter mAdapter;
    List<GetTermListBean> list = new ArrayList();

    private void initEmptyList() {
        mAdapter = new SeatAdapter(getActivity(), list);
        lvSeatInfo.setAdapter(mAdapter);
        lvSeatInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!CommonUtils.isNull(mList)) {
                    seatDeatilActivity.termList = mList;
                }
                seatDeatilActivity.itemXuanQiSelected = position;

                int size = list.size();
                int term_id = 0;
                String name = "";
                for (int i = 0; i < size; i++) {
                    GetTermListBean bean = list.get(i);
                    if (i == position) {
                        term_id = bean.getTerm_id();
                        name = bean.getTerm_name();
                        bean.setSelect(1);
                    } else {
                        bean.setSelect(0);
                    }

                }
                SeatDeatilActivity.termId = term_id;
                SeatDeatilActivity.selectTermId = term_id;
                seatDeatilActivity.changeFragment(0, school_id, "");//itemHomeSelected
//                seatDeatilActivity.changeFragment(4, "", term_id + "");
                seatDeatilActivity.updateContent(name);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.tv_prevs, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_prevs:
                if (page == 1) {
                    ToastUtils.showToastShort(R.string.first_page);
                    return;
                }
                page--;
                getTermList(page);
                break;
            case R.id.tv_next:
                if (page == accountPage) {
                    ToastUtils.showToastShort(R.string.last_page);
                    return;
                }

                page++;
                getTermList(page);
                break;
        }
    }
}