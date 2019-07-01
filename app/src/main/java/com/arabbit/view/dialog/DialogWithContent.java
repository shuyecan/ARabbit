package com.arabbit.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.utils.CommonUtils;


/**
 * 自定义Dialog
 * <p>
 * 元素只有  两段文本
 * <p>
 * 一个确定按钮
 *
 * @author kyq
 */
public class DialogWithContent extends Dialog {

    private DisplayMetrics displayM;
    private TextView tvContent;
    private TextView tvYes;


    public DialogWithContent(Context context) {
        super(context, R.style.common_dialog);
        init();
    }

    protected DialogWithContent(Context context, boolean cancelable,
                                OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public DialogWithContent(Context context, int theme) {
        super(context, theme);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        displayM = getContext().getResources().getDisplayMetrics();
        setContentView(R.layout.item_custom_dialog);
        // 获取手机屏幕大小
        LayoutParams lp = getWindow().getAttributes();
        if (displayM.widthPixels < 720) {
            lp.width = (int) (displayM.widthPixels * 0.75);
        } else {
            lp.width = (int) (displayM.widthPixels * 0.95);
        }
        // 设置Dialog的宽度为手机屏幕的四分之三

        getWindow().setAttributes(lp);
        // 点击屏幕关闭dialog框
        setCanceledOnTouchOutside(true);
        tvContent = (TextView) findViewById(R.id.dialog_content);
        tvYes = (TextView) findViewById(R.id.dialog_yes);

    }

    /**
     * 设置dialog中部要显示的文本内容
     *
     * @param content
     */
    public void setContent(String content) {
        if (CommonUtils.isNull(content)) {
            tvContent.setVisibility(View.GONE);
            return;
        }
        tvContent.setText(content);
//		dialogContent.setGravity(Gravity.CENTER);
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    /**
     * 设置dialog底部确认按钮的文本内容及单击事件
     *
     * @param txt
     * @param listener
     */
    public void setYesText(String txt, View.OnClickListener listener) {
        tvYes.setText(txt);
        tvYes.setOnClickListener(listener);
    }

}
