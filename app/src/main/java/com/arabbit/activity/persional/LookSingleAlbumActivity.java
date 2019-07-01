package com.arabbit.activity.persional;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.activity.BaseActivity;
import com.arabbit.adapter.LookAlbumAdapter;
import com.arabbit.bean.AlbumBean;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.model.SocialModel;
import com.arabbit.net.ApiException;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.previewlibrary.ZoomMediaLoader;
import com.previewlibrary.loader.IZoomMediaLoader;
import com.previewlibrary.loader.MySimpleTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Subscription;
import uk.co.senab.photoview.PhotoViewAttacher;


public class LookSingleAlbumActivity extends BaseActivity {
//    @InjectView(R.id.iv_img)
//    ImageView iv_img;
//    @InjectView(R.id.gridView)
//    GridView gridList;
//    SocialModel model;
//    String user_id = "";
//    private String term_id = "";
//private PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_album_single);
        ButterKnife.inject(this);
//        String imgurl = getIntent().getStringExtra("imgurl");
//        ArrayList<String> imglists = getIntent().getStringArrayListExtra("imglists");

//        ImgLoaderUtils.setImageloader(Config.IMG_URL + imgurl, iv_img);
//        term_id = getIntent().getStringExtra("term_id");
//        Log.e("aaa","上出的user_id："+user_id+",term_id："+term_id);
//        model = new SocialModel(this);
//        ButterKnife.inject(this);
//        titleValue.setText("查看相册");
//        getAlbumList();
//        ViewPager viewPager = (ViewPager)findViewById(R.id.bannerViewPager);
//        if(imglists != null && imglists.size()>0){
//            MyAdpter myAdpter = new MyAdpter(imglists);
//            viewPager.setAdapter(myAdpter);
//            int pos = getIntent().getIntExtra("pos", 0);
//            viewPager.setCurrentItem(pos);
//        }


    }
//    class MyAdpter extends PagerAdapter{
//        ArrayList<String> imglists;
//        public MyAdpter(ArrayList<String> imglists){
//                this.imglists = imglists;
//        }
//
//        @Override
//        public int getCount() {
//            return imglists.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            ImageView ivimg = null;
//            try {
//                String s = imglists.get(position);
//                ivimg = new ImageView(LookSingleAlbumActivity.this);
//                PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(ivimg);
//                photoViewAttacher.setScaleType(ImageView.ScaleType.FIT_XY);
//                ImgLoaderUtils.setImageloader(Config.IMG_URL + s, ivimg);
////                ivimg.setScaleType(ImageView.ScaleType.FIT_XY);
//                container.addView(ivimg);
//            } catch (Exception e) {
//                e.printStackTrace();
//                ivimg = new ImageView(LookSingleAlbumActivity.this);
//            }
//            return ivimg;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            try {
//                container.removeView((ImageView)object);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
//    @OnClick({R.id.iv_back})
//    public void onClick(View view) {
//
//        switch (view.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//        }
//    }
//    private void getAlbumList() {
//        model.getAlbumList(user_id, term_id, new IModelResult<List<AlbumBean>>() {
//            @Override
//            public void OnSuccess(List<AlbumBean> albumBean) {
//                if(albumBean != null && albumBean.size()>0){
//                    setList(albumBean);
//                }else{
//                    ToastUtils.showToastShort("暂无数据");
//                }
//
//            }
//
//            @Override
//            public void OnError(ApiException e) {
//                ToastUtils.showToastShort(""+e.message);
//            }
//
//            @Override
//            public void AddSubscription(Subscription subscription) {
//                addSubscription(subscription);
//            }
//        });
//
//
//    }
//
//    private void setList(List<AlbumBean> albumBean) {
//
//        LookAlbumAdapter lookAdapter = new LookAlbumAdapter(this,albumBean);
//        gridList.setAdapter(lookAdapter);
//        gridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//
//    }


}
