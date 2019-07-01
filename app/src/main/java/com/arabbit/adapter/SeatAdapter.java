package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.bean.GetTermListBean;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by net8 on 2017/7/27.
 */

public class SeatAdapter extends CustomBaseAdapter {
    Context mContext;
    List mList;

    public SeatAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_lv_seat, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetTermListBean bean = (GetTermListBean) mList.get(position);
        String termName = CommonUtils.formatNull(bean.getTerm_name());
        int isMain = CommonUtils.formatInt(bean.getIs_main());
        int select = CommonUtils.formatInt(bean.getSelect());
//        String create_time = CommonUtils.formatNull(bean.getCreate_time());
        String starttime = CommonUtils.formatNull(bean.getStarttime());
        String time = DateUtils.getSystemTime(starttime);
        holder.tvContent.setText(termName);
        holder.tvTime.setText(time);

        if (select == 0) {
            holder.ivChoice.setImageResource(R.mipmap.ico_danxuan_default);
        } else {
            holder.ivChoice.setImageResource(R.mipmap.ico_danxuan_selected);
        }
        if (isMain == 1) {
            holder.ivMain.setVisibility(View.VISIBLE);
        } else {
            holder.ivMain.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_main)
        ImageView ivMain;
        @InjectView(R.id.iv_choice)
        ImageView ivChoice;
        @InjectView(R.id.tv_content)
        TextView tvContent;
        @InjectView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
