package com.arabbit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.PrizeListEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/7/20.
 */

public class PrizeListAdapter extends BaseAdapter implements View.OnClickListener {


    Context mContext;
    List mList;
    String mtype="";
    private  Callback mCallback;

    //自定义接口，用于回调按钮点击事件到Activity
    public interface Callback {
        public void click(View v);
    }

    public PrizeListAdapter(Context context, List list,Callback callback) {
//        super(context, list);
        this.mContext = context;
        this.mList = list;
        mCallback = callback;
    }
    public PrizeListAdapter(Context context, List list,Callback callback,String type) {
//        super(context, list);
        this.mContext = context;
        this.mList = list;
        mCallback = callback;
        this.mtype = type;
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
        PrizeListAdapter.ViewHolder  holder= null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_prize, null);
            holder = new  PrizeListAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (PrizeListAdapter.ViewHolder) view.getTag();
        }
        PrizeListEntity.ListsBean entity = (PrizeListEntity.ListsBean) mList.get(position);
        String Prname = CommonUtils.formatNull(entity.getPrname());
        String Prinfo = CommonUtils.formatNull(entity.getInfo());
        String Prnum = CommonUtils.formatNull(entity.getPrnum());
        String Prrank = CommonUtils.formatNull(entity.getRank());
        holder.tvPrname.setText(Prname);
        holder.tvPrinfo.setText(Prinfo);
        holder.tvPrnum.setText(Prnum);

        holder.tvPrdetail.setOnClickListener(this);
        holder.tvPrdetail.setTag(position);
        String prized_count = entity.getPrized_count();
        holder.tvpred_pnum.setText(prized_count);
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
        @InjectView(R.id.pr_num)
        TextView tvPrnum;
        @InjectView(R.id.pr_rank)
        TextView tvPrrank;
        @InjectView(R.id.pr_detail)
        TextView tvPrdetail;
        @InjectView(R.id.pr_image)
        ImageView pr_image;
        @InjectView(R.id.pr_pnum)
        TextView tvpred_pnum;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        mCallback.click(v);

    }



}
