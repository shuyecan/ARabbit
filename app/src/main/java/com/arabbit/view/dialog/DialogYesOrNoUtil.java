package com.arabbit.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.arabbit.R;
import com.arabbit.utils.CommonUtils;


/**
 * 自定义Dialog
 *
 * @author Vin
 */
public class DialogYesOrNoUtil extends Dialog {

    private TextView dialogTitle;
    private TextView dialogContent;
    private TextView dialogNo;
    private TextView dialogYes;
    private DisplayMetrics displayM;
    private View view_1;
    private boolean isCancelBack;

    public DialogYesOrNoUtil(Context context) {
        super(context, R.style.common_dialog);
        initDialog();
    }

    public DialogYesOrNoUtil(Context context, int themeId) {
        super(context, themeId);
        initDialog();
    }

    /**
     * 初始化
     */
    private void initDialog() {
        displayM = getContext().getResources().getDisplayMetrics();
        setContentView(R.layout.dialog_yes_no);
        // 获取手机屏幕大小
        LayoutParams lp = getWindow().getAttributes();
        // 设置Dialog的宽度为手机屏幕的四分之三
        lp.width = displayM.widthPixels * 3 / 4;
        getWindow().setAttributes(lp);
        //点击屏幕关闭dialog框
        setCanceledOnTouchOutside(true);

        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogContent = (TextView) findViewById(R.id.dialog_content);
        dialogNo = (TextView) findViewById(R.id.dialog_no);
        dialogYes = (TextView) findViewById(R.id.dialog_yes);
        view_1 = findViewById(R.id.view_dialog_1);

    }

    /**
     * 设置dialog顶部Title的文本
     *
     * @param title
     */
    public void setDialogTitle(String title) {
        if (title == null || title == "") {
            dialogTitle.setVisibility(View.GONE);
            view_1.setVisibility(View.GONE);
        }
        dialogTitle.setText(title);
    }

    /**
     * 设置dialog中部要显示的文本内容
     *
     * @param content
     */
    public void setContent(String content) {
        if (CommonUtils.isNull(content)) {
            dialogContent.setVisibility(View.GONE);
            return;
        }
        dialogContent.setText(content);
//		dialogContent.setGravity(Gravity.CENTER);
        dialogContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void setContentCenterText(String content) {
        dialogContent.setText(content);
        dialogContent.setGravity(Gravity.CENTER);
    }

    /**
     * 设置dialog底部确认按钮的文本内容及单击事件
     *
     * @param txt
     * @param listener
     */
    public void setYesText(String txt, View.OnClickListener listener) {
        dialogYes.setText(txt);
        dialogYes.setOnClickListener(listener);
    }

    public TextView getYesButton() {
        return dialogYes;
    }

    /**
     * 设置dialog底部确认按钮的文本
     *
     * @param txt
     */
    public void setYesText(String txt) {
        dialogYes.setText(txt);
    }

    /**
     * 设置底部dialog取消按钮的文本及单击事件
     *
     * @param txt
     * @param listener
     */
    public void setNoText(String txt, View.OnClickListener listener) {
        dialogNo.setText(txt);
        dialogNo.setOnClickListener(listener);
    }

    private View.OnClickListener onClickListener;

    public void setOkClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setCancelBack(boolean isCancelBack) {
        this.isCancelBack = isCancelBack;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isCancelBack && keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
