package com.viewpagertext.views;


import android.content.Context;
import android.util.AttributeSet;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * <pre>
 *      author : 小龙虾
 *      time   : 2019/04/19
 *      desc   :自定义ImageView的宽高，使其高=宽
 * </pre
 */
public class MyRoundedImageView extends RoundedImageView {


    public MyRoundedImageView(Context context) {
        super(context);
    }

    public MyRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
