package com.arabbit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.adapter.SortAdapter;
import com.arabbit.bean.SortModel;
import com.arabbit.entity.GetCityListEntity;
import com.arabbit.entity.GetSchoolListEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CharacterParser;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.PinyinComparator;
import com.arabbit.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * 获取城市列表
 * 根据城市获取学校列表
 */
public class StudentSeatInformationActivity extends BaseActivity {

    @InjectView(R.id.country_lvcountry)
    ListView countryLvcountry;
    @InjectView(R.id.title_value)
    TextView titleValue;
    private ListView sortListView;
    private SortAdapter mAdapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> sourceDateList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    SocialModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_seat_information);
        ButterKnife.inject(this);
        if (!CacheActivity.activityList.contains(StudentSeatInformationActivity.this)) {
            CacheActivity.addActivity(StudentSeatInformationActivity.this);
        }
        titleValue.setText(R.string.student_seat_information);
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
                        initViews();
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

    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        sortListView = (ListView) findViewById(R.id.country_lvcountry);

//        SourceDateList = filledData(getResources().getStringArray(date));

        sourceDateList = filledData(list);

        // 根据a-z进行排序源数据
        Collections.sort(sourceDateList, pinyinComparator);
        mAdapter = new SortAdapter(this, sourceDateList);
        sortListView.setAdapter(mAdapter);
        mAdapter.setOnGvItemClick(new SortAdapter.GvItemClick() {
            @Override
            public void onGvItemClick(View view, int position, Object data, String school) {
                Intent intent = new Intent(mActivity, SeatDeatilActivity.class);
                intent.putExtra("school_id", (String) data);
                intent.putExtra("school", school);
                startActivityForResult(intent, 789);
            }

            @Override
            public void onSubscriptOnClickmClick(View view, int position, String city) {
                int status = mAdapter.getmList().get(position).getStatus();
                if (status == 0) {
                    List<GetSchoolListEntity> entityList = mAdapter.getmList().get(position).getEntity();
                    if (CommonUtils.isNull(entityList)) {
                        getSchoolList(city, position);
                    } else {
                        mAdapter.updateItem(position, 1);
                    }
                } else {
                    mAdapter.updateItem(position, 0);
                }
            }
        });

    }


    public void getSchoolList(String city, final int position) {
        try {
            model.getSchoolList(city, new IModelResult<List<GetSchoolListEntity>>() {
                @Override
                public void OnSuccess(List<GetSchoolListEntity> getSchoolListEntity) {
                    List<GetSchoolListEntity> entityList;
                    if (!CommonUtils.isNull(getSchoolListEntity)) {
                        entityList = getSchoolListEntity;
                    } else {
                        entityList = new ArrayList<GetSchoolListEntity>();
                    }
                    mAdapter.getmList().get(position).setEntity(entityList);
                    mAdapter.updateItem(position, 1);
                    mAdapter.notifyDataSetChanged();
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


    /**
     * 为ListView填充数据(以List<String>)
     *
     * @return
     */
    private List<SortModel> filledData(List<GetCityListEntity> stringList) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < stringList.size(); i++) {
            GetCityListEntity entity = stringList.get(i);
            SortModel sortModel = new SortModel();
            sortModel.setName(entity.getCity());
            sortModel.setStatus(0);
            //汉字转换成拼音
//            String pinyin = characterParser.getSelling(stringList.get(i));
            String sortString = entity.getCity_initial();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
//            finish();
        }
    }
}
