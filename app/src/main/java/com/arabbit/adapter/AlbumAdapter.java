package com.arabbit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GetCityListEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AlbumAdapter extends CustomBaseAdapter {
    Context mContext;
    List mList;

    public AlbumAdapter(Context context, List list) {
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

        String imgurl =  (String) mList.get(position);
//        String imgurl = imgage.getPath();
//
        if(TextUtils.equals(imgurl,"add_pic")){
            viewHolder.iv_img.setImageResource(R.mipmap.gridview_addpic);
        }else{
            ImgLoaderUtils.setImageloader(Config.IMG_URL + imgurl, viewHolder.iv_img);
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
