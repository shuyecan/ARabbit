package com.arabbit.activity.persional;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.entity.EmptyEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.request.UploadPicEntity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.DialogHelper;
import com.arabbit.utils.FileUtils;
import com.arabbit.utils.HttpClientUtil;
import com.arabbit.utils.ImageUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.PhotoUtils;
import com.arabbit.utils.SystemUtil;
import com.arabbit.utils.ToastUtils;
import com.arabbit.view.dialog.DialogYesOrNoUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Subscription;


public class PersonalAvatarActivity extends BaseActivity {

    @InjectView(R.id.iv_content)
    ImageView ivContent;
    @InjectView(R.id.tv_title)
    TextView tvTitle;

    SocialModel model;
    String urlImage = "";
    String type = "";
    //图片用处:avatar_img 普通头像,seat_img: 座位头像  register:当前为注册，只上传图片，不修改资料
    boolean isChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_avatar);
        ButterKnife.inject(this);
        String title = getIntent().getStringExtra("title");
        if(!TextUtils.equals(title,null)){
            tvTitle.setText(title);
        }else{
            tvTitle.setText(R.string.select_avatar);
        }

        urlImage = getIntent().getStringExtra("url");
        type = getIntent().getStringExtra("type");
        model = new SocialModel(this);
        myHandler = new MyHandler(this);
        if (!CommonUtils.isNull(urlImage)) {
            if (type.equals("register")) {
                ImgLoaderUtils.setImageloader("file://" + urlImage, ivContent);
            } else {
                ImgLoaderUtils.setImageloader(Config.IMG_URL + urlImage, ivContent);
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

    @OnClick({R.id.iv_back, R.id.iv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_menu:
                show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isChange) {
            final DialogYesOrNoUtil dialogYesOrNoUtil = new DialogYesOrNoUtil(this);
            dialogYesOrNoUtil.setDialogTitle(getString(R.string.tips));
            dialogYesOrNoUtil.setContent(getString(R.string.is_save));
            dialogYesOrNoUtil.setNoText(getString(R.string.cancel), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogYesOrNoUtil.dismiss();
                    mActivity.finish();
                }

            });
            dialogYesOrNoUtil.setYesText(getString(R.string.confirm), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePhotoAndSumbit();
                    dialogYesOrNoUtil.dismiss();
                }
            });
            dialogYesOrNoUtil.show();
        } else {
            finish();
        }
    }

    private Dialog dialog;

    public void show() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View view = View.inflate(this, R.layout.layout_camera_bottom_dialog, null);
        //初始化控件
        TextView ItemOne = (TextView) view.findViewById(R.id.tv_item_one);
        TextView ItemTwo = (TextView) view.findViewById(R.id.tv_item_two);
        TextView ItemThree = (TextView) view.findViewById(R.id.tv_item_three);
        TextView ItemCancel = (TextView) view.findViewById(R.id.tv_cancel);
        ItemOne.setOnClickListener(new DialogItemOnClick());
        ItemTwo.setOnClickListener(new DialogItemOnClick());
        ItemThree.setOnClickListener(new DialogItemOnClick());
        ItemCancel.setOnClickListener(new DialogItemOnClick());
        //将布局设置给Dialog
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();//显示对话框
    }

    public String getNewPhotoPath() {
        String p = Environment.getExternalStorageDirectory().getPath();
        File file = FileUtils.createDirectory(p, "ARabbit");
        File fileImage = FileUtils.createDirectory(file.getAbsolutePath(), "ImageSave");
        String path = fileImage.getAbsolutePath() + "/";
        String fileNo = DateUtils.getSystemTime();
        String fileName = "P" + fileNo + ".jpg";
        String newPath = path + fileName;
        return newPath;
    }


    class DialogItemOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_item_one:
//                    Intent TakePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    mActivity.startActivityForResult(TakePic, TAKE_PHOTO_REQUEST);
//                    ActivityUtils.jump2Next(mActivity);
//                    ToastUtils.showToastShort("拍照");
                    urlImage = getNewPhotoPath();
                    fileUri = new File(urlImage);
                    autoObtainCameraPermission();

                    break;
                case R.id.tv_item_two:
