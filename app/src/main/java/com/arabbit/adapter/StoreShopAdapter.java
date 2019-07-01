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

/**
 * Created by Administrator on 2018/11/11.
 */

public class StoreShopAdapter extends BaseAdapter {
    List<UserInfoEntity> userEntity;
    Context context;
    public StoreShopAdapter(List<UserInfoEntity> userEntity, Context context){
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
        StoreShopAdapter.ViewHolder holder;
        if(view == null){
            holder = new StoreShopAdapter.ViewHolder();
            view = View.inflate(context, R.layout.item_shop_pro,null);
            holder.tv_adress = (TextView)view.findViewById(R.id.tv_adress);
            holder.tv_nickname = (TextView)view.findViewById(R.id.tv_nickname);
            holder.tv_shopfar = (TextView)view.findViewById(R.id.tv_shopfar);
            holder.shop_image = (ImageView)view.findViewById(R.id.shop_image);
            view.setTag(holder);
        }else{
            holder = (StoreShopAdapter.ViewHolder)view.getTag();
        }
        UserInfoEntity userInfoEntity = userEntity.get(i);
        if(userInfoEntity != null){

            String nickname = userInfoEntity.getNickname();
            holder.tv_nickname.setText(nickname);

        }
        String avatar_img = userInfoEntity.getAvatar_img();
        ImgLoaderUtils.setImageloader(Config.IMG_URL + avatar_img, holder.shop_image);

        return view;
    }
    class ViewHolder{
        TextView tv_adress;
        TextView tv_nickname;
        TextView tv_shopfar;
        ImageView shop_image;
    }

}
