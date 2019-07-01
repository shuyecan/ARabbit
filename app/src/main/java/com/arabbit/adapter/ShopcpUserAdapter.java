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

public class ShopcpUserAdapter extends BaseAdapter implements View.OnClickListener {

    Context mContext;
    List mList;
    private ShopcpUserAdapter.Callback mCallback;

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

    public  ShopcpUserAdapter(Context context, List list,Callback callback) {

        this.mContext = context;
        this.mList = list;
        mCallback = callback;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ShopcpUserAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_shopmb_user, null);
            holder = new ShopcpUserAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ShopcpUserAdapter.ViewHolder) view.getTag();
        }
        HadShopcpEntity.ListsBean entity = (HadShopcpEntity.ListsBean) mList.get(position);
        String username = CommonUtils.formatNull(entity.getUsername());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String time = DateUtils.getSystemTime(create_time);
        String cpname = CommonUtils.formatNull(entity.getCpname());
        String cp_price = CommonUtils.formatNull(entity.getCp_price());
        holder.Username.setText(username);
        holder.tvGood.setText(cpname);
        holder.tvPrice.setText(cp_price);
        holder.tvBuytime.setText(time);
        holder.tvTxprice.setText("购买价：");
        holder.tvGood.setOnClickListener(this);
        holder.tvGood.setTag(position);
        String avatar_img = entity.getAvatar_img();

        ImgLoaderUtils.setImageloader(Config.IMG_URL + avatar_img, holder.imUimage);




        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.user_image)
        ImageView imUimage;
        @InjectView(R.id.user_name)
        TextView Username;
        @InjectView(R.id.good_name)
        TextView tvGood;
        @InjectView(R.id.mb_price)
        TextView tvPrice;
        @InjectView(R.id.mb_time)
        TextView tvBuytime;
        @InjectView(R.id.tx_price)
        TextView tvTxprice;

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
