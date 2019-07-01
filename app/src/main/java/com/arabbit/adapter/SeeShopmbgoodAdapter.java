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
import com.arabbit.entity.ShopmbgoodListEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/11/10.
 */

public class SeeShopmbgoodAdapter extends BaseAdapter {

    Context mContext;
    List mList;
    private final SimpleDateFormat simpledateformat;



    public SeeShopmbgoodAdapter(Context context, List list) {
//        super(context, list);
        this.mContext = context;
        this.mList = list;
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup arg2) {
        SeeShopmbgoodAdapter.ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_mbgood, null);
            holder = new SeeShopmbgoodAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (SeeShopmbgoodAdapter.ViewHolder) view.getTag();
        }

        ShopmbgoodListEntity.ListsBean entity = (ShopmbgoodListEntity.ListsBean) mList.get(position);
        String mbimg = entity.getMbimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + mbimg, holder.mb_image);
        String mbname = CommonUtils.formatNull(entity.getMbname());
        String info = CommonUtils.formatNull(entity.getInfo());
        String mbnum = CommonUtils.formatNull(entity.getNum());
        String mb_price = CommonUtils.formatNull(entity.getMb_price());
        String preprice = CommonUtils.formatNull(entity.getPreprice());
        String starttime = CommonUtils.formatNull(entity.getStarttime());
        String buy_count = CommonUtils.formatNull(entity.getBuy_count());

        holder.tvMbname.setText(mbname);
        holder.tvInfo.setText(info);
        holder.tvNum.setText(mbnum);
        holder.tvMbprice.setText(mb_price);
        holder.tvPreprice.setText(preprice);
        if (!TextUtils.isEmpty(starttime)) {
            String startformat = simpledateformat.format(new Date(Long.parseLong(starttime)));
            holder.tvStart_time.setText(startformat);
        } else {
            holder.tvStart_time.setText("");
        }
        holder.tvCount.setText(buy_count);

        return view;
    }

    static class ViewHolder {

        @InjectView(R.id.image_good)
        ImageView mb_image;
        @InjectView(R.id.good_name)
        TextView tvMbname;
        @InjectView(R.id.good_info)
        TextView tvInfo;
        @InjectView(R.id.good_num)
        TextView tvNum;
        @InjectView(R.id.mb_price)
        TextView tvMbprice;
        @InjectView(R.id.mb_preprice)
        TextView tvPreprice;
        @InjectView(R.id.start_time)
        TextView tvStart_time;
        @InjectView(R.id.buy_count)
        TextView tvCount;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }


}
