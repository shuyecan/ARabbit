package com.arabbit.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.arabbit.utils.FileUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;


/**
 */
public class BaseApplication extends Application {

    private static int mainThreadId;
    private static Handler mainHandler;

    private static BaseApplication application;

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }

    public static BaseApplication getApplication() {
        return application;
    }

    public static int Height, Width;

    //高德地图显示精度（数值越大，越精细）(貌似最多到19)
    public static final int ZOOMTOSIZE = 17;
    //高德地图--定位间隔
    public static final int INTERVAL = 5000;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        // 这里拿的一定是主线程id
        mainThreadId = android.os.Process.myTid();
        // 这里拿的一定是主线程的handler
        mainHandler = new Handler();
        getScreen(this);
        MultiDex.install(this);

        //初始化图片下载的框架：
        initImageLoader(getApplicationContext());

    }


    private void initImageLoader(Context context) {
        //File cacheDir = context.getExternalCacheDir();
        //自定义缓存图片的路径：
        File cacheDir = new File(FileUtils.getImgCacheDir());
        //Toast.makeText(context, cacheDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800)// max width, max height，即保存的每个缓存文件的最大长宽
                // 设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(5) // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024))// 你可以通过自己的内存缓存实现
                .memoryCacheSize(4 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())// 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100)// 缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                .build();// 开始构建
        ImageLoader.getInstance().init(configuration);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        Height = dm.heightPixels;
        Width = dm.widthPixels;
    }



}
