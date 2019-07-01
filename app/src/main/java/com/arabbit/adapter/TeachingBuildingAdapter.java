package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GetBuildingListEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * GetBuildingListEntity
 */
public class TeachingBuildingAdapter extends CustomBaseAdapter {
    Context mContext;
    List<GetBuildingListEntity> mList;

    public TeachingBuildingAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_gv_teaching_building, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        GetBuildingListEntity entity = mList.get(position);
        String type = entity.getType();
        if (type.equals(R.string.build_room)) {
            holder.ivImage.setImageResource(R.mipmap.btn_jiaoshi);
        } else if (type.equals(R.string.build_floor)) {
            holder.ivImage.setImageResource(R.mipmap.btn_louceng);
        } else
//        if (type.equals("教学楼") || type.equals("大礼堂"))
        {
            holder.ivImage.setImageResource(R.mipmap.btn_jiaoxuelou);
        }
        String str = entity.getBuilding_name();
        holder.tvBuildingValue.setText(str);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_image)
        ImageView ivImage;
        @InjectView(R.id.tv_building_value)
        TextView tvBuildingValue;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
