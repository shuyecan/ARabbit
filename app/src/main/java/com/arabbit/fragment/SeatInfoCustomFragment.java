package com.arabbit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arabbit.R;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.SeatDeatilActivity;
import com.arabbit.activity.persional.StudentDataActivity;
import com.arabbit.entity.GetSeatListEntity;
import com.arabbit.entity.GetTermListDetailByIdEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.SeatTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;

import static android.app.Activity.RESULT_OK;


/**
 * 获取座位表(改版)
 */
public class SeatInfoCustomFragment extends BaseFragment {

    @InjectView(R.id.seatView)
    SeatTable mSeatView;

    SeatDeatilActivity seatDeatilActivity;

    SocialModel model;

    String b_id = "";
    boolean isLoadHistory = false;
    String seatId = "";
    String build_name = "";
    String school = "";
    private String company_name = "";
    private String seat_user_id = "";
    private int screenwidth = 0;
    private int screenheight = 0;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seat_info_custom, container, false);
        ButterKnife.inject(this, view);
        Bundle bundle = getArguments();
        seatDeatilActivity = (SeatDeatilActivity) mActivity;
        b_id = bundle.getString("b_id");

        isLoadHistory = bundle.getBoolean("isLoadHistory");
        seatId = bundle.getString("seat_id");
        build_name = bundle.getString("build_name");
        school = bundle.getString("school");
        model = new SocialModel(this);
        if (!CommonUtils.isNull(build_name)) {
            seatDeatilActivity.titleValue.setText(build_name);
        }
