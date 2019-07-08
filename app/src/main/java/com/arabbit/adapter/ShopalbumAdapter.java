package com.arabbit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.arabbit.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShopalbumAdapter extends RecyclerView.Adapter<ShopalbumAdapter.ViewHolder> {

    List<String> list;
    Context context;
    private ClickInterface clickInterface;


    public ShopalbumAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnclick(ClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    //回调接口
    public interface ClickInterface {
        void onItemClick(View view, int position);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            String url = list.get(position);
        Glide.with(context).load(url).into(holder.img_shopalbum);
        holder.img_shopalbum.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.img_shopalbum);
                popupMenu.getMenuInflater().inflate(R.menu.menu_longclick,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        clickInterface.onItemClick(view,position);
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }
    public void deleteitem(int p){
        list.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, list.size() - p);
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
