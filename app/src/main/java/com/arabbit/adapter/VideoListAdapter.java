package com.arabbit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arabbit.R;
import com.arabbit.bean.AlbumBean;
import com.arabbit.model.Config;
import com.arabbit.utils.FileUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VideoListAdapter extends CustomBaseAdapter {
    Context mContext;
    List mList;

    public VideoListAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_album, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AlbumBean albumBean =  (AlbumBean) mList.get(position);
        String imgurl = albumBean.getUrl();
        if(TextUtils.equals(imgurl,"add_pic")){
            viewHolder.iv_img.setImageResource(R.mipmap.gridview_addpic);
        }else{
            Bitmap videoThumb = null;
            String videourl = Config.IMG_URL + imgurl;
            Log.e("aaa","视频路径;"+videourl);
            try {
                videoThumb = FileUtils.getNetVideoBitmap(videourl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(videoThumb != null){
                viewHolder.iv_img.setImageBitmap(videoThumb);
            }else{
                ImgLoaderUtils.setImageloader(Config.IMG_URL + imgurl, viewHolder.iv_img);
            }

        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_img)
        ImageView iv_img;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
