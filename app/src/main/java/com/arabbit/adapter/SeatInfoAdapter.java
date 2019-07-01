package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GetSeatListEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 */
public class SeatInfoAdapter extends CustomBaseAdapter {
    Context mContext;
    List< GetSeatListEntity.ListsBean> mList;

    public SeatInfoAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_gv_seatinfo, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetSeatListEntity.ListsBean entity = mList.get(position);
        String num = entity.getNo() + "";
        String name = entity.getNickname();
        holder.tvSeatInfoValue.setText(mContext.getString(R.string.number) + num + "\n" + name);
//        ImgLoaderUtils.setImageloader(url, holder.ivSeatInfoImage);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_seat_info_image)
        ImageView ivSeatInfoImage;
        @InjectView(R.id.tv_seat_info_value)
        TextView tvSeatInfoValue;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
