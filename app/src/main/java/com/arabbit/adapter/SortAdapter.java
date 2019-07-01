package com.arabbit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.bean.SortModel;
import com.arabbit.entity.GetSchoolListEntity;
import com.arabbit.utils.CommonUtils;
import com.arabbit.view.gridview.MyGridView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SortAdapter extends BaseAdapter implements SectionIndexer {


    private List<SortModel> mList = null;
    private Context mContext;

    public SortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    public List<SortModel> getmList() {
        return mList;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<SortModel> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_sort, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final SortModel mContent = mList.get(position);
        final String city = mContent.getName();
        holder.tvTitle.setText(city);
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(mContent.getSortLetters());
        } else {
            holder.tvLetter.setVisibility(View.GONE);
        }

        final int status = mContent.getStatus();
        if (status == 0) {
            holder.ivXiala.setImageResource(R.mipmap.btn_xiala_default);
            holder.gvSort.setVisibility(View.GONE);
        } else {
            holder.ivXiala.setImageResource(R.mipmap.btn_xiala_selecte);
            holder.gvSort.setVisibility(View.VISIBLE);
        }

        holder.layoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gvItemClick != null) {
                    gvItemClick.onSubscriptOnClickmClick(view, position, city);
                }
            }
        });


        final List<GetSchoolListEntity> schools = mContent.getEntity();
        if (!CommonUtils.isNull(schools)) {
            SortGridViewAdapter gvAdapter = new SortGridViewAdapter(mContext, schools);
            holder.gvSort.setAdapter(gvAdapter);
            holder.gvSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String school_id = CommonUtils.formatNull(schools.get(position).getSchool_id());
                    String school = CommonUtils.formatNull(schools.get(position).getSchool_name());
                    if (gvItemClick != null) {
                        gvItemClick.onGvItemClick(view, position, school_id, school);
                    }
                }
            });
//            UIUtils.setListViewHeightBasedOnChildren(holder.gvSort);

        }

        return view;
    }


    public interface GvItemClick {
        public void onGvItemClick(View view, int position, Object data, String school);

        public void onSubscriptOnClickmClick(View view, int position, String city);
    }

    GvItemClick gvItemClick;

    public void setOnGvItemClick(GvItemClick gvItemClick) {
        this.gvItemClick = gvItemClick;
    }

    public void updateItem(int position, int status) {
        mList.get(position).setStatus(status);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @InjectView(R.id.catalog)
        TextView tvLetter;
        @InjectView(R.id.title)
        TextView tvTitle;
        @InjectView(R.id.layout_title)
        RelativeLayout layoutTitle;
        @InjectView(R.id.iv_xiala)
        ImageView ivXiala;
        @InjectView(R.id.gv_sort)
        MyGridView gvSort;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mList.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }


}
