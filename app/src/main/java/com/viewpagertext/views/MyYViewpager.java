package com.viewpagertext.views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.viewpagertext.R;
import cn.youngkaaa.yviewpager.YViewPager;

/**
 * name:小龙虾
 * time:2019.5.7
 * type:解决详情页切换到播放页
 *      viewpager与recyclerview滑动冲突问题
 */

public class MyYViewpager extends YViewPager {

    private int mFirstX =0,mFirstY=0;

    public MyYViewpager(@NonNull Context context) {
        super(context);
    }

    public MyYViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean isIntercept=false;
        int x=(int) ev.getX();
        int y=(int) ev.getY();
        RecyclerView recyclerView=findViewById(R.id.xrv_list);
        switch (ev.getAction()){
            /**
             * 父容器必须返回false，即不拦截ACTION_DOWN事件，
             * 否则后续的ACTION_MOVE，ACTION_UP事件都会直接交给父容器处理，
             * 事件没办法再传递给子元素了
             */
            case MotionEvent.ACTION_DOWN://按下
//                isIntercept=false;
//                break;
            case MotionEvent.ACTION_MOVE://移动过程中
//                if (recyclerView.canScrollVertically(-1)==true){
//                    isIntercept=false;//不拦截
//                }
                break;
            case MotionEvent.ACTION_UP://放开
//                if (recyclerView.canScrollVertically(-1)==false){
//                    return super.onInterceptTouchEvent(ev);
//                }
//                isIntercept=false;
                break;
            default:
                break;
        }
        mFirstX=x;
        mFirstY=y;
        return isIntercept;
    }

}
