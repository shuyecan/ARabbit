package com.arabbit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arabbit.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShopalbumAdapter extends RecyclerView.Adapter<ShopalbumAdapter.ViewHolder> {

    List<String> list;
    Context context;

    public ShopalbumAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopalbum, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            String url = list.get(position);
        Glide.with(context).load(url).into(holder.img_shopalbum);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_shopalbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_shopalbum = (ImageView) itemView.findViewById(R.id.img_shopalbum);
        }
    }
}
