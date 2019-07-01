package com.arabbit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.GetClassListEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 */
public class ClassAdapter extends CustomBaseAdapter {
    Context mContext;
    List<GetClassListEntity> mList;

    public ClassAdapter(Context context, List list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_gv_class, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GetClassListEntity entity = mList.get(position);
        String class_name = entity.getClass_name();
        holder.tvClassValue.setText(class_name);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_class_value)
        TextView tvClassValue;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
