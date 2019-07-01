package com.arabbit.view.viewpager.VerticalViewPager;

import android.view.View;

/**
 * Created by wenke on 08/29/2016.
 */
public class YAccordionTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setPivotY(position < 0 ? 0 : view.getHeight());
        view.setScaleY(position < 0 ? 1f + position : 1f - position);
    }

}
