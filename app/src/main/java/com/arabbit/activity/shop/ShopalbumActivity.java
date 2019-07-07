package com.arabbit.activity.shop;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.adapter.ShopalbumAdapter;
import com.arabbit.entity.BaseResult;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.entity.ShopImgEntity;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.request.UploadPicEntity;
import com.arabbit.utils.DialogHelper;
import com.arabbit.utils.HttpClientUtil;
import com.arabbit.utils.ToastUtils;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Subscription;

public class ShopalbumActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.title_value)
    TextView titleValue;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.recycler_album)
    RecyclerView recyclerAlbum;
    @InjectView(R.id.btn_savealbum)
    Button btnSavealbum;
    private TakePhoto takePhoto;
    private Uri imageUri;//图片保存路径\
    ShopalbumAdapter adapter;
     List<String> urllist = new ArrayList<>();

    List<String> imglist = new ArrayList<>();
    SocialModel model;
    InvokeParam invokeParam;
    public DialogHelper dialogHelper;
    public UploadPicEntity result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopalbum);
        ButterKnife.inject(this);
        model = new SocialModel(this);
        myHandler = new MyHandler(this);
        initView();
        initDate();
    }


    private void initView() {
        titleValue.setText("店铺相册");
        tvAdd.setVisibility(View.VISIBLE);
        tvAdd.setText("保存");
    }

    private void initDate() {
        takePhoto = getTakePhoto();
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(false);
        builder.setCorrectImage(false);
        takePhoto.setTakePhotoOptions(builder.create());
        initPermission();

        adapter = new ShopalbumAdapter(urllist, ShopalbumActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(ShopalbumActivity.this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerAlbum.setLayoutManager(layoutManager);
        recyclerAlbum.setAdapter(adapter);

        getShopImgs();
    }

    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    private void initPermission() {
        // 申请权限。
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .send();
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeSuccess(TResult result) {
        if (result != null) {
            String url = result.getImage().getOriginalPath();
            urllist.add(url);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePhoto.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        takePhoto.onSaveInstanceState(outState);
    }

    @OnClick({R.id.iv_back, R.id.tv_add,R.id.btn_savealbum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                if(urllist.size()>0){
                    uploadImage();
                }else {
                    ToastUtils.showToastShort("您未做修改");
                }
                break;
            case R.id.btn_savealbum:
                imageUri = getImageCropUri();
                takePhoto.onPickFromGallery();
                break;
        }
    }


    public void uploadImage() {
        try {
            if (dialogHelper != null) {
                dialogHelper.dismissProgressDialog(mActivity);
                dialogHelper = null;
            }
            dialogHelper = new DialogHelper();
            dialogHelper.showProgressDialog(mActivity, R.string.upload_pictures, false);
            for (int i = 0;i<urllist.size();i++) {

                final int finalI = i;
                HttpClientUtil.upload(new File(urllist.get(i)), "avatar_img", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("aaa","上传的图片sd卡路径onFailure：");
                        dialogHelper.dismissProgressDialog(mActivity);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        result = new Gson().fromJson(response.body().string(), UploadPicEntity.class);
                        imglist.add(result.getData().getUrl());
                        Log.e("aaa",result+"上传的图片sd卡路径onResponse：");
                        if(finalI ==urllist.size()-1){
                            myHandler.sendEmptyMessage(1);
                        }
                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastShort(R.string.clipping_error);
        }
    }


    public void getShopImgs(){
        model.getShopImgs(new IModelResult<ShopImgEntity>() {
            @Override
            public void OnSuccess(ShopImgEntity emptyEntity) {

            }

            @Override
            public void OnError(ApiException e) {

            }

            @Override
            public void AddSubscription(Subscription subscription) {
                addSubscription(subscription);
            }
        });
    }



    public void updateShopimgs(){
        String[] files = new String[imglist.size()];
        imglist.toArray(files);
        model.updateShopShopImgs(files, new IModelResult<EmptyEntity>() {
            @Override
            public void OnSuccess(EmptyEntity emptyEntity) {
                imglist.clear();
                ToastUtils.showToastShort(R.string.update_success);
            }

            @Override
            public void OnError(ApiException e) {

            }

            @Override
            public void AddSubscription(Subscription subscription) {
                addSubscription(subscription);
            }
        });
    }


    MyHandler myHandler;
    static class MyHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyHandler(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ShopalbumActivity avatarActivity = (ShopalbumActivity) activityReference.get();
            avatarActivity.dialogHelper.dismissProgressDialog(avatarActivity);
            switch (msg.what) {
                case 1:
                    String code = avatarActivity.result.getCode();
                    if (code.equals("1")) {
                        avatarActivity.updateShopimgs();
                    } else {
                        ToastUtils.showToastShort(R.string.upload_failed);
                    }
                    break;
                case 2:
                    ToastUtils.showToastShort(R.string.upload_failed);
                    break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }
}
