package com.arabbit.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GetTermListDetailByIdEntity;
import com.arabbit.entity.GetTermListEntity;
import com.arabbit.fragment.MoreBuildingFragment;
import com.arabbit.fragment.SeatInfoCustomFragment;
import com.arabbit.fragment.SeatListFragment;
import com.arabbit.fragment.TeachingBuildingFragment;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;


/**
 * 座位系统
 */
public class SeatDeatilActivity extends BaseActivity {

    @InjectView(R.id.title_value)
    public TextView titleValue;
    @InjectView(R.id.tv_add)
    ImageView tvAdd;
    @InjectView(R.id.fl_content)
    FrameLayout flContent;
    @InjectView(R.id.tv_content)
    TextView tvContent;

    /**
     * 当前学期ID
     */
    public static int termId = -1;
    /**
     * 当前选择的学期ID
     */
    public static int selectTermId = -1;
    /**
     * 后台设置的主页学期ID
     */
    public static int mainTermId = -1;

    // Fragment管理对象
    private FragmentManager fManager;
    private FragmentTransaction ftranscation;

    String school_id = "";
    String seat_id = "";
    String school = "";

    /**
     * 是否加载的是历史座位表
     */
    boolean isLoadHistory = false;
    /**
     * 加载历史座位表是否成功
     */
    boolean isLoadHistoryTermSuccess = false;

    SocialModel model;
    /**
     * 学期列表中对应的索引
     */
    public int itemXuanQiSelected = -1;

