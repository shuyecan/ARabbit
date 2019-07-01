package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GetCityListEntity;
import com.arabbit.utils.CommonUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AreaAdapter extends CustomBaseAdapter {
    Context mContext;
    List mList;

    public AreaAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_area, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GetCityListEntity entity = (GetCityListEntity) mList.get(position);
        String name = CommonUtils.formatNull(entity.getCity());
        viewHolder.tvArea.setText(name);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_area)
        TextView tvArea;
        @InjectView(R.id.tv_choice)
        TextView tvChoice;
        @InjectView(R.id.layout_location)
        LinearLayout layoutLocation;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
