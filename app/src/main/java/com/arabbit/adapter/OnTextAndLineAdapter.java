package com.arabbit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.arabbit.R;
import com.arabbit.entity.SeatListEntity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OnTextAndLineAdapter extends BaseAdapter implements OnClickListener{


    private List mList = null;
    private Context mContext;
    private Callback mCallback;

    //自定义接口，用于回调按钮点击事件到Activity
    public interface Callback {
         public void click(View v);
     }

    public OnTextAndLineAdapter(Context mContext, List list, Callback callback) {
        this.mContext = mContext;
        this.mList = list;
        mCallback = callback;
    }

    boolean isMore = false;

    public OnTextAndLineAdapter(Context mContext, List list, boolean isMore, Callback callback) {
        this.mContext = mContext;
        this.isMore = isMore;
        this.mList = list;
        mCallback = callback;
    }

    int showSize = 4;

    public int getCount() {
        int size = mList.size();
        if (isMore) {
            return size;
        }
        if (showSize < size) {
            size = showSize;
        }
        return size;
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_textline, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        SeatListEntity.ListsBean entity = (SeatListEntity.ListsBean) mList.get(position);
        String content = entity.getName();
        String city = entity.getCity();
        String starttime = CommonUtils.formatNull(entity.getStarttime());
        String time = DateUtils.getSystemTime(starttime);
        holder.tvValue.setText(content);
        holder.tvCity.setText(city);
        holder.tvTime.setText(time);
        holder.ivXce.setOnClickListener(this);
        holder.ivVedio.setOnClickListener(this);
        holder.ivGift.setOnClickListener(this);
        holder.ivPrzie.setOnClickListener(this);
        //不设置setTag会导致空指针异常
        holder.ivXce.setTag(position);
        holder.ivVedio.setTag(position);
        holder.ivGift.setTag(position);
        holder.ivPrzie.setTag(position);



        return view;
    }


    static class ViewHolder {
        @InjectView(R.id.tv_value)
        TextView tvValue;
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.tv_city)
        TextView tvCity;
        @InjectView(R.id.iv_xce)
        ImageView ivXce;
        @InjectView(R.id.iv_vedio)
        ImageView ivVedio;
        @InjectView(R.id.iv_gift)
        ImageView ivGift;
        @InjectView(R.id.iv_przie)
        ImageView ivPrzie;

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
