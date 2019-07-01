package com.arabbit.activity.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.Userpage.SeeUserCardActivity;
import com.arabbit.adapter.JoinUserAdapter;
import com.arabbit.adapter.JoinUserAdapter.Callback;
import com.arabbit.entity.UserJoingiftEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/10/3.
 */

public class JoinUserActivity  extends BaseActivity implements AdapterView.OnItemClickListener,Callback {

    @InjectView(R.id.lv_gfuser)
    ListView lvGfuser;
    @InjectView(R.id.title_value)
    TextView titleValue;


    @InjectView(R.id.ed_num)
    EditText edNum;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    int page = 1;
    int accountPage = 1;

    SocialModel model;
    String gift_id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinuser);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initEdit();
        initList();
        giftList(page);
        initTitle();
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
                    giftList(newPage);
                }
                return false;
            }
        });
    }

    List<UserJoingiftEntity.ListsBean> mList = new ArrayList();

    public void giftList(final int page) {
        gift_id = getIntent().getStringExtra("gift_id");
        try {
            model.getJoinUser(gift_id,page,new IModelResult<UserJoingiftEntity>() {
                @Override
                public void OnSuccess(UserJoingiftEntity usergiftEntities) {
                    mList.clear();
                    if (!CommonUtils.isNull(usergiftEntities)) {
                        List<UserJoingiftEntity.ListsBean> newList = usergiftEntities.getLists();
                        if (!CommonUtils.isNull(newList)) {
                            mList.addAll(newList);
                        }
                        accountPage = usergiftEntities.getTotal_page();
                        tvCount.setText("/" + accountPage);
                        edNum.setText(page + "");
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

    JoinUserAdapter mAdapter;

    private void initList() {
        mAdapter = new JoinUserAdapter(mActivity, mList,this);
        lvGfuser.setAdapter(mAdapter);
        lvGfuser.setOnItemClickListener(this);
    }


    private void initTitle() {

        titleValue.setText("参加活动用户");
    }

    @OnClick({R.id.iv_back, R.id.close, R.id.tv_prevs, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity,MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
            case R.id.tv_prevs:
                if (page == 1) {
                    ToastUtils.showToastShort(R.string.first_page);
                    return;
                }
                page--;
                giftList(page);
                break;
            case R.id.tv_next:
                if (page == accountPage) {
                    ToastUtils.showToastShort(R.string.last_page);
                    return;
                }
                page++;
                giftList(page);
                break;

        }
    }

    //响应ListView中item的点击事件
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        Intent intent = new Intent(mActivity,SeeUserCardActivity.class);
        intent.putExtra("user_id",CommonUtils.formatNull(mList.get(position).getUser_id()));
        startActivity(intent);

    }

    //接口方法，响应ListView按钮点击事件
    @Override
    public void click(View v) {
//        int position;
//            position = (Integer) v.getTag();
//            switch (v.getId()) {
//                case R.id.gf_name:
//                    Intent intent = new Intent(mActivity, GiftinfoActivity.class);
//                    intent.putExtra("gift_id", CommonUtils.formatNull(mList.get(position).getGift_id()));
//                    startActivityForResult(intent, 705);
//                    finish();
//                    break;
//
//        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }



}
