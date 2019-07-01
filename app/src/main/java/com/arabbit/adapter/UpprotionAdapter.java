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
import com.arabbit.entity.ProtionListEntity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/7/13.
 */

public class UpprotionAdapter extends BaseAdapter {

    Context mContext;
    List mList;
    private final SimpleDateFormat simpledateformat;


    public UpprotionAdapter(Context context, List list) {
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
        UpprotionAdapter.ViewHolder  holder= null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_pro, null);
            holder = new  UpprotionAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (UpprotionAdapter.ViewHolder) view.getTag();
        }
        ProtionListEntity.ListsBean entity = (ProtionListEntity.ListsBean) mList.get(position);
//        ProtionListEntity entity =  mList.get(position);
        String ptype = CommonUtils.formatNull(entity.getType());
        String pfar = CommonUtils.formatNull(entity.getFar());
        String starttime = CommonUtils.formatNull(entity.getStarttime());
        String endtime = CommonUtils.formatNull(entity.getEndtime());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String vposition = CommonUtils.formatNull(position+1);
        String time = DateUtils.getSystemTime(create_time);

        holder.tvCount.setText(vposition);
        if(ptype.equals("1")){
            holder.tvPtype.setText("普通抽奖");
        }else if (ptype.equals("2")){
            holder.tvPtype.setText("秒爆抽奖");
        }
        holder.tvFar.setText(pfar);
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

        holder.tvPro_time.setText(create_time);

        return view;
    }

    static class ViewHolder {

        @InjectView(R.id.count)
        TextView tvCount;
        @InjectView(R.id.pr_type)
        TextView tvPtype;
        @InjectView(R.id.far)
        TextView tvFar;
        @InjectView(R.id.start_time)
        TextView tvStart_time;
        @InjectView(R.id.end_time)
        TextView tvEnd_time;
        @InjectView(R.id.pro_time)
        TextView tvPro_time;


        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