//                    Intent SelectPhoto = new Intent("android.intent.action.GET_CONTENT");//从相册/文件管理中选取图片
//                    SelectPhoto.setType("image/*");//相片类型
//                    mActivity.startActivityForResult(SelectPhoto, CHOOSE_PICTURE);
//                    ActivityUtils.jump2Next(mActivity);

                    autoObtainStoragePermission();

                    break;
                case R.id.tv_item_three:
                    savePhotoAndSumbit();

                    break;
                case R.id.tv_cancel:
                    ToastUtils.showToastShort(R.string.cancel);
                    break;
            }
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }


    public void savePhotoAndSumbit() {
        if (!isChange) {
            ToastUtils.showToastShort(R.string.please_update_pictures);
            return;
        }
        urlImage = getNewPhotoPath();
        ivContent.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(ivContent.getDrawingCache());
        ivContent.setDrawingCacheEnabled(false);

        try {
            ImageUtils.ratioAndGenThumb(bitmap, urlImage, bitmap.getWidth(), bitmap.getHeight());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SystemUtil.refreshGallery(mActivity, urlImage);

        if (type.equals("register")) {
            Intent intent = new Intent();
            intent.putExtra("value", urlImage);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
        uploadImage();
    }

    public void uploadImage() {
        try {
            if (dialogHelper != null) {
                dialogHelper.dismissProgressDialog(mActivity);
                dialogHelper = null;
            }
            dialogHelper = new DialogHelper();
            dialogHelper.showProgressDialog(mActivity, R.string.upload_pictures, false);
            Log.e("aaa","上传的图片sd卡路径："+urlImage);
            HttpClientUtil.upload(new File(urlImage), type, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("aaa","上传的图片sd卡路径onFailure："+urlImage);
                    myHandler.sendEmptyMessage(2);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    result = new Gson().fromJson(response.body().string(), UploadPicEntity.class);
                    Log.e("aaa",result+"上传的图片sd卡路径onResponse："+urlImage);
                    FileUtils.deleteFile(urlImage);
                    myHandler.sendEmptyMessage(1);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastShort(R.string.clipping_error);
        }
    }


    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.showToastShort(R.string.refused_one);
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(mActivity, "com.arabbit.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showToastShort(R.string.not_sd);
            }
        }
    }


    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST,type);
        }

    }

    private File fileUri;
    private Uri imageUri;

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    public DialogHelper dialogHelper;

    public UploadPicEntity result;

    MyHandler myHandler;

    static class MyHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyHandler(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PersonalAvatarActivity avatarActivity = (PersonalAvatarActivity) activityReference.get();
            avatarActivity.dialogHelper.dismissProgressDialog(avatarActivity);
            switch (msg.what) {
                case 1:
                    String code = avatarActivity.result.getCode();
                    if (code.equals("1")) {
                        avatarActivity.urlImage = avatarActivity.result.getData().getUrl();
                        if (avatarActivity.type.equals("avatar_img")) {
                            //图片用处:avatar_img普通头像,seat_img:座位头像
                            avatarActivity.updateUserInfoAvatarImg();
                        } else if (avatarActivity.type.equals("seat_img")) {
                            avatarActivity.updateUserInfoSeatImg();
                        }else if (avatarActivity.type.equals("upload_img")) {
                            Intent intent = new Intent();
                            intent.putExtra("value", avatarActivity.urlImage);
                            avatarActivity.setResult(RESULT_OK, intent);
                            avatarActivity.finish();
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("value", avatarActivity.urlImage);
                            avatarActivity.setResult(RESULT_OK, intent);
                            avatarActivity.finish();
                        }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CODE_CAMERA_REQUEST:
                if (resultCode == RESULT_CANCELED) {
                    ToastUtils.showToastShort(R.string.cancelled_photo);
                    return;
                }
                imageUri = Uri.fromFile(fileUri);
                ImgLoaderUtils.setImageloader(imageUri + "", ivContent);
                isChange = true;
                break;
            case CODE_GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    //照片的原始资源地址
                    isChange = true;
                    if(TextUtils.equals(type,"avatar_img")){

                        startPhotoZoom(data.getData());
                    }else{
                        try {
                            String originalUri = data.getDataString();
                            //使用ContentProvider通过URI获取原始图片
                            ImgLoaderUtils.setImageloader(originalUri, ivContent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    break;
                }
                break;
            case 5://裁剪过后的回调
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    ivContent.setImageBitmap(photo);
                }
                //ImgLoaderUtils.setImageloader(originalUri, ivContent);
                break;
        }
    }
    public void startPhotoZoom(Uri uri) {
        Log.e("aaa","图片uri："+uri);
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(mActivity, "com.arabbit.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showToastShort(R.string.not_sd);
                    }
                } else {

                    ToastUtils.showToastShort(R.string.please_turn_camera);
                }
                break;

            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST,type);
                } else {
                    ToastUtils.showToastShort(R.string.please_turn_sd);
                }
                break;

        }
    }


    public void updateUserInfoAvatarImg() {
        try {
            model.updateUserInfoAvatarImg(urlImage, new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity loginEntity) {
                    ToastUtils.showToastShort(R.string.update_success);
                    Intent intent = new Intent();
                    intent.putExtra("value", urlImage);
                    setResult(RESULT_OK, intent);
                    finish();

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


    public void updateUserInfoSeatImg() {
        try {
            model.updateUserInfoSeatImg(urlImage, new IModelResult<EmptyEntity>() {
                @Override
                public void OnSuccess(EmptyEntity loginEntity) {
                    ToastUtils.showToastShort(R.string.update_success);
                    Intent intent = new Intent();
                    intent.putExtra("value", urlImage);
                    setResult(RESULT_OK, intent);
                    finish();

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

}
