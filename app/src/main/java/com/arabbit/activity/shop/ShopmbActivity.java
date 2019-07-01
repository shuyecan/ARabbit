package com.arabbit.activity.shop;

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
import com.arabbit.adapter.ShopmbAdapter;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.ShopmbListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.dialog.DialogYesOrNoUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by Administrator on 2018/11/1.
 * 店铺秒爆活动
 */

public class ShopmbActivity extends BaseActivity {

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
    @InjectView(R.id.lv_shopmb)
    ListView lvShopmb;

    SocialModel model;
    String user_id ="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopmb);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        user_id = SPUtils.getString("user_id", "");
        initTitle();
        getUserInfo();
        initList();
        shopmbList();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
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
        titleValue.setText("秒爆促销活动");
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
                Intent intent = new Intent(mActivity, AddShopmbActivity.class);
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

    List<ShopmbListEntity.ListsBean> mList = new ArrayList();

    public void shopmbList() {

        try {
            model.getShopmbList(user_id,  new IModelResult<ShopmbListEntity>() {
                @Override
                public void OnSuccess(ShopmbListEntity shopmbListEntitys) {
                    try {
                        mList.clear();
                        if (!CommonUtils.isNull(shopmbListEntitys)) {
                            List<ShopmbListEntity.ListsBean> newList =shopmbListEntitys.getLists();
                            if (!CommonUtils.isNull(newList)) {
                                mList.addAll(newList);
                            }
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

    ShopmbAdapter mAdapter;

    private void initList() {
        mAdapter = new ShopmbAdapter(mActivity, mList);
        lvShopmb.setAdapter(mAdapter);
        lvShopmb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, ShopmbListActivity.class);
                intent.putExtra("mb_id", CommonUtils.formatNull(mList.get(position).getMb_id()));
                startActivity(intent);
            }
        });



        lvShopmb.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                try {
                    ShopmbListEntity.ListsBean entity = (ShopmbListEntity.ListsBean)adapterView.getAdapter().getItem(i);
                    String endtime = entity.getEndtime();
                    if(!TextUtils.equals(endtime,null) && !TextUtils.isEmpty(endtime)){
                        long endTimeStamp = Long.parseLong(endtime);

                        long currentTimeMillis = System.currentTimeMillis();
                        if(endTimeStamp >= currentTimeMillis){
                            showTip(i,entity.getMb_id()+"");
                        }else{
                            ToastUtils.showToastShort("活动已结束,不可删除");
                        }
                    }else{
                        ToastUtils.showToastShort("活动已结束,不可删除");
                    }

                } catch (NumberFormatException e) {
                    ToastUtils.showToastShort("活动已结束,不可删除");
                    e.printStackTrace();
                }


                return true;
            }
        });

    }
    private void showTip(final int pos,final String imgid) {

        final DialogYesOrNoUtil dialogYesOrNoUtil = new DialogYesOrNoUtil(this);
        dialogYesOrNoUtil.setDialogTitle(getString(R.string.tips));
        dialogYesOrNoUtil.setContent("确定要删除？");
        dialogYesOrNoUtil.setNoText(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYesOrNoUtil.dismiss();
            }

        });
        dialogYesOrNoUtil.setYesText(getString(R.string.confirm), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYesOrNoUtil.dismiss();
                deletePhotoAndSumbit(pos,imgid);
            }
        });
        dialogYesOrNoUtil.show();






    }
    private void deletePhotoAndSumbit(final int pos,String imgid) {

        try {
            model.delShopmb(imgid, new IModelResult<ShopmbListEntity.ListsBean>() {
                @Override
                public void OnSuccess(ShopmbListEntity.ListsBean albumBean) {
                    ToastUtils.showToastShort("删除成功");
                    mList.remove(pos);
                    if(mAdapter != null){
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void OnError(ApiException e) {
                    ToastUtils.showToastShort(""+e.message);
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
