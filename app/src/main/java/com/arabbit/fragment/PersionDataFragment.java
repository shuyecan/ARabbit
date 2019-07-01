package com.arabbit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.SeatDeatilActivity;
import com.arabbit.activity.persional.LookAlbumActivity;
import com.arabbit.activity.persional.LookVideoListActivity;
import com.arabbit.activity.persional.StudentDataMoreActivity;
import com.arabbit.activity.persional.StudentInfoActivity;
import com.arabbit.activity.promotion.UploadVideoActivity;
import com.arabbit.activity.promotion.UpxceActivity;
import com.arabbit.activity.promotion.UpvedioActivity;
import com.arabbit.activity.promotion.UpgiftActivity;
import com.arabbit.activity.promotion.UpprizeActivity;
import com.arabbit.adapter.OnTextAndLineAdapter;
import com.arabbit.adapter.OnTextAndLineAdapter.Callback;
import android.widget.AdapterView.OnItemClickListener;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.SeatListEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

import static android.app.Activity.RESULT_OK;


/**
 * 改版后的个人中心
 */
public class PersionDataFragment extends BaseFragment implements OnItemClickListener, Callback {


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
    //    @InjectView(R.id.tv_school)
//    TextView tvSchool;
    @InjectView(R.id.tv_more)
    TextView tvMore;
    @InjectView(R.id.lv_seat)
    ListViewForScrollView lvSeat;
    SocialModel model;
    private String b_id = "";

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persion_data, container, false);
        ButterKnife.inject(this, view);
        model = new SocialModel(this);
        b_id = getActivity().getIntent().getStringExtra("b_id");
        initEmptyList();
        getUserInfo();
        seatList();
        return view;
    }

    List<SeatListEntity.ListsBean> mList = new ArrayList();

    public void seatList() {
        String user_id = SPUtils.getString("user_id", "");
        try {
            model.seatList(user_id, "1", new IModelResult<SeatListEntity>() {
                @Override
                public void OnSuccess(SeatListEntity seatListEntities) {
                    mList.clear();
                    if (!CommonUtils.isNull(seatListEntities)) {
                        List<SeatListEntity.ListsBean> newList = seatListEntities.getLists();
                        if (!CommonUtils.isNull(newList)) {
                            mList.addAll(newList);
                            if (newList.size() < 4) {
                                tvMore.setVisibility(View.INVISIBLE);
                            } else {
                                tvMore.setVisibility(View.VISIBLE);
                            }
                        } else {
                            tvMore.setVisibility(View.INVISIBLE);
                        }

                    }
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
        String school = entity.getSchool();
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


    OnTextAndLineAdapter mAdapter;

    private void initEmptyList() {

        mAdapter = new OnTextAndLineAdapter(mActivity, mList, this);
        lvSeat.setAdapter(mAdapter);
        lvSeat.setOnItemClickListener(this);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }


    @OnClick({R.id.iv_image, R.id.tv_more})
    public void onClick(View view) {
        String user_id = SPUtils.getString("user_id", "");
        switch (view.getId()) {
            case R.id.tv_more:
                Intent intent = new Intent(mActivity, StudentDataMoreActivity.class);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent, 456);
                break;
            case R.id.iv_image:
                Intent infointent = new Intent(mActivity, StudentInfoActivity.class);
                infointent.putExtra("user_id", user_id);
                startActivityForResult(infointent, 444);
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

    //响应ListView中item的点击事件
      @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
          Intent intent = new Intent(mActivity, SeatDeatilActivity.class);
          intent.putExtra("seat_id", CommonUtils.formatNull(mList.get(position).getSeat_id()));
          intent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
          intent.putExtra("school", CommonUtils.formatNull(mList.get(position).getSchool_name()));
          intent.putExtra("term_id", mList.get(position).getTerm_id());
          intent.putExtra("seat_user_id", mList.get(position).getUser_id()+"");
          intent.putExtra("isLoadHistory", true);
          startActivity(intent);
    }

    //接口方法，响应ListView按钮点击事件
    @Override
    public void click(View v) {
        int position;
        position = (Integer) v.getTag();
        String user_id = SPUtils.getString("user_id", "");
        switch (v.getId()) {
            case R.id.iv_xce:
                Intent upintent2 = new Intent(mActivity, LookAlbumActivity.class);
                upintent2.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                upintent2.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
                startActivity(upintent2);
//                ToastUtils.showToastShort("这里要上传相册tup,"+position );
//                Intent upintent = new Intent(mActivity, UpxceActivity.class);
//                upintent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
//                upintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
//                upintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
//                startActivityForResult(upintent, 650);
                break;
            case R.id.iv_vedio:

                ToastUtils.showToastShort("视频功能暂未开放，敬请关注！" );

//                Intent upintent1 = new Intent(mActivity, LookVideoListActivity.class);

//                upintent1.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
//                upintent1.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
//                startActivity(upintent1);

                break;
            case R.id.iv_gift:
                Intent gfintent = new Intent(mActivity, UpgiftActivity.class);
                gfintent.putExtra("b_id",b_id);
                gfintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                gfintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
                startActivityForResult(gfintent, 656);
                break;
            case R.id.iv_przie:
                Intent pintent = new Intent(mActivity, UpprizeActivity.class);
                pintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                pintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
                startActivityForResult(pintent, 657);
                break;
            default:
                break;
        }

    }

}
