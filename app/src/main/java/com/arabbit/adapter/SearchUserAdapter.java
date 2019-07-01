package com.arabbit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.entity.UserInfoEntity;
import com.arabbit.model.Config;
import com.arabbit.utils.ImgLoaderUtils;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by linana on 2018-09-16.
 */

public class SearchUserAdapter extends BaseAdapter {
    List<UserInfoEntity> userEntity;
    Context context;
    public SearchUserAdapter(List<UserInfoEntity> userEntity, Context context){
        this.userEntity = userEntity;
        this.context = context;
    }
    @Override
    public int getCount() {
        return userEntity.size();
    }

    @Override
    public Object getItem(int i) {
        return userEntity.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_search_user,null);
            holder.tv_name = (TextView)view.findViewById(R.id.tv_area);
            holder.tv_nickname = (TextView)view.findViewById(R.id.tv_nickname);
            holder.pr_image = (ImageView)view.findViewById(R.id.pr_image);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        UserInfoEntity userInfoEntity = userEntity.get(i);
        if(userInfoEntity != null){
            String account = userInfoEntity.getAccount();
            if(!TextUtils.isEmpty(account) && !TextUtils.equals(account,null) && account != null){
                holder.tv_name.setText(account);
            }else{
                holder.tv_name.setText("暂无");

            }
            String nickname = userInfoEntity.getNickname();
            holder.tv_nickname.setText(nickname);

        }
        String avatar_img = userInfoEntity.getAvatar_img();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + avatar_img, holder.pr_image);

        return view;
    }
    class ViewHolder{
        TextView tv_name;
        TextView tv_nickname;
        ImageView pr_image;
    }
}
