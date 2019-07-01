package com.arabbit.view.recyclerview;

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by net4 on 2017/6/19.
 */

public class CardLayoutManager extends RecyclerView.LayoutManager {
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    public CardLayoutManager(@NonNull RecyclerView recyclerView, @NonNull ItemTouchHelper itemTouchHelper) {
        this.mRecyclerView = recyclerView;
        this.mItemTouchHelper = itemTouchHelper;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        removeAllViews();
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if(itemCount>CardConfig.DEFAULT_SHOW_ITEM){
            for(int position = CardConfig.DEFAULT_SHOW_ITEM;position>=0;position--){
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view,0,0);
                int widthSpace = getWidth() -getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getBottomDecorationHeight(view);
                layoutDecoratedWithMargins(view,widthSpace/2,heightSpace/2,widthSpace/2+getDecoratedMeasuredWidth(view),heightSpace/2+getBottomDecorationHeight(view));
                if(position == CardConfig.DEFAULT_SHOW_ITEM){
                    view.setScaleX(1-(position-1)*CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1-(position-1)*CardConfig.DEFAULT_SCALE);
                    view.setTranslationY((position-1)*view.getMeasuredHeight()/CardConfig.DEFAULT_TRANSLATE_Y);
                }else if(position>0){
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);

                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);

                    view.setTranslationY(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);

                }else{
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }else{
            // 当数据源个数小于或等于最大显示数时
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // recyclerview 布局
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));

                if (position > 0) {
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }

    }
    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mItemTouchHelper.startSwipe(childViewHolder);
            }
            return false;
        }
    };
}