//        Log.e("aaa","SeatInfoCustomFragment:"+isLoadHistory+",b_id:"+b_id+",seatId:"+seatId);
//        Log.e("aaa","自定义展位界面:"+build_name+",school:"+school+",seatId:"+seatId);
        if(isLoadHistory){
            company_name = getActivity().getIntent().getStringExtra("name");
            seat_user_id = getActivity().getIntent().getStringExtra("seat_user_id");
            Log.e("aaa",company_name+",isLoadHistory编号sn:"+seat_user_id+",座位seatId："+seatId);
//            String no = bundle.getString("no");
//            Log.e("aaa","name:"+name+",no："+no);
        }
        if (isLoadHistory || CommonUtils.isNull(b_id)) {
            getSeatListById(seatId);
        } else {
            getSeatList();
        }
        Display display=getActivity().getWindowManager().getDefaultDisplay();
        screenwidth = display.getWidth();
        screenheight = display.getHeight();
        mSeatView.setScreenWH(screenwidth,screenheight);
        return view;
    }

    private void initSeat(final GetSeatListEntity.ListsBean[][] strings) {
        mSeatView.setMaxSelected(1);
        mSeatView.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
//                if(column==2) {
//                    return false;
//                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
//                if(row==6&&column==6){
//                    return true;
//                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

//                Intent intent = new Intent(mActivity, StudentDataActivity.class);
//                intent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
//                startActivityForResult(intent, 456);
//                ToastUtils.showToastShort("选中了第" + row + "行，第" + column + "排");

            }

            @Override
            public void checkedWithId(int row, int column, String user_id) {
                if (CommonUtils.isNull(user_id)) {
                    return;
                }
                Log.e("aaa","checkedWithId:"+row+",column:"+column);
                String currentUser_id = SPUtils.getString("user_id", "");
                int type = SPUtils.getInt("type", 1);
                if (currentUser_id.equals(user_id) && type == 1) {
                    Log.e("aaa","回到主页");
                    MainActivity.isGoPersionl = true;
//                    MainActivity.b_id = b_id;
                    CacheActivity.finishActivity();
                } else {
                    Log.e("aaa","回到StudentDataActivity");
                    GetSeatListEntity.ListsBean listsBean = strings[row][column];
                    String sn = listsBean.getSn();

//                    Log.e("aaa","checkedWithId:"+row+",跳转到公司信息:"+sn);
                    Intent intent = new Intent(mActivity, StudentDataActivity.class);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("b_id", b_id);
                    int seat_user_id1 = listsBean.getUser_id();
                    intent.putExtra("seat_user_id", seat_user_id1+"");

                    startActivityForResult(intent, 456);
                }


//                ToastUtils.showToastShort("选中了第" + row + "行，第" + column + "排" + ":user_id:" + user_id);
            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
//        mSeatView.setData(50, 50, strings);
        mSeatView.setData(30, 30, strings);
//        Log.e("aaa","查找到seat_user_id："+seat_user_id);
        if(!TextUtils.isEmpty(seat_user_id)){
            for (int i=0;i<strings.length;i++){
                GetSeatListEntity.ListsBean[] string = strings[i];
                for (int j=0;j<string.length;j++){
                    GetSeatListEntity.ListsBean listsBean = string[j];
                    int no = listsBean.getNo();
                    String nickname = listsBean.getNickname();
                    int seat_id = listsBean.getSeat_id();
                    String sn = listsBean.getSn();
                    String list_user_id = listsBean.getUser_id()+"";
//                    Log.e("aaa",listsBean.getNickname()+"展位sn："+sn);
                    if(TextUtils.equals(seat_user_id,list_user_id)){
//                        Log.e("aaa",sn+"查找到row号："+i+",column号："+j);
                        mSeatView.setSelectPos(i,j);
                        break;
                    }
//                    Log.e("aaa","列表：no"+no+",nickname："+nickname+",seat_id："+seat_id+",sn："+sn);
                }

            }
        }


    }

    GetSeatListEntity.ListsBean[][] entity = null;

    private void getSeatList() {
        try {

            model.getSeatList(SeatDeatilActivity.termId + "", b_id, new IModelResult<GetSeatListEntity>() {
                @Override
                public void OnSuccess(GetSeatListEntity getSeatListEntity) {
                    if (!CommonUtils.isNull(getSeatListEntity)) {
                        mList.clear();
                        mList.addAll(getSeatListEntity.getLists());
                        if (!CommonUtils.isNull(mList)) {
//                            entity = CommonUtils.changeListForEntity(mList, 50);
                            entity = CommonUtils.changeListForEntity(mList, 30);
                        }
                    }
                    initSeat(entity);

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


    private void getSeatListById(String seat_id) {
        try {
            Log.e("aaa","不是第一次请求seat_id："+seat_id);

            model.getSeatListById(seat_id, new IModelResult<GetSeatListEntity>() {
                @Override
                public void OnSuccess(GetSeatListEntity getSeatListEntity) {
                    if (!CommonUtils.isNull(getSeatListEntity)) {
                        mList.clear();
                        mList.addAll(getSeatListEntity.getLists());
//                        entity = CommonUtils.changeListForEntity(mList, 50);
                        entity = CommonUtils.changeListForEntity(mList, 30);
                        if (!CommonUtils.isNull(getSeatListEntity.getFull_name())) {
                            seatDeatilActivity.titleValue.setText(school + getSeatListEntity.getFull_name());
                        }
                    }
                    initSeat(entity);
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


    List<GetSeatListEntity.ListsBean> mList = new ArrayList<>();


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSeatView != null) {
            mSeatView.clear();
        }
        if (resultCode == RESULT_OK) {
            String seat_id = data.getStringExtra("seat_id");
            int term_id = data.getIntExtra("term_id", 0);
//            if (term_id != SeatDeatilActivity.termId) {
            getTermListDetailById(term_id, seat_id);
//            } else {
//                getSeatListById(seat_id);
//            }
//            getSeatList();

        }
    }


    private void getTermListDetailById(int termId, final String seat_id) {
        try {
            model.getTermListDetailById(termId + "", new IModelResult<GetTermListDetailByIdEntity>() {
                        @Override
                        public void OnSuccess(GetTermListDetailByIdEntity getIdEntity) {
                            String name = "";//暂无学期列表
                            if (!CommonUtils.isNull(getIdEntity)) {
                                name = getIdEntity.getTerm_name();
                                SeatDeatilActivity.termId = getIdEntity.getTerm_id();
                                SeatDeatilActivity.selectTermId = getIdEntity.getTerm_id();
                                if (CommonUtils.isNull(name)) {
                                    name = getString(R.string.no_term_list);
                                    ToastUtils.showToastShort(name);
                                } else {
                                    seatDeatilActivity.updateContent(name);
                                }
                                getSeatListById(seat_id);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}