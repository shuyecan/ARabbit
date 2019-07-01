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
import com.arabbit.entity.UserHadproEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/8/29.
 */

public class ProUserAdapter  extends BaseAdapter {

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

    public  ProUserAdapter(Context context, List list) {
//        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ProUserAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_giftuser, null);
            holder = new ProUserAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ProUserAdapter.ViewHolder) view.getTag();
        }
        UserHadproEntity.ListsBean entity = (UserHadproEntity.ListsBean) mList.get(position);
        String username = CommonUtils.formatNull(entity.getUsername());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String time = DateUtils.getSystemTime(create_time);
        holder.Username.setText(username);
        holder.tvProtime.setText(time);
        holder.tvProname.setVisibility(View.GONE);
        holder.tvTitle.setVisibility(View.GONE);
        holder.Time_title.setText("参与活动时间：");
        String avatar_img = entity.getAvatar_img();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + avatar_img, holder.imUimage);
        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.user_image)
        ImageView imUimage;
        @InjectView(R.id.user_name)
        TextView Username;
        @InjectView(R.id.gf_name)
        TextView tvProname;
        @InjectView(R.id.gf_time)
        TextView tvProtime;
        @InjectView(R.id.gf_title)
        TextView tvTitle;
        @InjectView(R.id.time_title)
        TextView Time_title;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }



}
