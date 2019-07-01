package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arabbit.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by net8 on 2017/7/27.
 */
public class CityAdapter extends CustomBaseAdapter {
    Context mContext;
    List mList;

    public CityAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_city, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        GetShopCategoryEntity entity = mList.get(position);
//        String image = entity.getUrl();
//        String name = entity.getName();
//        ImgLoaderUtils.setImageloader(image, viewHolder.item_image);
//        viewHolder.text.setText(name);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_area)
        TextView tvArea;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
