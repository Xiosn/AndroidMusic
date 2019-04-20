package com.viewpagertext.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TextView;



/**
 * <pre>
 *     author : 小龙虾
 *     time   : 2019/04/19
 *     desc   : 字体通用属性
 * </pre>
 */

public class UserUtils {


    /**
     * //设置字体大小
     * @param tv
     * @param size
     */
    public static void setTextSize(TextView tv,int size){
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }

    /**
     * 设置字体颜色
     * @param tv
     * @param context
     * @param color
     */
    public static void setTextColor(TextView tv, Context context,int color){
        tv.setTextColor(context.getResources().getColor(color));
    }

    /**
     * 字体是否加粗
     */
    public static void setTypeface(TextView tv,int BOLD){
        tv.setTypeface(Typeface.defaultFromStyle(BOLD));
    }


}
