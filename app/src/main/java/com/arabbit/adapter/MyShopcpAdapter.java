package com.arabbit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.HadShopcpEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/11/18.
 */

public class MyShopcpAdapter extends BaseAdapter {
    Context mContext;
    List mList;
    public int getCount() {
        int size = mList.size();
        return size;
    }
    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public MyShopcpAdapter(Context context, List list) {
        this.mContext = context;
        this.mList = list;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        MyShopcpAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_myshopcp, null);
            holder = new MyShopcpAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyShopcpAdapter.ViewHolder) view.getTag();
        }
        HadShopcpEntity.ListsBean entity = (HadShopcpEntity.ListsBean) mList.get(position);
        String cpname = CommonUtils.formatNull(entity.getCpname());
        String buynum = CommonUtils.formatNull(entity.getBuynum());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String had_time = DateUtils.getSystemTime(create_time);
        holder.tvCpname.setText(cpname);
        holder.tvBuynum.setText(buynum);
        holder.tvCp_price.setText(had_time);
        holder.tvCptime.setText(had_time);
        String cpimg = entity.getCpimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + cpimg, holder.Cpimage);
        return view;
    }
    static class ViewHolder {

        @InjectView(R.id.cp_name)
        TextView tvCpname;
        @InjectView(R.id.buy_num)
        TextView tvBuynum;
        @InjectView(R.id.cp_price)
        TextView tvCp_price;
        @InjectView(R.id.cp_time)
        TextView tvCptime;
        @InjectView(R.id.cp_image)
        ImageView Cpimage;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

