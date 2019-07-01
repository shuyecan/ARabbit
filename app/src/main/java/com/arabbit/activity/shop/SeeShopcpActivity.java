package com.arabbit.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.activity.promotion.AddprotionActivity;
import com.arabbit.adapter.ShopcpListAdapter;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.ShopcpListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/2.
 */

public class SeeShopcpActivity extends BaseActivity implements AdapterView.OnItemClickListener,ShopcpListAdapter.Callback {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.iv_image)
    ImageView ivImage;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_account)
    TextView tvAccount;
    @InjectView(R.id.lv_cpgood)
    ListView lvCpgood;

    SocialModel model;
    String target_user_id = "";
    String user_id ="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_shopcp);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        target_user_id = getIntent().getStringExtra("user_id");
        initTitle();
        Log.e("aaa","重新开启界面onCreate");
        getUserInfo();
        initList();
        shopcpListList();
        View layoutoper=findViewById(R.id.cp_oper);
        layoutoper.setVisibility(View.GONE);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("aaa","重新开启界面onNewIntent");
    }

    public void getUserInfo() {
        String user_id = SPUtils.getString("user_id", "");
        try {
            model.getUserInfo(user_id, new IModelResult<GetUserInfoEntity>() {
                @Override
                public void OnSuccess(GetUserInfoEntity entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initUserInfo(entity);

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

    private void initUserInfo(GetUserInfoEntity entity) {
        try {
            String url = entity.getAvatar_img();
            String name = entity.getNickname();
            String account = entity.getAccount();
            String type = CommonUtils.formatNull(entity.getRole());
            String address = entity.getAddress();
            tvName.setText(name);
            if (!CommonUtils.isNull(url)) {
                ImgLoaderUtils.setImageloader(Config.IMG_URL + url, ivImage);
            }
            tvAddress.setText(address);
            tvAccount.setText(account);
            if (type.equals("1")){
                tvType.setText("公司");
            }else if(type.equals("2")){
                tvType.setText("个人");
            }else if(type.equals("3")){
                tvType.setText("店铺");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTitle() {
        titleValue.setText("打折降价促销");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
//            Log.e("aaa","重新开启界面onActivityResult");
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);
        }


        super.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick({R.id.btn_add,R.id.iv_back, R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_add:
                Intent intent = new Intent(mActivity, AddprotionActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity,MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;
        }
    }

    List<ShopcpListEntity.ListsBean> mList = new ArrayList();

    public void shopcpListList() {

        try {
            model.getShopcpList(user_id, new IModelResult<ShopcpListEntity>() {
                @Override
                public void OnSuccess(ShopcpListEntity shopcpListEntitys) {
                    try {
                        mList.clear();
                        if (!CommonUtils.isNull(shopcpListEntitys)) {
                            Log.e("aaa","重新开启界面OnSuccess");
                            List<ShopcpListEntity.ListsBean> newList =shopcpListEntitys.getLists();
                            if (!CommonUtils.isNull(newList)) {
                                mList.addAll(newList);
                            }
                            Log.e("aaa",mList.size()+"重新开启界面OnSuccess");
                            mAdapter.notifyDataSetChanged();

                        }else {
                            ToastUtils.showToastShort(
                                    "空数据"
                            );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

    ShopcpListAdapter mAdapter;

    private void initList() {
        mAdapter = new ShopcpListAdapter(mActivity, mList, this);
        lvCpgood.setAdapter(mAdapter);
        lvCpgood.setOnItemClickListener(this);

    }


    //响应ListView中item的点击事件
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        Intent intent = new Intent(mActivity, ShopcpgoodActivity.class);
        intent.putExtra("cp_id", CommonUtils.formatNull(mList.get(position).getCp_id()));
        startActivity(intent);

    }

    //接口方法，响应ListView按钮点击事件
    @Override
    public void click(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.gf_detail:
                Intent intent = new Intent(mActivity, ShopcpgoodActivity.class);
                intent.putExtra("cp_id", CommonUtils.formatNull(mList.get(position).getCp_id()));
                startActivityForResult(intent, 701);
                finish();
                break;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
