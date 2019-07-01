package com.arabbit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GiftListEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/6/24.
 */

public class UpgiftAdapter extends BaseAdapter implements View.OnClickListener {

    Context mContext;
    List mList;
    private UpgiftAdapter.Callback mCallback;

    //自定义接口，用于回调按钮点击事件到Activity
    public interface Callback {
        public void click(View v);
    }
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

    public UpgiftAdapter(Context context, List list,Callback callback) {
//        super(context, list);
        this.mContext = context;
        this.mList = list;
        mCallback = callback;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        UpgiftAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_gift, null);
            holder = new UpgiftAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (UpgiftAdapter.ViewHolder) view.getTag();
        }
        GiftListEntity.ListsBean entity = (GiftListEntity.ListsBean) mList.get(position);
        String gfname = CommonUtils.formatNull(entity.getGfname());
        String gfinfo = CommonUtils.formatNull(entity.getGfinfo());
        String gfnum = CommonUtils.formatNull(entity.getGfnum());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String time = DateUtils.getSystemTime(create_time);
        holder.tvGfname.setText(gfname);
        holder.tvGfinfo.setText(gfinfo);
        holder.tvGfnum.setText(gfnum);
        holder.ivGfdetail.setOnClickListener(this);
        holder.ivGfdetail.setTag(position);
        String gfimg = entity.getGfimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + gfimg, holder.imGf_image);
        int ava_num = entity.getAva_num();
        holder.ivGfpnum.setText(ava_num+"");
        return view;
    }
    static class ViewHolder {
        @InjectView(R.id.gf_image)
        ImageView imGf_image;
        @InjectView(R.id.gf_name)
        TextView tvGfname;
        @InjectView(R.id.gf_info)
        TextView tvGfinfo;
        @InjectView(R.id.gf_num)
        TextView tvGfnum;
        @InjectView(R.id.gf_pnum)
        TextView ivGfpnum;
        @InjectView(R.id.gf_detail)
        TextView ivGfdetail;


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
