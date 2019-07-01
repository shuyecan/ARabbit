package com.arabbit.activity.promotion;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.GridView;
import android.view.Window;
import android.view.WindowManager;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.io.ByteArrayOutputStream;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.util.List;


import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.album.MyGridView;
import com.arabbit.activity.persional.LookAlbumActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.adapter.AlbumAdapter;
import com.arabbit.bean.AlbumBean;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.entity.UserHadprizeEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.request.UploadPicEntity;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.DialogHelper;
import com.arabbit.utils.FileUtils;
import com.arabbit.utils.HttpClientUtil;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.OkHttp3Utils;
import com.arabbit.utils.PhotoUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.arabbit.activity.album.MyDialog;
import com.arabbit.activity.album.MyDialog.OnButtonClickListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UpxceActivity extends BaseActivity implements
        OnButtonClickListener, OnItemClickListener{

    private static final int REQUEST_PICK = 789;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
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
    @InjectView(R.id.tv_upxce)
    TextView tvUpxce;
    @InjectView(R.id.gridView)
    MyGridView gridList;
    private ArrayList<String> imgurls;
//    private ArrayList<File> uploadimgurls;

    SocialModel model;
    String user_id = "";
    private AlbumAdapter albumAdapter;
    private String term_id = "";
    private List<File> fileimgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upxce);
        ButterKnife.inject(this);
        if (!CacheActivity.activityList.contains(UpxceActivity.this)) {
            CacheActivity.addActivity(UpxceActivity.this);
        }
        fileimgs = new ArrayList<>();
        imgurls = new ArrayList<>();
//        uploadimgurls = new ArrayList<>();
//        Image image = new Image();
//        image.setPath("add_pic");
        imgurls.add("add_pic");



        albumAdapter = new AlbumAdapter(this,imgurls);
        gridList.setAdapter(albumAdapter);
        user_id = getIntent().getStringExtra("user_id");
        term_id = getIntent().getStringExtra("term_id");
        Log.e("aaa","列表用户id："+user_id+"，期id："+term_id);
        initTitle();
        model = new SocialModel(this);
        getUserInfo();
        upPic();

    }
