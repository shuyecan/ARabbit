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
import com.arabbit.entity.HadShopmbEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/11/15.
 */

public class ShopmbUserAdapter extends BaseAdapter implements View.OnClickListener {

    Context mContext;
    List mList;
    private ShopmbUserAdapter.Callback mCallback;

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

    public  ShopmbUserAdapter(Context context, List list,Callback callback) {

        this.mContext = context;
        this.mList = list;
        mCallback = callback;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ShopmbUserAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_shopmb_user, null);
            holder = new ShopmbUserAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ShopmbUserAdapter.ViewHolder) view.getTag();
        }
        HadShopmbEntity.ListsBean entity = (HadShopmbEntity.ListsBean) mList.get(position);
        String username = CommonUtils.formatNull(entity.getUsername());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String time = DateUtils.getSystemTime(create_time);
        String mbname = CommonUtils.formatNull(entity.getMbname());
        String mb_price = CommonUtils.formatNull(entity.getMb_price());
        holder.Username.setText(username);
        holder.tvGood.setText(mbname);
        holder.tvMbtime.setText(time);
        holder.tvPrice.setText(mb_price);
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
        TextView tvMbtime;

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

