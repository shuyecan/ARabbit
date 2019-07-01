package com.arabbit.activity.shop;

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
import com.arabbit.activity.Userpage.UserInfoActivity;
import com.arabbit.adapter.ShopptUserAdapter;
import com.arabbit.entity.HadShopptEntity;
import com.arabbit.entity.UserHadproEntity;
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
 * Created by Administrator on 2018/11/14.
 */

public class ShopptUserActivity extends BaseActivity {

    @InjectView(R.id.lv_gfuser)
    ListView lvProuser;
    @InjectView(R.id.title_value)
    TextView titleValue;


    @InjectView(R.id.ed_num)
    EditText edNum;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    int page = 1;
    int accountPage = 1;

    SocialModel model;
    String pt_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftuser);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        initEdit();
        initList();
        proList(page);
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
                    proList(newPage);
                }
                return false;
            }
        });
    }
    List<HadShopptEntity.ListsBean> mList = new ArrayList();

    public void proList(int newpage) {
        pt_id = getIntent().getStringExtra("pt_id");
        try {
            model.getUserShopPtList(pt_id,newpage, new IModelResult<HadShopptEntity>() {
                @Override
                public void OnSuccess(HadShopptEntity hadShopptEntities) {
                    mList.clear();
                    if (!CommonUtils.isNull(hadShopptEntities)) {
                        List<HadShopptEntity.ListsBean> newList = hadShopptEntities.getLists();
                        if (!CommonUtils.isNull(newList)) {
                            mList.addAll(newList);
                        }
                        accountPage = hadShopptEntities.getTotal_page();
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

    ShopptUserAdapter mAdapter;

    private void initList() {
        mAdapter = new ShopptUserAdapter(mActivity, mList);
        lvProuser.setAdapter(mAdapter);
        lvProuser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                 Intent intent = new Intent(mActivity, UserInfoActivity.class);
                                                 intent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
                                                 startActivity(intent);
                                             }
                                         }
        );
    }


    private void initTitle() {

        titleValue.setText("参与活动的用户");
    }

    @OnClick({R.id.iv_back, R.id.close, R.id.tv_prevs, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity, MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
            case R.id.tv_prevs:
                if (page == 1) {
                    ToastUtils.showToastShort(R.string.first_page);
                    return;
                }
                page--;
                proList(page);
                break;
            case R.id.tv_next:
                if (page == accountPage) {
                    ToastUtils.showToastShort(R.string.last_page);
                    return;
                }
                page++;
                proList(page);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }


}