//    private class MyImageLoader implements ImagePicker.ImageLoader {
//        public void displayImage(ImageView imageView, Image image) {
//            Glide.with(imageView.getContext())
//                    .load(image.getPath())
//                    .dontAnimate()
//                    .placeholder(R.drawable.ic_camera)
//                    .into(imageView);
//        }
//    }


    public void getUserInfo() {
        String user_id = SPUtils.getString("user_id", "");
        this.user_id = user_id;
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
        titleValue.setText(R.string.perional_data);
//        String role = SPUtils.getString("role", "");
//        if(TextUtils.equals(role,"1")){//公司用户
//            String currentuser_id = SPUtils.getString("user_id", "");
//            if(TextUtils.equals(currentuser_id,user_id)){
//                tvAdd.setVisibility(View.VISIBLE);
//                tvAdd.setText("编辑");
//            }else{
//                tvAdd.setVisibility(View.GONE);
//            }
//        }else{//非公司用户
//            tvAdd.setVisibility(View.GONE);
//        }
//        tvAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent upintent = new Intent(mActivity, LookAlbumActivity.class);
//                upintent.putExtra("term_id", term_id);
//                upintent.putExtra("user_id", user_id);
//                startActivity(upintent);
//            }
//        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
    List<Uri> mSelected;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {


            switch (requestCode) {
                case CODE_CAMERA_REQUEST:
                    if (resultCode == RESULT_CANCELED) {
                        ToastUtils.showToastShort(R.string.cancelled_photo);
                        return;
                    }
                    fileimgs.clear();
                    imageUri = Uri.fromFile(fileUri);
                    String path1 = FileUtils.getPath(UpxceActivity.this, imageUri);
                    File file1 = new File(path1);
                    fileimgs.add(file1);
                    uploadImage();
                    break;
                case REQUEST_PICK:
                    fileimgs.clear();
                    mSelected = Matisse.obtainResult(data);
                    if(mSelected!=null && mSelected.size()>0){
//                        Log.e("aaa","本次选中了图片数量："+mSelected.size());


                        if (dialogHelper != null) {
                            dialogHelper.dismissProgressDialog(mActivity);
                            dialogHelper = null;
                        }
                        dialogHelper = new DialogHelper();
                        dialogHelper.showProgressDialog(mActivity, R.string.upload_pictures, false);
                        for (int i=0;i<mSelected.size();i++){
                            Uri uri = mSelected.get(i);
                            String path = FileUtils.getPath(UpxceActivity.this, uri);
                            if(!TextUtils.equals(path,"add_pic")){

                                File file = new File(path);
                                fileimgs.add(file);

                            }

                        }
                        uploadImage();

                    }

//                    ArrayList<Image> imageList = data.getParcelableArrayListExtra(ImagePicker.EXTRA_IMAGE_LIST);
//                    if(imageList != null && imgurls.size()>0){
//                        imgurls.remove(imgurls.size()-1);
//                        imgurls.addAll(imageList);
//                        Image image = new Image();
//                        image.setPath("add_pic");
//                        imgurls.add(image);
//                        albumAdapter = new AlbumAdapter(this,imgurls);
//                        gridList.setAdapter(albumAdapter);
//                    }
//
//





                    break;
                default:

                    GetUserInfoEntity entity = (GetUserInfoEntity) data.getSerializableExtra("userInfo");
                    if (CommonUtils.isNull(entity)) {
                        return;
                    }
                    initUserInfo(entity);
                    break;

            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }
    private Dialog dialogs;

    public void show() {
        dialogs = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View view = View.inflate(this, R.layout.layout_upxce_dialog, null);
        //初始化控件
        TextView ItemOne = (TextView) view.findViewById(R.id.tv_item_one);
        TextView ItemTwo = (TextView) view.findViewById(R.id.tv_item_two);
        TextView ItemCancel = (TextView) view.findViewById(R.id.tv_cancel);
        ItemOne.setOnClickListener(new DialogItemOnClick());
        ItemTwo.setOnClickListener(new DialogItemOnClick());
        ItemCancel.setOnClickListener(new DialogItemOnClick());
        //将布局设置给Dialog
        dialogs.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialogs.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogs.show();//显示对话框
    }
    String urlImage = "";
    private File fileUri;
    private Uri imageUri;
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
    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
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
                    dialogs.dismiss();
                    urlImage = getNewPhotoPath();
                    fileUri = new File(urlImage);
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            imageUri = FileProvider.getUriForFile(mActivity, "com.arabbit.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        }

                        PhotoUtils.takePicture(UpxceActivity.this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.showToastShort(R.string.not_sd);
                    }

                    break;
                case R.id.tv_item_two:
                    dialogs.dismiss();
                    Matisse.from(UpxceActivity.this)
                            .choose(MimeType.allOf()) // 选择 mime 的类型
                            .countable(true)
                            .maxSelectable(8) // 图片选择的最多数量
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f) // 缩略图的比例
                            .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                            .forResult(REQUEST_PICK); // 设置作为标记的请求码
                    break;

                case R.id.tv_cancel:
                    dialogs.dismiss();
//                    ToastUtils.showToastShort(R.string.cancel);
                    break;
            }
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    public void upPic() {
        gridList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemflag = (String)adapterView.getAdapter().getItem(i);
//                Log.e("aaa","点击标志："+itemflag);
                if(TextUtils.equals(itemflag,"add_pic")){

                    if(imgurls.size()>=9){
                        ToastUtils.showToastShort("一次最多允许上传九张图片");
                        return;
                    }
                    show();


//                    new ImagePicker()
//                            .mode(ImagePicker.MODE_MULTI_SELECT)
//                            .imageLoader(new MyImageLoader())
//                            .selectLimit(8)//最多选择图片数量
//                            .requestCode(REQUEST_PICK)
//                            .start(UpxceActivity.this);
//                    Intent intent = new Intent(UpxceActivity.this, PersonalAvatarActivity.class);
//                    intent.putExtra("type","upload_img");
//                    startActivityForResult(intent, 2);
                }else{//查看相册

                }
            }
        });

        tvUpxce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            uploadMultiFile();
