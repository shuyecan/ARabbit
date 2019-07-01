package com.arabbit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GiftListEntity;
import com.arabbit.entity.UserJoingiftEntity;
import com.arabbit.model.Config;
import com.arabbit.model.IModelResult;
import com.arabbit.net.ApiException;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.ImgLoaderUtils;
import com.arabbit.utils.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;

/**
 * Created by Administrator on 2018/8/5.
 */

public class SeeGiftAdapter extends BaseAdapter {

    Context mContext;
    List mList;
    private Callback mCallback;

    //自定义接口，用于回调按钮点击事件到Activity
    public interface Callback {
        public void click(View v,int gift_id,int ava_num);
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

    public SeeGiftAdapter(Context context, List list,Callback callback) {
        this.mContext = context;
        this.mList = list;
        mCallback = callback;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        SeeGiftAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_seegift, null);
            holder = new SeeGiftAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (SeeGiftAdapter.ViewHolder) view.getTag();
        }
        GiftListEntity.ListsBean entity = (GiftListEntity.ListsBean) mList.get(position);
        String gfname = CommonUtils.formatNull(entity.getGfname());
        String gfinfo = CommonUtils.formatNull(entity.getGfinfo());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String time = DateUtils.getSystemTime(create_time);
        holder.tvGfname.setText(gfname);
        holder.tvGfinfo.setText(gfinfo);
        final int gift_id = entity.getGift_id();
        final int ava_num = entity.getAva_num();
        final String gfnum = entity.getGfnum();
        final String gfend = entity.getGfend();
        final String gfstart = entity.getGfstart();
        holder.btGf_hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("aaa","SeeGiftAdapter结束时间："+gfend);
                try {
                    long currntTimeStamp = System.currentTimeMillis();
                    if(!TextUtils.isEmpty(gfstart) && !TextUtils.equals(gfstart,null)){
                        long startTimeStampl = Long.parseLong(gfstart);
                        if(startTimeStampl >currntTimeStamp){
                            ToastUtils.showToastShort("活动尚未开始，不能领取奖品");
                            return;
                        }
                    }else{
                        ToastUtils.showToastShort("活动尚未开始，不能领取奖品");
                        return;
                    }
                    if(!TextUtils.isEmpty(gfend) && !TextUtils.equals(gfend,null)){
                        long endTimeStampl = Long.parseLong(gfend);

                        Log.e("aaa","SeeGiftAdapter结束时间："+endTimeStampl+"，当前时间："+currntTimeStamp);
                        if(currntTimeStamp >endTimeStampl){
                            ToastUtils.showToastShort("活动已结束");
                            return;
                        }
                    }else{
                        ToastUtils.showToastShort("活动已结束");
                        return;
                    }
                    int gftotalnum = Integer.parseInt(gfnum);
                    if(gftotalnum > ava_num){
                        mCallback.click(view,gift_id,ava_num);
                    }else{
                        ToastUtils.showToastShort("礼品已经被领取完了，谢谢参与活动");
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    ToastUtils.showToastShort("活动已结束");
                }
            }
        });
        holder.btGf_hit.setTag(position);

        holder.ivGfpnum.setText(ava_num+"");

        String gfimg = entity.getGfimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + gfimg, holder.imGf_image);
        return view;
    }
    static class ViewHolder {
        @InjectView(R.id.gf_image)
        ImageView imGf_image;
        @InjectView(R.id.gf_name)
        TextView tvGfname;
        @InjectView(R.id.gf_info)
        TextView tvGfinfo;
        @InjectView(R.id.gf_pnum)
        TextView ivGfpnum;
        @InjectView(R.id.gf_hit)
        TextView btGf_hit;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//
//
//    }

}
