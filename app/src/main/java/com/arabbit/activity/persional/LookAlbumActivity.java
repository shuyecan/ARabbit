package com.arabbit.activity.persional;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.album.MyGridView;
import com.arabbit.activity.promotion.UpxceActivity;
import com.arabbit.adapter.LookAlbumAdapter;
import com.arabbit.bean.AlbumBean;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.IViewLoad;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.dialog.DialogYesOrNoUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.previewlibrary.loader.IZoomMediaLoader;
import com.previewlibrary.loader.MySimpleTarget;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.ui.BasePreviewActivity;
import com.zhihu.matisse.internal.ui.SelectedPreviewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;


public class LookAlbumActivity extends BaseActivity {
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.gridView)
    GridView gridList;
    SocialModel model;
    String user_id = "";
    private String term_id = "";
    private LookAlbumAdapter lookAdapter;
    List<AlbumBean> albumBeans;
    private ArrayList<ThumbViewInfo> mThumbViewInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_album);
        user_id = getIntent().getStringExtra("user_id");
        term_id = getIntent().getStringExtra("term_id");
        Log.e("aaa","上出的user_id："+user_id+",term_id："+term_id);
        model = new SocialModel(this);
        ButterKnife.inject(this);
        titleValue.setText("查看相册");
        initRight();
        ZoomMediaLoader.getInstance().init(new ImageLoader());
        getAlbumList();
    }

    public class ImageLoader implements IZoomMediaLoader {
//        RequestOptions options;
//
//        {
//            options = new RequestOptions()
//                    .centerCrop()
//                    .placeholder(R.drawable.ic_photo_camera_white_24dp)
//                    .error(R.drawable.ic_photo_camera_white_24dp)
//                    .priority(Priority.HIGH);
//        }

        @Override
        public void displayImage(Fragment context, String path, final MySimpleTarget<Bitmap> simpleTarget) {
            Glide.with(context)
                    .load(path)
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.ic_photo_camera_white_24dp)
                    .error(R.drawable.ic_photo_camera_white_24dp)
                    .priority(Priority.HIGH)
                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                            simpleTarget.onResourceReady(resource);
//                        }
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            simpleTarget.onLoadStarted();
                        }
//                        @Override
//                        public void onLoadFailed(Drawable errorDrawable) {
//                            super.onLoadFailed(errorDrawable);
//                            simpleTarget.onLoadFailed(errorDrawable);
//                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            simpleTarget.onLoadFailed(errorDrawable);
                        }

                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            simpleTarget.onResourceReady(resource);
                        }
                    });
        }

        @Override
        public void onStop(@NonNull Fragment context) {
            Glide.with(context).onStop();
        }
        @Override
        public void clearMemory(@NonNull Context c) {
            Glide.get(c).clearMemory();
        }
    }

    private void initRight() {
        String role = SPUtils.getString("role", "");
        if(TextUtils.equals(role,"1")){//公司用户
            String currentuser_id = SPUtils.getString("user_id", "");
            if(TextUtils.equals(currentuser_id,user_id)){
                tvAdd.setVisibility(View.VISIBLE);
                tvAdd.setText("上传");
            }else{
                tvAdd.setVisibility(View.GONE);
            }
        }else{//非公司用户
            tvAdd.setVisibility(View.GONE);
        }
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upintent = new Intent(mActivity, UpxceActivity.class);
                upintent.putExtra("term_id", term_id);
                upintent.putExtra("user_id", user_id);
                startActivityForResult(upintent,8);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8 && resultCode == 3){
            getAlbumList();
        }
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
    private void getAlbumList() {
        model.getAlbumList(user_id, term_id, new IModelResult<List<AlbumBean>>() {
            @Override
            public void OnSuccess(List<AlbumBean> albumBean) {
                if(albumBean != null && albumBean.size()>0){
                    setList(albumBean);
                }else{
                    //ToastUtils.showToastShort("暂无数据");
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
        lookAdapter = new LookAlbumAdapter(this,albumBean);
        gridList.setAdapter(lookAdapter);
        final ArrayList<String> imglists = new ArrayList<>();
        for (int i=0;i<albumBeans.size();i++){
            AlbumBean albumBean1 = albumBeans.get(i);
            String url = albumBean1.getUrl();
            imglists.add(url);

        }
        // 这个最好定义成成员变量
        mThumbViewInfoList = new ArrayList<>();
        ThumbViewInfo item;
        mThumbViewInfoList.clear();
        for (int i = 0;i < albumBeans.size(); i++) {
            Rect bounds = new Rect();
            //new ThumbViewInfo(图片地址);
            AlbumBean albumBean1 = albumBeans.get(i);
            String url = albumBean1.getUrl();
            item=new ThumbViewInfo(Config.IMG_URL+url);
            item.setBounds(bounds);
            mThumbViewInfoList.add(item);
        }
        gridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlbumBean albumbean = (AlbumBean)adapterView.getAdapter().getItem(i);
//                String url = albumbean.getUrl();
//                Intent intent = new Intent(LookAlbumActivity.this,LookSingleAlbumActivity.class);
//                intent.putExtra("pos",i);
//                intent.putStringArrayListExtra("imglists",imglists);
//                startActivity(intent);

//                Intent intent = new Intent(LookAlbumActivity.this, SelectedPreviewActivity.class);
//                intent.putExtra(BasePreviewActivity.EXTRA_DEFAULT_SELECTED, (ArrayList<Item>) mSelectedCollection.asList());
//                startActivityForResult(intent, REQUEST_CODE_PREVIEW);



//打开预览界面
                GPreviewBuilder.from(LookAlbumActivity.this)
                        //是否使用自定义预览界面，当然8.0之后因为配置问题，必须要使用
                        //.to(ImageLookActivity.class)
                        .setData(mThumbViewInfoList)
                        .setCurrentIndex(i)
                        .setSingleFling(true)
                        .setType(GPreviewBuilder.IndicatorType.Number)
                        // 小圆点
//  .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start();//启动





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
            model.deletePhoto(imgid, new IModelResult<List<AlbumBean>>() {
                @Override
                public void OnSuccess(List<AlbumBean> albumBean) {
                    ToastUtils.showToastShort("删除成功");
                    albumBeans.remove(pos);
                    if(mThumbViewInfoList != null && mThumbViewInfoList.size()>0){
                        mThumbViewInfoList.remove(pos);
                    }

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
