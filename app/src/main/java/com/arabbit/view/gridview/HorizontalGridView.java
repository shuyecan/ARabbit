package com.arabbit.view.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.arabbit.utils.UIUtils;


/**
 * Created by net8 on 2017/6/19.
 */

public class HorizontalGridView extends GridView {
    public HorizontalGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public HorizontalGridView(Context context) {
        super(context);
    }

    public HorizontalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int childWidth = UIUtils.dp2px(88);
        int childHeight = UIUtils.dp2px(65);
        int lastPadding = UIUtils.dp2px(10);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(expandSpec, heightMeasureSpec);

        //设置GridView的宽度
        setMeasuredDimension(childCount * childWidth + lastPadding, childHeight);
    }

}