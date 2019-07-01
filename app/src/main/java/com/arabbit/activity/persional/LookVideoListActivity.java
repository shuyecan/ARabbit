package com.arabbit.activity.persional;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.promotion.UploadVideoActivity;
import com.arabbit.adapter.LookAlbumAdapter;
import com.arabbit.adapter.VideoAdapter;
import com.arabbit.adapter.VideoListAdapter;
import com.arabbit.bean.AlbumBean;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.dialog.DialogYesOrNoUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;


public class LookVideoListActivity extends BaseActivity {
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.gridView)
    GridView gridList;
    SocialModel model;
    String user_id = "";
    private String term_id = "";
    private VideoListAdapter lookAdapter;
    List<AlbumBean> albumBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_album);
        user_id = getIntent().getStringExtra("user_id");
        term_id = getIntent().getStringExtra("term_id");
        Log.e("aaa","上出的user_id："+user_id+",term_id："+term_id);
        model = new SocialModel(this);
        ButterKnife.inject(this);
        titleValue.setText("活动视频列表");
        initRight();
        getVideoList();
    }

    private void initRight() {
        String role = SPUtils.getString("role", "");
        if(TextUtils.equals(role,"1")){//公司用户
            String currentuser_id = SPUtils.getString("user_id", "");
            if(TextUtils.equals(currentuser_id,user_id)){
                tvAdd.setVisibility(View.VISIBLE);
                tvAdd.setText("编辑");
            }else{
                tvAdd.setVisibility(View.GONE);
            }

        }else{//非公司用户
            tvAdd.setVisibility(View.GONE);
        }
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upintent = new Intent(mActivity, UploadVideoActivity.class);
                upintent.putExtra("term_id", term_id);
                upintent.putExtra("user_id", user_id);
                startActivity(upintent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
    @OnClick({R.id.iv_back})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
    private void getVideoList() {
        model.getVideoList(user_id, term_id, new IModelResult<List<AlbumBean>>() {
            @Override
            public void OnSuccess(List<AlbumBean> albumBean) {
//                Log.e("aaa","视频数量："+albumBean.size());
                if(albumBean != null && albumBean.size()>0){
                    setList(albumBean);
                }else{
//                    ToastUtils.showToastShort("暂无数据");
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


    }

    private void setList(List<AlbumBean> albumBean) {
        this.albumBeans = albumBean;
        lookAdapter = new VideoListAdapter(this,albumBean);
        gridList.setAdapter(lookAdapter);
        gridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlbumBean albumbean = (AlbumBean)adapterView.getAdapter().getItem(i);
                String video = Config.IMG_URL+albumbean.getUrl();
//                    Log.e("aaa","视频播放路径："+video);
                Intent openVideo = new Intent(Intent.ACTION_VIEW);
                openVideo.setDataAndType(Uri.parse(video), "video/*");
                startActivity(openVideo);
//                String url = albumbean.getUrl();
//                Intent intent = new Intent(LookVideoListActivity.this,LookSingleAlbumActivity.class);
//                intent.putExtra("imgurl",url);
//                startActivity(intent);
            }
        });


        String role = SPUtils.getString("role", "");
        if(TextUtils.equals(role,"1")) {//公司用户,上传相册的功能
            String currentuser_id = SPUtils.getString("user_id", "");
            if(TextUtils.equals(currentuser_id,user_id)){
                gridList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AlbumBean albumbena = (AlbumBean)adapterView.getAdapter().getItem(i);
                        showTip(i,albumbena.getId());
                        return true;
                    }
                });
            }

        }








    }

    private void showTip(final int pos,final String imgid) {

        final DialogYesOrNoUtil dialogYesOrNoUtil = new DialogYesOrNoUtil(this);
        dialogYesOrNoUtil.setDialogTitle(getString(R.string.tips));
        dialogYesOrNoUtil.setContent("确定要删除？");
        dialogYesOrNoUtil.setNoText(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYesOrNoUtil.dismiss();
//                mActivity.finish();
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
            model.deleteVideo(imgid, new IModelResult<List<AlbumBean>>() {
                @Override
                public void OnSuccess(List<AlbumBean> albumBean) {
                    ToastUtils.showToastShort("删除成功");
                    albumBeans.remove(pos);
                    if(lookAdapter != null){
                        lookAdapter.notifyDataSetChanged();
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


}
