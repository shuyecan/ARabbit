package com.arabbit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.UserHadgiftEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/8/19.
 */

public class MyGiftAdapter extends BaseAdapter {
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

    public MyGiftAdapter(Context context, List list) {
        this.mContext = context;
        this.mList = list;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        MyGiftAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_mygift, null);
            holder = new MyGiftAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyGiftAdapter.ViewHolder) view.getTag();
        }
        UserHadgiftEntity.ListsBean entity = (UserHadgiftEntity.ListsBean) mList.get(position);
        String gfname = CommonUtils.formatNull(entity.getGfname());
        String gfinfo = CommonUtils.formatNull(entity.getGfinfo());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String hadgf_time = DateUtils.getSystemTime(create_time);
        holder.tvGfname.setText(gfname);
        holder.tvGfinfo.setText(gfinfo);
        holder.tvHadgf_time.setText(hadgf_time);
        String primg = entity.getGfimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + primg, holder.pr_image);
        return view;
    }
    static class ViewHolder {

        @InjectView(R.id.gf_name)
        TextView tvGfname;
        @InjectView(R.id.gf_info)
        TextView tvGfinfo;
        @InjectView(R.id.hadgf_time)
        TextView tvHadgf_time;
        @InjectView(R.id.gf_image)
        ImageView pr_image;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
