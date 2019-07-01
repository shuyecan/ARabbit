package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.bean.GetSchoolListBean;
import com.arabbit.utils.CommonUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 */
public class SchoolListAdapter extends CustomBaseAdapter {
    Context mContext;
    List mList;

    public SchoolListAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_lv_school_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetSchoolListBean entity = (GetSchoolListBean) mList.get(position);
        String school = CommonUtils.formatNull(entity.getSchool_name());
        int status = CommonUtils.formatInt(entity.getStatus());
        if (status == 1) {
            holder.ivSelect.setVisibility(View.VISIBLE);
        } else {
            holder.ivSelect.setVisibility(View.INVISIBLE);
        }
        holder.tvSchool.setText(school);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_school)
        TextView tvSchool;
        @InjectView(R.id.iv_select)
        ImageView ivSelect;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
