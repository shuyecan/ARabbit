package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GetFloorListEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 */
public class FloorAdapter extends CustomBaseAdapter {
    Context mContext;
    List<GetFloorListEntity> mList;

    public FloorAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_gv_floor, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetFloorListEntity entity = mList.get(position);
        String floor_name = entity.getFloor_name();
        holder.tvFloorValue.setText(floor_name);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_floor_value)
        TextView tvFloorValue;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