    int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_deatil);
        ButterKnife.inject(this);
        if (!CacheActivity.activityList.contains(SeatDeatilActivity.this)) {
            CacheActivity.addActivity(SeatDeatilActivity.this);
        }
        model = new SocialModel(this);
        myHandler = new MyHandler(this);
        fManager = getSupportFragmentManager();
        school_id = getIntent().getStringExtra("school_id");
        school = getIntent().getStringExtra("school");
        isLoadHistory = getIntent().getBooleanExtra("isLoadHistory", false);
        seat_id = getIntent().getStringExtra("seat_id");
        termId = getIntent().getIntExtra("term_id", 0);
        initTitle();
        if (isLoadHistory) {
            if (!isLoadHistoryTermSuccess) {
                selectTermId = termId;
                getTermListDetailById(termId);
            }
        } else {
            termId = -1;
            getTermList();
        }

    }

    private void getTermListDetailById(int termId) {
        try {
            model.getTermListDetailById(termId + "", new IModelResult<GetTermListDetailByIdEntity>() {
                        @Override
                        public void OnSuccess(GetTermListDetailByIdEntity getIdEntity) {
                            if (!CommonUtils.isNull(getIdEntity)) {
                                termName = getIdEntity.getTerm_name();
                                if (CommonUtils.isNull(termName)) {
                                    termName = getString(R.string.no_term_list);
                                    ToastUtils.showToastShort(termName);
                                } else {
                                    updateContent(termName);
                                }
                                isLoadHistoryTermSuccess = true;
                                getTermList();
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
                    }

            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<GetTermListEntity.ListsBean> termList;

    GetTermListEntity.MainTermBean mainTerm;

    private void getTermList() {
        try {
            model.getTermList(school_id, "1", new IModelResult<GetTermListEntity>() {
                @Override
                public void OnSuccess(GetTermListEntity getTermListEntity) {
                    String name = "";//暂无学期列表
                    if (!CommonUtils.isNull(getTermListEntity)) {
                        termList = getTermListEntity.getLists();
                        mainTerm = getTermListEntity.getMain_term();
                        if (!CommonUtils.isNull(mainTerm)) {
                            mainTermId = mainTerm.getId();
                            name = mainTerm.getName();
                        }
                        if (!CommonUtils.isNull(termList)) {
                            if (mainTermId < 0) {
                                mainTermId = termList.get(0).getTerm_id();
                                name = termList.get(0).getTerm_name();
                            }
                            if (termId < 0) {
                                termId = mainTermId;
                                selectTermId = termId;
                                termName = name;
                            }

                        }

                    } else {
                        termList = new ArrayList<GetTermListEntity.ListsBean>();
                    }
                    if (!isLoadHistoryTermSuccess) {
                        if (CommonUtils.isNull(name)) {
                            name = getString(R.string.no_term_list);
                            ToastUtils.showToastShort(name);
                        } else {
                            updateContent(name);
                        }
                    }
                    Log.e("aaa","加载fragment的标志："+isLoadHistory);
                    if (isLoadHistory) {
                        changeFragment(4, school, seat_id);
                    } else {
                        changeFragment(0, "", "");
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

    public void updateContent(String obj) {
        Message msg = Message.obtain();
        msg.what = 1;
        msg.obj = obj;
        myHandler.sendMessage(msg);
    }


    private void initTitle() {
        titleValue.setText(school);
        tvAdd.setVisibility(View.VISIBLE);
//        tvAdd.setTextSize(16);

    }


    TeachingBuildingFragment teachingBuildingFragment;


    public void changeFragment(int position, String value, String valueTwo) {
        if (position == 3) {
            isLoadHistory = false;
        }
        Fragment fragment = null;
        currentPosition = position;
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putString("school_id", school_id);
                bundle.putString("school", school);
                bundle.putString("b_id", valueTwo);
                teachingBuildingFragment = new TeachingBuildingFragment();
                fragment = teachingBuildingFragment;
                break;
            case 1:
                bundle.putString("school_id", school_id);
                bundle.putString("b_id", valueTwo);
                bundle.putString("school", school);
                bundle.putString("build_name", value);
                fragment = new MoreBuildingFragment();
                break;
//            case 2:
//                bundle.putString("floor_id", value);
//                fragment = new ClassrommFragment();
//                break;
            case 3:
                bundle.putString("class_id", value);
                bundle.putString("school_id", school_id);
                bundle.putString("school", school);
                fragment = new SeatListFragment();
                break;
            case 4:
                if (isLoadHistory) {
                    bundle.putString("seat_id", valueTwo);
                    bundle.putBoolean("isLoadHistory", isLoadHistory);
                } else {
                    bundle.putString("b_id", valueTwo);
                }
                bundle.putString("build_name", value);
                bundle.putString("school", school);
                fragment = new SeatInfoCustomFragment();
                break;
        }
        if (fragment == null) {
            return;
        }

        fragment.setArguments(bundle);

        ftranscation = fManager.beginTransaction();
        //当前的fragment会被myJDEditFragment替换
        ftranscation.replace(R.id.fl_content, fragment);
        ftranscation.addToBackStack(null);
        ftranscation.commit();
    }

    @Override
    public void onBackPressed() {
        if (isLoadHistory) {
            CacheActivity.finishActivity();
            return;
        }
        if (fManager.getBackStackEntryCount() == 1 || (teachingBuildingFragment != null && teachingBuildingFragment.isVisible())) {
            finish();
//            CacheActivity.finishActivity();
        } else {
            fManager.popBackStack();
        }
    }

    String termName = "";

    @OnClick({R.id.iv_back, R.id.tv_add, R.id.tv_main, R.id.tv_last, R.id.tv_content})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add:
                MainActivity.isGoMain = true;
                CacheActivity.finishActivity();
                break;
            case R.id.tv_main://
                //返回主页选中学期（主页图标勾中的那个）
                isLoadHistoryTermSuccess = false;
                if (!CommonUtils.isNull(termList)) {
                    termId = mainTermId;
                    selectTermId = termId;
                    itemXuanQiSelected = -1;
                    String name = "";
                    if (CommonUtils.isNull(mainTerm)) {
                        name = termList.get(0).getTerm_name();
                    } else {
                        name = mainTerm.getName();
                    }
                    updateContent(name);
                    termName = name;
                    changeFragment(0, school_id, "");//itemHomeSelected
                    fManager.popBackStackImmediate(TeachingBuildingFragment.class.getName(), 0);
                    isLoadHistory = false;
                }

                break;
            case R.id.tv_content:
                //返回当前选中学期
                if (!isLoadHistoryTermSuccess) {
                    if (itemXuanQiSelected > -1) {
                        if (!CommonUtils.isNull(termList)) {
                            termId = termList.get(itemXuanQiSelected).getTerm_id();
                            termName = termList.get(itemXuanQiSelected).getTerm_name();
                            updateContent(termName);
                            selectTermId = termId;
                        }
                    } else if (selectTermId > 0) {
                        termId = selectTermId;
                        updateContent(termName);

                    }
                }

                changeFragment(0, school_id, "");//itemXuanQiSelected
                fManager.popBackStackImmediate(TeachingBuildingFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                isLoadHistory = false;
                break;
            case R.id.tv_last:
                changeFragment(3, school_id, "");
                break;
        }
    }


    public MyHandler myHandler;

    static class MyHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyHandler(Activity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SeatDeatilActivity activity = (SeatDeatilActivity) activityReference.get();
            switch (msg.what) {
                case 1:
                    String value = (String) msg.obj;
                    activity.tvContent.setText(value);
                    break;
                case 2:
                    break;
            }
        }

    }


    List<String> bIdList = new ArrayList<>();

    public void addBid(String bid) {
        bIdList.add(bid);
    }

    public String getBid() {
        String bid = "0";
        if (!CommonUtils.isNull(bIdList)) {
            bIdList.get(bIdList.size() - 1);
            bIdList.remove(bIdList.size() - 1);
        }
        return bid;
    }

}
