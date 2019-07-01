package com.arabbit.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.arabbit.application.BaseApplication;


public class UIUtils {
    public static Resources getResources() {
        return BaseApplication.getApplication().getResources();
    }

    //返回string.xml的数组
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取颜色id
     */
    // 同时兼容高、低版本
    public static int getColor(int colorId) {
        return ContextCompat.getColor(BaseApplication.getApplication(), colorId);
    }

    /**
     * dp转成px
     *
     * @param
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f);//四舍五入
        return px;
    }

    /**
     * px转dp
     *
     * @param
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        float density = getResources().getDisplayMetrics().density;
        int dp = (int) (px / density + 0.5f);
        return dp;

    }

    public static View inflate(int resId) {
        return View.inflate(BaseApplication.getApplication(), resId, null);
    }

    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    /**
     * 得到EditView的字符串：
     */
    public static String getStrEditView(EditText editText) {
        return CommonUtils.formatNull(editText.getText().toString().trim());
    }

    /**
     * 得到SpinnerView的选中的字符串：
     */
    public static String getStrSpinnerView(Spinner spinner) {
        return spinner.getSelectedItem().toString();
    }

    /**
     * 得到TextView的字符串：
     */
    public static String getStrTextView(TextView tv) {
        return CommonUtils.formatNull(tv.getText().toString().trim());
    }

    /**
     * 校验手机号码是否有效
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        /*Pattern pattern = Pattern.compile("^[1][358][0-9]{9}$");
        Matcher matcher = pattern.matcher(phone);
		if (matcher.find())
		{
			return true;
		}
		return false;*/
        if (phone.length() > 10) {
            return true;
        } else if (phone.length() != 11) {
            return false;
        } else {
            return false;
        }

    }


    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }


}