//                ToastUtils.showToastShort("HUILAIBA LINX");

//                if((imgurls.size())<=1){
//                    ToastUtils.showToastShort("请先选择图片");
//                    return;
//                }
                String url = "";
                for (int i=0;i<(imgurls.size()-1);i++){
                    String s = imgurls.get(i);
//                    String s = imgae.getPath();
                    if(!TextUtils.equals(s,"add_pic")){
                        url+=s;
                        if(i != (imgurls.size()-2)){
                            url+=";";
                        }


                    }

                }
                if(TextUtils.isEmpty(url)){
                    ToastUtils.showToastShort("请先选择图片");
                    return;
                }

                String currentuser_id = SPUtils.getString("user_id", "");
//                Log.e("aaa","上出的user_id："+currentuser_id+",term_id："+term_id+",图片的地址："+url);
                model.uploadToAlbum(currentuser_id, term_id,url, new IModelResult<AlbumBean>() {
                    @Override
                    public void OnSuccess(AlbumBean listsBean) {
                        ToastUtils.showToastShort("上传成功");
                        setResult(3);
                        finish();
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
        });

    }

    private MyDialog dialog;// 图片选择对话框
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private GridView gridView; // 网格显示缩略图
    private final int IMAGE_OPEN = 4; // 打开图片标记
    private String pathImage; // 选择图片路径
    private Bitmap bmp; // 导入临时图片
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter; // 适配器
    public DialogHelper dialogHelper;
    public UploadPicEntity result;
    public List<String> uploadurls;


    public void uploadImage() {
        uploadurls = new ArrayList<String>();
        try {

            OkHttp3Utils.getInstance().sendMultipart(fileimgs)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            //totalimg++;
                            Log.e("aaa", "一次上传多张图片测试上传完成");
                            if (dialogHelper != null) {
                                dialogHelper.dismissProgressDialog(mActivity);
                                dialogHelper = null;
                            }
                            imgurls.remove(imgurls.size()-1);
                            imgurls.addAll(uploadurls);
                            imgurls.add("add_pic");
                            albumAdapter = new AlbumAdapter(UpxceActivity.this,imgurls);
                            gridList.setAdapter(albumAdapter);

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e("aaa", "一次上传多张图片测试上传失败throwable:" + throwable.toString());
                        }

                        @Override
                        public void onNext(String s) {
                            try {
                                JSONObject object = new JSONObject(s.toString());
                                JSONArray jsonarray = object.getJSONArray("data");
//                                JSONArray jsonarray = new JSONArray();
                                for (int i=0;i<jsonarray.length();i++){
                                    String o = (String) jsonarray.get(i);
                                    uploadurls.add(o);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            Log.e("aaa", "一次上传多张图片测试返回的链接s:" + s);
                        }
                    });

//            Log.e("aaa","上传的图片sd卡路径："+urlImage);
//            HttpClientUtil.upload(new File(urlImage), "upload_img", new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Log.e("aaa","上传的图片sd卡路径onFailure："+urlImage);
//                   // myHandler.sendEmptyMessage(2);
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//
//                    result = new Gson().fromJson(response.body().string(), UploadPicEntity.class);
//                    Log.e("aaa",result+"上传的图片sd卡路径onResponse："+urlImage);
//                    FileUtils.deleteFile(urlImage);
//                   // myHandler.sendEmptyMessage(1);
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastShort(R.string.clipping_error);
        }
    }
    @Override
    public void camera() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), "temp.jpg")));
        startActivityForResult(intent, PHOTOHRAPH);
    }

    @Override
    public void gallery() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_OPEN);

    }

    @Override
    public void cancel() {
        // TODO Auto-generated method stub
        dialog.cancel();
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        // TODO Auto-generated method stub
        if (imageItem.size() == 10) { // 第一张为默认图片
//            Toast.makeText(mActivity.this, "图片数9张已满",
//                    Toast.LENGTH_SHORT).show();
        } else if (position == 0) { // 点击图片位置为+ 0对应0张图片
            // 选择图片
            dialog.show();

            // 通过onResume()刷新数据
        } else {
//            dialog(position);
        }

    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 64);
        intent.putExtra("outputY", 64);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }

}