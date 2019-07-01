package com.arabbit.view.edit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.arabbit.R;
import com.arabbit.utils.CommonUtils;


/**
 * 带图文提示(剧中)的EditText，并附带右侧图片点击事件
 */
public class SearchView extends EditText {

    float searchSize = 0;
    float textSize = 0;
    int textColor = 0xFF000000;
    Drawable mDrawable;
    Paint paint;


    /**
     * EditText右侧的图标
     */
    protected Drawable mRightDrawable;

    private RightPicOnclickListener rightPicOnclickListener;

    public interface RightPicOnclickListener {
        void rightPicClick();
    }


    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        InitResource(context, attrs);
        InitPaint();
    }

    private void init(final Context context) {
        mRightDrawable = getCompoundDrawables()[2];

        if (mRightDrawable == null) {
            return;
        }

        mRightDrawable.setBounds(0, 0, mRightDrawable.getIntrinsicWidth(), mRightDrawable.getIntrinsicHeight());
    }

    private void InitResource(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.searchedit);
        float density = context.getResources().getDisplayMetrics().density;
        searchSize = mTypedArray.getDimension(R.styleable.searchedit_imagewidth, 18 * density + 0.5F);
        textColor = mTypedArray.getColor(R.styleable.searchedit_textColor, 0xFF848484);
        textSize = mTypedArray.getDimension(R.styleable.searchedit_textSize, 14 * density + 0.5F);
        mTypedArray.recycle();
    }

    private void InitPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DrawSearchIcon(canvas);
    }

    String hint = "";

    public void setHintValue(String hint) {
        this.hint = hint;
    }

    private void DrawSearchIcon(Canvas canvas) {
        if (this.getText().toString().length() == 0) {
            if (CommonUtils.isNull(hint)) {
                return;
            }
            float textWidth = paint.measureText(hint);
            float textHeight = getFontLeading(paint);
            float dx = (getWidth() - searchSize - textWidth - 8) / 2;
            float dy = (getHeight() - searchSize) / 2;
            canvas.save();
            canvas.translate(getScrollX() + dx, getScrollY() + dy);
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            }

            canvas.drawText(hint, getScrollX() + searchSize + 8, getScrollY() + (getHeight() - (getHeight() - textHeight) / 2) - paint.getFontMetrics().bottom - dy, paint);
            canvas.restore();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mDrawable == null) {
            try {
                mDrawable = getContext().getResources().getDrawable(R.mipmap.search_icon);
                mDrawable.setBounds(0, 0, (int) searchSize, (int) searchSize);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
            mDrawable = null;
        }
        super.onDetachedFromWindow();
    }

    public float getFontLeading(Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {

                    //设置点击EditText右侧图标EditText失去焦点，
                    // 防止点击EditText右侧图标EditText获得焦点，软键盘弹出
                    setFocusableInTouchMode(false);
                    setFocusable(false);

                    //点击EditText右侧图标事件接口回调
                    if (rightPicOnclickListener != null) {
                        rightPicOnclickListener.rightPicClick();
                    }
                } else {
                    //设置点击EditText输入区域，EditText请求焦点，软键盘弹出，EditText可编辑
                    setFocusableInTouchMode(true);
                    setFocusable(true);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void setRightPicOnclickListener(RightPicOnclickListener rightPicOnclickListener) {
        this.rightPicOnclickListener = rightPicOnclickListener;
    }

}