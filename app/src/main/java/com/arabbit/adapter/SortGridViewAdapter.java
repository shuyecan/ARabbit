package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GetSchoolListEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by net8 on 2017/7/27.
 */

public class SortGridViewAdapter extends CustomBaseAdapter {
    Context mContext;
    List<GetSchoolListEntity> mList;

    public SortGridViewAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_one_textl, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetSchoolListEntity entity = mList.get(position);
        String name = entity.getSchool_name();
        holder.tvValue.setText(name);
//        GetShopCategoryEntity entity = mList.get(position);
//        String image = entity.getUrl();
//        String name = entity.getName();
//        ImgLoaderUtils.setImageloader(image, holder.item_image);
//        holder.text.setText(name);

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_value)
        TextView tvValue;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
