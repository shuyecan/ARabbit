package com.arabbit.activity.persional;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.SeatDeatilActivity;
import com.arabbit.activity.Userpage.ComProtionActivity;
import com.arabbit.activity.Userpage.SeeGiftActivity;
import com.arabbit.activity.promotion.UpgiftActivity;
import com.arabbit.activity.promotion.UploadVideoActivity;
import com.arabbit.activity.promotion.UpprizeActivity;
import com.arabbit.activity.promotion.UpvedioActivity;
import com.arabbit.activity.promotion.UpxceActivity;
import com.arabbit.adapter.OnTextAndLineAdapter;
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
import com.arabbit.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;


/**
 * 改版的个人中心-查看更多
 */
public class StudentDataMoreActivity extends BaseActivity implements AdapterView.OnItemClickListener, OnTextAndLineAdapter.Callback {

    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.iv_image_more)
    ImageView ivImageMore;
    @InjectView(R.id.tv_name_more)
    TextView tvNameMore;
    @InjectView(R.id.tv_type_more)
    TextView tvTypeMore;
    @InjectView(R.id.tv_address_more)
    TextView tvAddressMore;
    @InjectView(R.id.tv_account_more)
    TextView tvAccountMore;
    @InjectView(R.id.lv_seat_more)
    ListView lvSeatMore;
    @InjectView(R.id.ed_num)
    EditText edNum;
    @InjectView(R.id.tv_count)
    TextView tvCount;

    SocialModel model;

    String user_id = "";
    private String seat_user_id = "";
    private String company_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_more);
        ButterKnife.inject(this);
        if (!CacheActivity.activityList.contains(StudentDataMoreActivity.this)) {
            CacheActivity.addActivity(StudentDataMoreActivity.this);
        }
        user_id = getIntent().getStringExtra("user_id");
        seat_user_id = getIntent().getStringExtra("seat_user_id");
        initTitle();
        model = new SocialModel(this);
        initEmptyList();
        initEdit();
        getUserInfo();
        seatList(page);
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
                    seatList(newPage);
                }
                return false;
            }
        });
    }

    List<SeatListEntity.ListsBean> mList = new ArrayList();

    int page = 1;
    int accountPage = 1;

    public void seatList(final int currentPage) {
        this.page = currentPage;
        try {
            model.seatList(user_id, currentPage + "", new IModelResult<SeatListEntity>() {
                @Override
                public void OnSuccess(SeatListEntity seatListEntities) {
                    mList.clear();
                    if (!CommonUtils.isNull(seatListEntities)) {
                        List<SeatListEntity.ListsBean> newList = seatListEntities.getLists();
                        if (!CommonUtils.isNull(newList)) {
                            mList.addAll(newList);
                        }
                        accountPage = seatListEntities.getTotal_page();
                    }
                    tvCount.setText("/" + accountPage);
                    edNum.setText(currentPage + "");
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

    GetUserInfoEntity userInfoEntity;

    public void getUserInfo() {
        try {
            model.getUserInfo(user_id, new IModelResult<GetUserInfoEntity>() {
                @Override
                public void OnSuccess(GetUserInfoEntity entity) {
                    if (!CommonUtils.isNull(entity)) {
                        userInfoEntity = entity;
                        initUserInfo(userInfoEntity);
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
        String school = entity.getSchool();
        tvNameMore.setText(name);
        company_name = name;
        if (!CommonUtils.isNull(url)) {
            ImgLoaderUtils.setImageloader(Config.IMG_URL + url, ivImageMore);
        }
        tvAddressMore.setText(address);
        tvAccountMore.setText(account);
        tvTypeMore.setText(type);
    }

    private void initTitle() {
        titleValue.setText(R.string.perional_data);
        tvAdd.setVisibility(View.GONE);

    }


    OnTextAndLineAdapter mAdapter;

    private void initEmptyList() {

        mAdapter = new OnTextAndLineAdapter(this, mList, true,this);
        lvSeatMore.setAdapter(mAdapter);
        lvSeatMore.setOnItemClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_image_more, R.id.tv_prevs, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_image_more:
                finish();
                break;
            case R.id.tv_prevs:
                if (page == 1) {
                    ToastUtils.showToastShort(R.string.first_page);
                    return;
                }
                page--;
                seatList(page);
                break;
            case R.id.tv_next:
                if (page == accountPage) {
                    ToastUtils.showToastShort(R.string.last_page);
                    return;
                }
                page++;
                seatList(page);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
            if (CommonUtils.isNull(entity)) {
                return;
            }
            userInfoEntity = entity;
            initUserInfo(userInfoEntity);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra("userInfo", userInfoEntity));
        super.onBackPressed();
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

          intent.putExtra("name", company_name);
          intent.putExtra("seat_user_id",seat_user_id);
        startActivity(intent);
    }

    //   接口方法，响应ListView按钮点击事件
    @Override
    public void click(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.iv_xce:
                Intent upintent1 = new Intent(mActivity, LookAlbumActivity.class);
                upintent1.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                upintent1.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
                startActivity(upintent1);
//                String role = SPUtils.getString("role", "");
//                if(TextUtils.equals(role,"1")){//公司用户,上传相册的功能
//                    String currentuser_id = SPUtils.getString("user_id", "");
//                    String list_userid = CommonUtils.formatNull(mList.get(position).getUser_id());
//                    if(TextUtils.equals(currentuser_id,list_userid)){
//                        Intent upintent = new Intent(mActivity, UpxceActivity.class);
//                        upintent.putExtra("user_id", CommonUtils.formatNull(mList.get(position).getUser_id()));
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
//                Intent upintent = new Intent(mActivity, UpxceActivity.class);
//                upintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
//                upintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
//                startActivityForResult(upintent, 650);
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
                String role= SPUtils.getString("role", "");
                if (user_id .equals(SPUtils.getString("user_id", "")) ){
                    Intent gfintent = new Intent(mActivity, UpgiftActivity.class);
                    gfintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                    gfintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
                    startActivityForResult(gfintent, 656);
                    break;
                }else{
                Intent gfintent = new Intent(mActivity, SeeGiftActivity.class);
                gfintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                gfintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
                gfintent.putExtra("user_id", user_id);
                startActivityForResult(gfintent, 656);
                break;
                }
            case R.id.iv_przie:
                if (user_id .equals(SPUtils.getString("user_id", "")) ){
                    Intent pintent = new Intent(mActivity, UpprizeActivity.class);
                    pintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                    pintent.putExtra("school_id", CommonUtils.formatNull(mList.get(position).getSchool_id()));
                    startActivityForResult(pintent, 657);
                    break;
                }else {
                    Intent pintent = new Intent(mActivity, ComProtionActivity.class);
                    pintent.putExtra("term_id", CommonUtils.formatNull(mList.get(position).getTerm_id()));
                    pintent.putExtra("user_id", user_id);
                    startActivityForResult(pintent, 657);
                    break;
                }

            default:
                break;
        }

    }




}
