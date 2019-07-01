package com.arabbit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.UserHadprizeEntity;
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

public class MyPrizeAdapter extends BaseAdapter {

    Context mContext;
    List mList;


    public MyPrizeAdapter(Context context, List list) {
//        super(context, list);
        this.mContext = context;
        this.mList = list;
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
        MyPrizeAdapter.ViewHolder  holder= null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_myprize, null);
            holder = new  MyPrizeAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyPrizeAdapter.ViewHolder) view.getTag();
        }
        UserHadprizeEntity.ListsBean entity = (UserHadprizeEntity.ListsBean) mList.get(position);
        String Prname = CommonUtils.formatNull(entity.getPrname());
        String Prinfo = CommonUtils.formatNull(entity.getInfo());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String hadpr_time = DateUtils.getSystemTime(create_time);
        String Prrank = CommonUtils.formatNull(entity.getRank());

        holder.tvPrname.setText(Prname);
        holder.tvPrinfo.setText(Prinfo);
        holder.tvCreate_time.setText(hadpr_time);
//        holder.tvPrrank.setText(Prrank);
        String primg = entity.getPrimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + primg, holder.pr_image);

        if(TextUtils.equals(Prrank,"0")){
            holder.tvPrrank.setText("一等奖");
        }else if(TextUtils.equals(Prrank,"1")){
            holder.tvPrrank.setText("二等奖");
        }else if(TextUtils.equals(Prrank,"2")){
            holder.tvPrrank.setText("三等奖");
        }else{
            holder.tvPrrank.setText("鼓励奖");
        }

        return view;
    }

    static class ViewHolder {

        @InjectView(R.id.pr_name)
        TextView tvPrname;
        @InjectView(R.id.pr_info)
        TextView tvPrinfo;
        @InjectView(R.id.pr_create_time)
        TextView tvCreate_time;
        @InjectView(R.id.pr_rank)
        TextView tvPrrank;
        @InjectView(R.id.pr_image)
        ImageView pr_image;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
