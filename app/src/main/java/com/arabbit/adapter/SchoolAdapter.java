package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.ApplistEntity;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 */
public class SchoolAdapter extends CustomBaseAdapter {
    Context mContext;
    List mList;

    public SchoolAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_gv_school, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ApplistEntity applistEntity = (ApplistEntity) mList.get(position);
        String str = applistEntity.getAppName();
        holder.tvValue.setText(str);
        Glide.with(mContext).load(applistEntity.getUrlId()).into(holder.iv_image);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_value)
        TextView tvValue;
        @InjectView(R.id.iv_image)
        ImageView iv_image;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
