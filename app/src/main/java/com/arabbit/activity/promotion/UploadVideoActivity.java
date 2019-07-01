package com.arabbit.activity.promotion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.activity.album.MyDialog;
import com.arabbit.activity.album.MyDialog.OnButtonClickListener;
import com.arabbit.activity.album.MyGridView;
import com.arabbit.activity.persional.LookAlbumActivity;
import com.arabbit.activity.persional.LookVideoListActivity;
import com.arabbit.activity.persional.PersonalAvatarActivity;
import com.arabbit.adapter.AlbumAdapter;
import com.arabbit.adapter.VideoAdapter;
import com.arabbit.bean.AlbumBean;
import com.arabbit.entity.GetUserInfoEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.request.UploadPicEntity;
import com.arabbit.utils.CacheActivity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DialogHelper;
import com.arabbit.utils.FileUtils;
import com.arabbit.utils.HttpClientUtil;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.SPUtils;
import com.arabbit.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Subscription;

public class UploadVideoActivity extends BaseActivity implements
        OnButtonClickListener, OnItemClickListener{

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
    private List<String> imgurls;

    SocialModel model;
    String user_id = "";
    private VideoAdapter albumAdapter;
    private String term_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upvideo);
        ButterKnife.inject(this);
        if (!CacheActivity.activityList.contains(UploadVideoActivity.this)) {
            CacheActivity.addActivity(UploadVideoActivity.this);
        }
        imgurls = new ArrayList<>();
        imgurls.add("add_pic");

        albumAdapter = new VideoAdapter(this,imgurls);
        gridList.setAdapter(albumAdapter);
        user_id = getIntent().getStringExtra("user_id");
        term_id = getIntent().getStringExtra("term_id");
        Log.e("aaa","列表用户id："+user_id+"，期id："+term_id);
        initTitle();
        model = new SocialModel(this);
        getUserInfo();
        upPic();

    }

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
//
//        }else{//非公司用户
//            tvAdd.setVisibility(View.GONE);
//        }
//        tvAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent upintent = new Intent(mActivity, LookVideoListActivity.class);
//                upintent.putExtra("term_id", term_id);
//                upintent.putExtra("user_id", user_id);
//                startActivity(upintent);
//            }
//        });

    }


    @Override
    protected void onDestroy() {
        if(dialogHelper != null){
            dialogHelper.dismissProgressDialog(mActivity);
            dialogHelper = null;
        }
        super.onDestroy();
        ButterKnife.reset(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {


            switch (requestCode) {
                case 2:
                    Uri data1 = data.getData();
//                    String path = data1.getPath();
                    String value = FileUtils.getPath(UploadVideoActivity.this, data1);

                    uploadImage(value);
                    Log.e("aaa","上出的视频路径："+value+",图片的数量："+imgurls.size());



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

    public DialogHelper dialogHelper;
    public void uploadImage(final String videopath) {
        try {
            if (dialogHelper != null) {
                dialogHelper.dismissProgressDialog(mActivity);
                dialogHelper = null;
            }
            dialogHelper = new DialogHelper();
            dialogHelper.showProgressDialog(mActivity, "正在上传中...", true);
            Log.e("aaa","上传的图片sd卡路径："+videopath);
            HttpClientUtil.upload(new File(videopath), "upload_video", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    dialogHelper.dismissProgressDialog(mActivity);
                    try {
                        Log.e("aaa","上传的图片sd卡路径onFailure："+videopath);
                        e.printStackTrace();
//                    myHandler.sendEmptyMessage(2);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    dialogHelper.dismissProgressDialog(mActivity);
                    UploadPicEntity result = new Gson().fromJson(response.body().string(), UploadPicEntity.class);
                    final String videourl = result.getData().getUrl();
                    Log.e("aaa",result+"上传的图片sd卡路径onResponse："+videourl);
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgurls.remove(imgurls.size()-1);
                                imgurls.add(videourl);
                                imgurls.add("add_pic");
                                albumAdapter = new VideoAdapter(UploadVideoActivity.this,imgurls);
                                gridList.setAdapter(albumAdapter);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//
// myHandler.sendEmptyMessage(1);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastShort(R.string.clipping_error);
        }
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }


    public void upPic() {
        gridList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemflag = (String)adapterView.getAdapter().getItem(i);
                Log.e("aaa","点击标志："+itemflag);
                if(TextUtils.equals(itemflag,"add_pic")){
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);     //限制持续时长
                    startActivityForResult(intent, 2);
                }else{//查看相册

                }
            }
        });

        tvUpxce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            uploadMultiFile();
//                ToastUtils.showToastShort("HUILAIBA LINX");

                if((imgurls.size())<=1){
                    ToastUtils.showToastShort("请先选择图片");
                    return;
                }
                String url = "";
                for (int i=0;i<(imgurls.size()-1);i++){
                    String s = imgurls.get(i);
                    if(!TextUtils.equals(s,"add_pic")){
                        url+=s;
                        if(i != (imgurls.size()-2)){
                            url+=";";
                        }

                    }

                }

                String currentuser_id = SPUtils.getString("user_id", "");
                Log.e("aaa","上出的user_id："+currentuser_id+",term_id："+term_id+",图片的地址："+url);
                model.uploadVideo(currentuser_id, term_id,url, new IModelResult<AlbumBean>() {
                    @Override
                    public void OnSuccess(AlbumBean listsBean) {
                        ToastUtils.showToastShort("上传成功");
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
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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