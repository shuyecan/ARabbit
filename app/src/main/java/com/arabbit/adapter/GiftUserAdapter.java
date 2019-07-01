package com.arabbit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.UserHadgiftEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.DateUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/8/28.
 */

public class GiftUserAdapter  extends BaseAdapter implements View.OnClickListener{

    Context mContext;
    List mList;
    private Callback mCallback;

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

    public  GiftUserAdapter(Context context, List list,Callback callback) {
//        super(context, list);
        this.mContext = context;
        this.mList = list;
        mCallback = callback;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        GiftUserAdapter.ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_giftuser, null);
            holder = new GiftUserAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (GiftUserAdapter.ViewHolder) view.getTag();
        }
        UserHadgiftEntity.ListsBean entity = (UserHadgiftEntity.ListsBean) mList.get(position);
        String username = CommonUtils.formatNull(entity.getUsername());
        String gfname = CommonUtils.formatNull(entity.getGfname());
        String create_time = CommonUtils.formatNull(entity.getCreate_time());
        String time = DateUtils.getSystemTime(create_time);
        holder.Username.setText(username);
        holder.tvGfname.setText(gfname);
        holder.tvGftime.setText(time);
        holder.tvGfname.setOnClickListener(this);
        holder.tvGfname.setTag(position);
//        String gfimg = entity.getGfimg();
        String avatar_img = entity.getAvatar_img();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + avatar_img, holder.imUimage);
        return view;
    }
    static class ViewHolder {
        @InjectView(R.id.user_image)
        ImageView imUimage;
        @InjectView(R.id.user_name)
        TextView Username;
        @InjectView(R.id.gf_name)
        TextView tvGfname;
        @InjectView(R.id.gf_time)
        TextView tvGftime;

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
