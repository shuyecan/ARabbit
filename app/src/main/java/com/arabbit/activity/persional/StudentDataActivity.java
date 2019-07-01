package com.arabbit.activity.persional;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.SeatDeatilActivity;
import com.arabbit.activity.SetUpActivity;
import com.arabbit.activity.Userpage.SeeGiftActivity;
import com.arabbit.activity.Userpage.ComProtionActivity;
import com.arabbit.activity.promotion.UploadVideoActivity;
import com.arabbit.activity.promotion.UpvedioActivity;
import com.arabbit.activity.promotion.UpxceActivity;
import com.arabbit.adapter.OnTextAndLineAdapter;
import com.arabbit.adapter.OnTextAndLineAdapter.Callback;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.SeatListEntity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;

public class StudentDataActivity extends BaseActivity implements AdapterView.OnItemClickListener, Callback {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
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
    @InjectView(R.id.lv_seat)
    ListViewForScrollView lvSeat;
    @InjectView(R.id.tv_more)
    TextView tvMore;
    @InjectView(R.id.layout_buttom)
    LinearLayout layoutButtom;


    SocialModel model;
    String target_user_id = "";
    private String company_name = "";
    private String seat_user_id = "";
    private String b_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional);
        ButterKnife.inject(this);
        if (!CacheActivity.activityList.contains(StudentDataActivity.this)) {
            CacheActivity.addActivity(StudentDataActivity.this);
        }
        target_user_id = getIntent().getStringExtra("user_id");
        b_id = getIntent().getStringExtra("b_id");
        seat_user_id = getIntent().getStringExtra("seat_user_id");
        Log.e("aaa","StudentDataActivity编号sn:"+seat_user_id);
        initTitle();
        model = new SocialModel(this);
        initEmptyList();
        getUserInfo();
        seatList();
    }


    List<SeatListEntity.ListsBean> mList = new ArrayList();

    public void seatList() {
        try {
            model.seatList(target_user_id, "1", new IModelResult<SeatListEntity>() {
                @Override
                public void OnSuccess(SeatListEntity seatListEntities) {
                    mList.clear();
//                    List<SeatListEntity.ListsBean> lists = seatListEntities.getLists();
//                    for(int i=0;i<lists.size();i++){
//                        SeatListEntity.ListsBean listsBean = lists.get(i);
//                        String name = listsBean.getName();
//                        String city = listsBean.getCity();
//                        String no = listsBean.getNo();
//                        String school_name = listsBean.getSchool_name();
//                        int school_id = listsBean.getSchool_id();
//                        Log.e("aaa",school_id+",列表数据："+name+",city:"+city+",no:"+no+",school_name:"+school_name);
//                    }
//                    Log.e("aaa","列表数据："+lists.toString());
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
        try {
            model.getUserInfo(target_user_id, new IModelResult<GetUserInfoEntity>() {
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
        String type = entity.getRole() > 1 ? getString(R.string.other) : getString(R.string.student);
        String address = entity.getAddress();
        String role = CommonUtils.formatNull(entity.getRole());
        company_name = name;
        tvName.setText(name);
        if (!CommonUtils.isNull(url)) {
            ImgLoaderUtils.setImageloader(Config.IMG_URL +url, ivImage);
        }
        tvAddress.setText(address);
        tvAccount.setText(account);
        tvType.setText(type);
    }

    private void initTitle() {
                titleValue.setText(R.string.perional_data);
        tvAdd.setVisibility(View.GONE);
        String currentUserId = SPUtils.getString("user_id", "");
        int type = SPUtils.getInt("type", 1);
        if (currentUserId.equals(target_user_id) && type != 2) {
            layoutButtom.setVisibility(View.GONE);
        }
        if (type == 2) {
            layoutButtom.setVisibility(View.GONE);
        }
    }


    OnTextAndLineAdapter mAdapter;

    private void initEmptyList() {

        mAdapter = new OnTextAndLineAdapter(this, mList,this);
        lvSeat.setAdapter(mAdapter);
        lvSeat.setOnItemClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_more, R.id.tv_send, R.id.tv_chat, R.id.tv_add, R.id.iv_image})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                startActivity(new Intent(mActivity, SetUpActivity.class));
                break;
            case R.id.tv_more:
                Intent intent = new Intent(mActivity, StudentDataMoreActivity.class);
                intent.putExtra("user_id", target_user_id);
                intent.putExtra("seat_user_id", seat_user_id);
                startActivityForResult(intent, 456);
                break;
            case R.id.iv_image:
                Intent infointent = new Intent(mActivity, StudentInfoActivity.class);
                infointent.putExtra("user_id", target_user_id);
                startActivityForResult(infointent, 444);
                break;
            case R.id.tv_send:
                break;
            case R.id.tv_chat:
                break;

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
        intent.putExtra("isLoadHistory", true);
//          SeatListEntity.ListsBean listsBean = mList.get(position);
          intent.putExtra("name", company_name);
          intent.putExtra("seat_user_id",seat_user_id);
          Log.e("aaa","onItemClick编号seat_user_id:"+seat_user_id);
          startActivity(intent);
    }

 //   接口方法，响应ListView按钮点击事件
 @Override
    public void click(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.iv_xce:
//                String role = SPUtils.getString("role", "");
//                if(TextUtils.equals(role,"1")){//公司用户,上传相册的功能
//                    String currentuser_id = SPUtils.getString("user_id", "");
//                    String list_userid = CommonUtils.formatNull(mList.get(position).getUser_id());
//                    if(TextUtils.equals(currentuser_id,list_userid)){
//                        Intent upintent = new Intent(mActivity, UpxceActivity.class);
//                        upintent.putExtra("user_id",list_userid);
//                        upintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
//                        upintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
//                        startActivityForResult(upintent, 650);
//                    }else{
//                        Intent upintent = new Intent(mActivity, LookAlbumActivity.class);
//                        upintent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
//                        upintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
//                        upintent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
//                        startActivity(upintent);
//                    }
//
//                }else{//非公司用户查看的功能
//                    Intent upintent = new Intent(mActivity, LookAlbumActivity.class);
//                    upintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
//                    upintent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
//                    startActivity(upintent);
//                }
                Intent upintent1 = new Intent(mActivity, LookAlbumActivity.class);
                upintent1.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                upintent1.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
                startActivity(upintent1);
                break;
            case R.id.iv_vedio:
                Intent upintent = new Intent(mActivity, LookVideoListActivity.class);

                upintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                upintent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
                startActivity(upintent);
//                String role1 = SPUtils.getString("role", "");
//                if(TextUtils.equals(role1,"1")){//公司用户,上传相册的功能
//                    String currentuser_id = SPUtils.getString("user_id", "");
//                    String list_userid = CommonUtils.formatNull(mList.get(position).getUser_id());
//                    if(TextUtils.equals(currentuser_id,list_userid)){
//                        Intent upvintent = new Intent(mActivity, UploadVideoActivity.class);
//                        upvintent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
//                        upvintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
//                        upvintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
//                        startActivityForResult(upvintent, 655);
//                    }else{
//                        Intent upintent = new Intent(mActivity, LookVideoListActivity.class);
//
//                        upintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
//                        upintent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
//                        startActivity(upintent);
//                    }
//                }else{//非公司用户查看的功能
//                    Intent upintent = new Intent(mActivity, LookVideoListActivity.class);
//                    upintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
//                    upintent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
//                    startActivity(upintent);
//                }




                break;
            case R.id.iv_gift:
                Intent gfintent = new Intent(mActivity, SeeGiftActivity.class);
                gfintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                gfintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
                gfintent.putExtra("user_id", target_user_id);
                gfintent.putExtra("b_id", b_id);
                startActivityForResult(gfintent, 656);
//                startActivity(gfintent);
                break;
            case R.id.iv_przie:
                Intent pintent = new Intent(mActivity, ComProtionActivity.class);
                String seatid  = mList.get(position).getSeat_id() + "";
                pintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                pintent.putExtra("user_id", target_user_id);
                pintent.putExtra("seatid", seatid);
                startActivityForResult(pintent, 657);
//                startActivity(pintent);
                break;
            default:
                break;
        }

    }


}
