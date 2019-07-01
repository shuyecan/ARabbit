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
import com.arabbit.entity.HadShopprEntity;
import com.arabbit.entity.UserHadprizeEntity;
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

public class ShopprUserAdapter extends BaseAdapter implements View.OnClickListener {

    Context mContext;
    List mList;
    private ShopprUserAdapter.Callback mCallback;

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

    public  ShopprUserAdapter(Context context, List list,ShopprUserAdapter.Callback callback) {

        this.mContext = context;
        this.mList = list;
        mCallback = callback;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ShopprUserAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_prizeuser, null);
            holder = new ShopprUserAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ShopprUserAdapter.ViewHolder) view.getTag();
        }
        HadShopprEntity.ListsBean entity = (HadShopprEntity.ListsBean) mList.get(position);
        String username = CommonUtils.formatNull(entity.getUsername());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String time = DateUtils.getSystemTime(create_time);
        String prname = CommonUtils.formatNull(entity.getPrname());
        String Prrank = CommonUtils.formatNull(entity.getRank());
        holder.Username.setText(username);
        holder.tvPrname.setText(prname);
        holder.tvProtime.setText(time);
//        holder.Prrank.setText(rank);
        holder.tvPrname.setOnClickListener(this);
        holder.tvPrname.setTag(position);
        String avatar_img = entity.getAvatar_img();

        ImgLoaderUtils.setImageloader(Config.IMG_URL + avatar_img, holder.imUimage);


        if(TextUtils.equals(Prrank,"0")){
            holder.Prrank.setText("一等奖");
        }else if(TextUtils.equals(Prrank,"1")){
            holder.Prrank.setText("二等奖");
        }else if(TextUtils.equals(Prrank,"2")){
            holder.Prrank.setText("三等奖");
        }else{
            holder.Prrank.setText("鼓励奖");
        }


        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.user_image)
        ImageView imUimage;
        @InjectView(R.id.user_name)
        TextView Username;
        @InjectView(R.id.pr_name)
        TextView tvPrname;
        @InjectView(R.id.pr_time)
        TextView tvProtime;
        @InjectView(R.id.pr_rank)
        TextView Prrank;

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
