package com.arabbit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arabbit.R;
import com.arabbit.bean.AlbumBean;
import com.arabbit.model.Config;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LookAlbumAdapter extends CustomBaseAdapter {
    Context mContext;
    List mList;

    public LookAlbumAdapter(Context context, List list) {
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

        ImgLoaderUtils.setImageloader(Config.IMG_URL + imgurl, viewHolder.iv_img);
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
