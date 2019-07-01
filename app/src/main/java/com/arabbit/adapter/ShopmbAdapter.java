package com.arabbit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.ShopmbListEntity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/11/4.
 */

public class ShopmbAdapter extends BaseAdapter {

    Context mContext;
    List mList;
    private final SimpleDateFormat simpledateformat;


    public ShopmbAdapter(Context context, List list) {
//        super(context, list);
        this.mContext = context;
        this.mList = list;
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    public int getCount() {
        return mList == null ? 0 :mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }



    public View getView(final int position, View view, ViewGroup arg2) {
        ShopmbAdapter.ViewHolder  holder= null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_mbsales, null);
            holder = new  ShopmbAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ShopmbAdapter.ViewHolder) view.getTag();
        }

        ShopmbListEntity.ListsBean entity = (ShopmbListEntity.ListsBean) mList.get(position);
        String title = CommonUtils.formatNull(entity.getTitle());
        String mbfar = CommonUtils.formatNull(entity.getFar());
        String starttime = CommonUtils.formatNull(entity.getStarttime());
        String endtime = CommonUtils.formatNull(entity.getEndtime());
        String vposition = CommonUtils.formatNull(position+1);

        holder.tvCount.setText(vposition);
        holder.tvTitle.setText(title);
        holder.tvFar.setText(mbfar);
        if(!TextUtils.isEmpty(starttime)){
            String startformat = simpledateformat.format(new Date(Long.parseLong(starttime)));
            holder.tvStart_time.setText(startformat);
        }else{
            holder.tvStart_time.setText("");
        }

        if(!TextUtils.isEmpty(endtime)){
            String endformat = simpledateformat.format(new Date(Long.parseLong(endtime)));
            holder.tvEnd_time.setText(endformat);
        }else{
            holder.tvEnd_time.setText("");
        }


        return view;
    }

    static class ViewHolder {

        @InjectView(R.id.count)
        TextView tvCount;
        @InjectView(R.id.sales_title)
        TextView tvTitle;
        @InjectView(R.id.far)
        TextView tvFar;
        @InjectView(R.id.start_time)
        TextView tvStart_time;
        @InjectView(R.id.end_time)
        TextView tvEnd_time;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
