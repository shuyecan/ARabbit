package com.arabbit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.HadShopmbEntity;
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

public class MyShopmbAdapter extends BaseAdapter {
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

    public MyShopmbAdapter(Context context, List list) {
        this.mContext = context;
        this.mList = list;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        MyShopmbAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_myshopcp, null);
            holder = new MyShopmbAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyShopmbAdapter.ViewHolder) view.getTag();
        }
        HadShopmbEntity.ListsBean entity = (HadShopmbEntity.ListsBean) mList.get(position);
        String mbname = CommonUtils.formatNull(entity.getMbname());
        String mbinfo = CommonUtils.formatNull(entity.getInfo());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String had_time = DateUtils.getSystemTime(create_time);
        holder.tvMbname.setText(mbname);
        holder.tvMbinfo.setText(mbinfo);
        holder.tvMbprice.setText(had_time);
        holder.tvMbtime.setText(had_time);
        String mbimg = entity.getMbimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + mbimg, holder.Mbimage);
        return view;
    }
    static class ViewHolder {

        @InjectView(R.id.cp_name)
        TextView tvMbname;
        @InjectView(R.id.cp_info)
        TextView tvMbinfo;
        @InjectView(R.id.cp_price)
        TextView tvMbprice;
        @InjectView(R.id.cp_time)
        TextView tvMbtime;
        @InjectView(R.id.cp_image)
        ImageView Mbimage;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
