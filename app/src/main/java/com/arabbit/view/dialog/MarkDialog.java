package com.arabbit.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.view.MarkView.DrawCheckMarkView;
import com.arabbit.view.MarkView.DrawCrossMarkView;


/**
 *
 */
public class MarkDialog extends Dialog {

    public DrawCheckMarkView checkMarkView;
    public DrawCrossMarkView crossMarkView;
    private TextView tvBehave;


    public MarkDialog(Context context, int type) {
        super(context, R.style.common_dialog);
        this.type = type;
        init();
    }

    protected MarkDialog(Context context, boolean cancelable,
                         OnCancelListener cancelListener, int type) {
        super(context, cancelable, cancelListener);
        this.type = type;
        init();
    }

    public MarkDialog(Context context, int theme, int type) {
        super(context, theme);
        this.type = type;
        init();
    }

    int type;


    /**
     * 初始化
     */
    private void init() {
        setContentView(R.layout.item_mark_dialog);
        // 点击屏幕关闭dialog框
        setCanceledOnTouchOutside(true);
        checkMarkView = (DrawCheckMarkView) findViewById(R.id.mv_check);
        crossMarkView = (DrawCrossMarkView) findViewById(R.id.mv_cross);
        tvBehave = (TextView) findViewById(R.id.tv_behave);
        if (type == 1) {
            checkMarkView.setVisibility(View.VISIBLE);
            crossMarkView.setVisibility(View.INVISIBLE);
        } else {
            checkMarkView.setVisibility(View.INVISIBLE);
            crossMarkView.setVisibility(View.VISIBLE);
        }
        Behave behave = new Behave();
        checkMarkView.setAnimationDone(behave);
        crossMarkView.setAnimationDone(behave);
    }

    class Behave implements DrawCheckMarkView.AnimationDone {

        @Override
        public void Done() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dismiss();
        }
    }


    public void setBeHave(String beHave) {
        if (tvBehave != null) {
            tvBehave.setText(beHave);
        }
    }
}
