package com.arabbit.activity.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.MainActivity;
import com.arabbit.adapter.PrizeListAdapter;
import com.arabbit.adapter.PrizeListAdapter.Callback;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.PrizeListEntity;
import com.arabbit.entity.ProtionListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.ListViewForScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/7/18.
 */

public class ProtioninfoActivity extends BaseActivity implements AdapterView.OnItemClickListener, Callback {

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

    @InjectView(R.id.pt_type)
    TextView Pttype;
    @InjectView(R.id.pt_far)
    TextView Ptfar;
    @InjectView(R.id.rule)
    TextView Rule;
    @InjectView(R.id.start_time)
    TextView Starttime;
    @InjectView(R.id.end_time)
    TextView Endtime;
    @InjectView(R.id.pro_time)
    TextView Protime;
    @InjectView(R.id.user_join)
    TextView User_join;
    @InjectView(R.id.user_win)
    TextView User_win;
    @InjectView(R.id.lv_prize)
    ListViewForScrollView lvPrize;

    SocialModel model;
    String school_id = "";
    String term_id = "";
    String user_id = "";
    String pt_id = "";
    int pttype = 1;
    private SimpleDateFormat simpledateformat;
    private String endtimetamp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protion);
        ButterKnife.inject(this);
        user_id = SPUtils.getString("user_id", "");
        pt_id = getIntent().getStringExtra("pt_id");
        term_id = getIntent().getStringExtra("term_id");
        model = new SocialModel(this);
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        initTitle();
        getUserInfo();
        getProtionInfo();
        initList();
        prizeList();

    }

    public void getUserInfo() {

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

    private void initTitle() {
        titleValue.setText("展会抽奖活动");
    }

    public void getProtionInfo() {
        try {
            model.getProtionInfo(pt_id, new IModelResult<ProtionListEntity.ListsBean>() {
                @Override
                public void OnSuccess(ProtionListEntity.ListsBean entity) {
                    if (!CommonUtils.isNull(entity)) {
                        initProtionInfo(entity);

//这里
//                        initList();
//                        prizeList();
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

    private void initProtionInfo(ProtionListEntity.ListsBean entity) {
        pttype = entity.getType();
        String ptfar = entity.getFar();
        String ptstart = entity.getStarttime();
        String ptend = entity.getEndtime();
        endtimetamp = ptend;
        String count2 = CommonUtils.formatNull(entity.getCount2());
//        Log.e("aaa","fanhui"+endtimetamp);
        showlife();
        try {
            Ptfar.setText(ptfar);
            if (!TextUtils.isEmpty(ptstart)) {
                Starttime.setText(simpledateformat.format(new Date(Long.parseLong(ptstart))));
            } else {
                Starttime.setText("");
            }
            if (!TextUtils.isEmpty(ptend)) {
                String format = simpledateformat.format(new Date(Long.parseLong(ptend)));
                Endtime.setText(format);

            } else {
                Endtime.setText("");
//                Protime.setText("");
            }
            Protime.setText("10");
            User_join.setText(count2);
            int count3 = entity.getCount3();
            User_win.setText(count3 + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            initUserInfo(entity);
            ProtionListEntity.ListsBean pentity = (ProtionListEntity.ListsBean) data.getSerializableExtra("protionInfo");
            if (CommonUtils.isNull(pentity)) {
                return;
            }
            initProtionInfo(pentity);
        } else if (resultCode == 2) {
            pt_id = getIntent().getStringExtra("pt_id");
            getUserInfo();
            getProtionInfo();
            prizeList();
        }


        super.onActivityResult(requestCode, resultCode, data);

    }


    public void showlife() {
        View layoutcust = findViewById(R.id.line_cust);
        View layoutbao = findViewById(R.id.line_bao);

        if (pttype == 1) {
            Pttype.setText("普通抽奖");
            layoutcust.setVisibility(View.VISIBLE);
            layoutbao.setVisibility(View.GONE);

        } else if (pttype == 2) {
            Pttype.setText("秒爆抽奖");
            layoutcust.setVisibility(View.GONE);
            layoutbao.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.btn_add, R.id.iv_back, R.id.bt_editor, R.id.close, R.id.lay_join, R.id.lay_win, R.id.rule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_add:
                Intent intent = new Intent(mActivity, AddprzieActivity.class);
                intent.putExtra("pt_id", pt_id);
                intent.putExtra("type", pttype + "");
                startActivity(intent);
                break;
            case R.id.bt_editor:
                if (TextUtils.isEmpty(endtimetamp)) {
                    ToastUtils.showToastShort("数据异常");
                    return;
                }
                try {
                    long currentTimeStamp = System.currentTimeMillis();
                    long endtimeStamp = Long.parseLong(endtimetamp);


                    if (endtimeStamp < currentTimeStamp) {
                        ToastUtils.showToastShort("活动已结束，不能再修改");
                        return;
                    }
                    Intent intent1 = new Intent(mActivity, UpdateProtionActivity.class);
                    intent1.putExtra("pt_id", pt_id);
                    intent1.putExtra("term_id", term_id);
                    startActivity(intent1);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    ToastUtils.showToastShort("数据异常");
                }
                break;
            case R.id.lay_join:
                Intent intent2 = new Intent(mActivity, ProtionUserActivity.class);
                intent2.putExtra("pt_id", pt_id);
                startActivity(intent2);
                break;
            case R.id.lay_win:
                Intent intent3 = new Intent(mActivity, WProUserActivity.class);
                intent3.putExtra("pt_id", pt_id);
                startActivity(intent3);
                break;
            case R.id.rule:
                Intent intent4 = new Intent(mActivity, ProRuleActivity.class);
                intent4.putExtra("pt_id", pt_id);
                startActivity(intent4);
                break;
            case R.id.close:
                MainActivity.isGoMain = true;
                Intent mintent = new Intent(mActivity, MainActivity.class);
                startActivity(mintent);
                break;
        }
    }

    List<PrizeListEntity.ListsBean> mList = new ArrayList();

    public void prizeList() {

        school_id = getIntent().getStringExtra("school_id");
//        Log.e("aaa","活动id："+pt_id);
        try {
            model.getPrizeList(pt_id, new IModelResult<PrizeListEntity>() {
                @Override
                public void OnSuccess(PrizeListEntity prizeListEntitys) {
                    mList.clear();
                    if (!CommonUtils.isNull(prizeListEntitys)) {
                        List<PrizeListEntity.ListsBean> newList = prizeListEntitys.getLists();
                        if (!CommonUtils.isNull(newList)) {
                            mList.addAll(newList);
                        }
                        mAdapter.notifyDataSetChanged();

                    } else {
                        ToastUtils.showToastShort(
                                "空数据"
                        );
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

    PrizeListAdapter mAdapter;

    private void initList() {
        mAdapter = new PrizeListAdapter(mActivity, mList, this, pttype+"");
        lvPrize.setAdapter(mAdapter);
        lvPrize.setOnItemClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    //响应ListView中item的点击事件
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

        try {

            Intent intent = new Intent(mActivity, PrizeInfoActivity.class);
            intent.putExtra("pr_id", CommonUtils.formatNull(mList.get(position).getPr_id()));
            intent.putExtra("timestamp", endtimetamp);
            intent.putExtra("pt_id", pt_id);
            intent.putExtra("type", pttype+"");
            startActivityForResult(intent, 3);


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //接口方法，响应ListView按钮点击事件
    @Override
    public void click(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.pr_detail:
                Intent intent = new Intent(mActivity, PrizeUserActivity.class);
                intent.putExtra("pr_id", CommonUtils.formatNull(mList.get(position).getPr_id()));
                startActivityForResult(intent, 702);
                break;

        }
    }

}
