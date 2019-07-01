package com.arabbit.utils;

import android.widget.ImageView;

import com.arabbit.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by net8 on 2016/6/28.
 */
public class ImgLoaderUtils {

    private static DisplayImageOptions options;
    private static ImageLoader imageloader;

    static {
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.loading)//加载过程中的图片资源
                .showImageForEmptyUri(R.mipmap.ic_error_page)//加载为空时的图片资源
                .showImageOnFail(R.mipmap.ic_error_page)//加载失败时的图片资源
                .cacheInMemory(true)//是否进行内存缓存
                .cacheOnDisk(true)//是否进行磁盘缓存
                .displayer(new FadeInBitmapDisplayer(0))//配置图片显示器，本例为淡入式显示
                .build();
        imageloader = ImageLoader.getInstance();
    }

    public static void setImageloader(String uri, ImageView img) {
        imageloader.displayImage(uri, img, options);
    }

    /**
     * 加载圆型图
     */
    public static void displayCircle(ImageView imageView, Object imageUrl) {
        if (imageView == null || imageUrl == null)
            return;
        Glide
                .with(imageView.getContext())
                .load(imageUrl)
                .transform(new GlideCircleTransform(imageView.getContext()))
                /*.error(R.mipmap.glide_img)
                .placeholder(R.mipmap.red_log)*/
                .listener(new RequestListener<Object, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }

}
