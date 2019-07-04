package com.arabbit.activity.shop;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.adapter.ShopalbumAdapter;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ShopalbumActivity extends TakePhotoActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopalbum);
        ButterKnife.inject(this);
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
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        if (result != null) {
            String url = result.getImage().getOriginalPath();
            urllist.add(url);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_add,R.id.btn_savealbum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                imageUri = getImageCropUri();

                break;
            case R.id.btn_savealbum:
                takePhoto.onPickFromGallery();
                break;
        }
    }

}
