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
import com.arabbit.entity.ShopcpListEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.CommonUtils;
import com.arabbit.utils.ImgLoaderUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/11/8.
 */

public class ShopcpListAdapter extends BaseAdapter implements View.OnClickListener  {

    Context mContext;
    List mList;
    private ShopcpListAdapter.Callback mCallback;
    private final SimpleDateFormat simpledateformat;

    //自定义接口，用于回调按钮点击事件到Activity
    public interface Callback {
        public void click(View v);
    }

    public ShopcpListAdapter(Context context, List list, Callback callback) {
//        super(context, list);
        this.mContext = context;
        this.mList = list;
        mCallback = callback;
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
        ShopcpListAdapter.ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_cpgood, null);
            holder = new ShopcpListAdapter.ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ShopcpListAdapter.ViewHolder) view.getTag();
        }

        ShopcpListEntity.ListsBean entity = (ShopcpListEntity.ListsBean) mList.get(position);
        String cpimg = entity.getCpimg();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + cpimg, holder.Cp_image);
        String cpname = CommonUtils.formatNull(entity.getCpname());
        String info = CommonUtils.formatNull(entity.getInfo());
        String cpnum = CommonUtils.formatNull(entity.getNum());
        String cp_price = CommonUtils.formatNull(entity.getCp_price());
        String preprice = CommonUtils.formatNull(entity.getPreprice());
        String buy_count = CommonUtils.formatNull(entity.getBuy_count());
        holder.tvCpuser.setOnClickListener(this);
        holder.tvCpuser.setTag(position);

        holder.tvCpname.setText(cpname);
        holder.tvInfo.setText(info);
        holder.tvNum.setText(cpnum);
        holder.tvCpprice.setText(cp_price);
        holder.tvPreprice.setText(preprice);
//        if (!TextUtils.isEmpty(starttime)) {
//            String startformat = simpledateformat.format(new Date(Long.parseLong(starttime)));
//            holder.tvStart_time.setText(startformat);
//        } else {
//            holder.tvStart_time.setText("");
//        }
        holder.tvCount.setText(buy_count);

        return view;
    }

    static class ViewHolder {

        @InjectView(R.id.image_good)
        ImageView Cp_image;
        @InjectView(R.id.good_name)
        TextView tvCpname;
        @InjectView(R.id.good_info)
        TextView tvInfo;
        @InjectView(R.id.good_num)
        TextView tvNum;
        @InjectView(R.id.cp_price)
        TextView tvCpprice;
        @InjectView(R.id.cp_preprice)
        TextView tvPreprice;
        @InjectView(R.id.buy_count)
        TextView tvCount;
        @InjectView(R.id.cb_user)
        TextView tvCpuser;

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
