package com.arabbit.activity.promotion;

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
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.GiftListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.adapter.UpgiftAdapter;
import com.arabbit.adapter.UpgiftAdapter.Callback;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/6/17.
 */

public class UpgiftActivity extends BaseActivity implements AdapterView.OnItemClickListener, Callback {


    @InjectView(R.id.lv_gift)
    ListView lvGift;
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
    @InjectView(R.id.title_value)
    TextView titleValue;

    SocialModel model;
    String school_id = "";
    String term_id = "";
    String b_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgift);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        b_id = getIntent().getStringExtra("b_id");
        school_id = getIntent().getStringExtra("school_id");
//        Log.e("aaa","建筑id："+b_id);
        getUserInfo();
        initList();
        giftList();
        initTitle();

    }

    List<GiftListEntity.ListsBean> mList = new ArrayList();

    public void giftList() {
        String user_id = SPUtils.getString("user_id", "");
        term_id = getIntent().getStringExtra("term_id");
        try {
            model.getGiftList(user_id, term_id, new IModelResult<GiftListEntity>() {
                @Override
                public void OnSuccess(GiftListEntity giftListEntities) {
                    mList.clear();
                    if (!CommonUtils.isNull(giftListEntities)) {
                        List<GiftListEntity.ListsBean> newList = giftListEntities.getLists();
                        if (!CommonUtils.isNull(newList)) {
                            mList.addAll(newList);
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

    UpgiftAdapter mAdapter;

    private void initList() {
        mAdapter = new UpgiftAdapter(mActivity, mList, this);
        lvGift.setAdapter(mAdapter);
        lvGift.setOnItemClickListener(this);
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
        String url = entity.getAvatar_img();
        String name = entity.getNickname();
        String account = entity.getAccount();
        String type = CommonUtils.formatNull(entity.getRole());
        String address = entity.getAddress();
        tvName.setText(name);
        if (!CommonUtils.isNull(url)) {
//            ImgLoaderUtils.setImageloader(Config.BASE_URL + url, ivImage);
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);
        }
    }

    private void initTitle() {
        titleValue.setText("展会礼品");
    }

    @OnClick({R.id.btn_add, R.id.iv_back, R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_add:
                Intent intent1 = new Intent(mActivity, AddgiftActivity.class);
                intent1.putExtra("term_id", term_id);
                intent1.putExtra("school_id", school_id);
                intent1.putExtra("b_id", b_id);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity, MainActivity.class);
                CacheActivity.finishActivity();
                startActivity(mintent);
                break;

        }
    }

    //响应ListView中item的点击事件
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        term_id = getIntent().getStringExtra("term_id");
        Intent intent = new Intent(mActivity, GiftinfoActivity.class);
        intent.putExtra("gift_id", CommonUtils.formatNull(mList.get(position).getGift_id()));
        intent.putExtra("term_id", term_id);
        startActivity(intent);

    }

    //接口方法，响应ListView按钮点击事件
    @Override
    public void click(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.gf_detail:
                Intent intent = new Intent(mActivity, GiftUserActivity.class);
                intent.putExtra("gift_id", CommonUtils.formatNull(mList.get(position).getGift_id()));
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
