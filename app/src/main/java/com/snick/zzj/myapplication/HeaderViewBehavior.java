package com.snick.zzj.myapplication;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by zzj on 17-5-24.
 */

public class HeaderViewBehavior extends CoordinatorLayout.Behavior<ImageView> {
    private static final String TAG = "HeaderViewBehavior";

    private Context context;

    //一定要实现这个构造函数，否则会报Could not inflate Behavior subclass xxx 异常，可查看源码
    public HeaderViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View directTargetChild, View target, int nestedScrollAxes) {
        //拦截垂直方向上的滚动事件且当前状态是打开的并且还可以继续向上收缩
        boolean result = canScroll(child, 0);
        Log.d(TAG,"onStartScroll:"+result);
        return result;
    }

    //getTranslationY计算的是view对于parent的偏移量
    private boolean canScroll(View child, float pendingDy) {
        int pendingTranslationY = (int) (child.getTranslationY() - pendingDy);
        Log.d(TAG, "canScroll:"+pendingTranslationY+"------"+getHeaderOffsetRange()+"-------"+getHeaderOffsetRangeHideToolBar());
        if (pendingTranslationY >= 0-getHeaderOffsetRange()-getHeaderOffsetRangeHideToolBar() && pendingTranslationY <= 0) {
            return true;
        }
        return false;
    }

    //onNestedPreScroll该方法的会传入内部View移动的dx,dy，如果你需要消耗一定的dx,dy，
    // 就通过最后一个参数consumed进行指定，例如我要消耗一半的dy，就可以写consumed[1]=dy/2
    //dy是单次滑动的距离，下次滑动会重新计数
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ImageView child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //dy>0 scroll up;dy<0,scroll down
        float halfOfDis = dy / 4.0f; //消费掉其中的4分之1，不至于滑动效果太灵敏
        //在快速滑动时halfOfDis有可能一次跳变超过20以上，如果原本translationY差19到达顶部，这样一来就会判断成无法scroll，造成顶部有缝隙
        if (canScroll(child, halfOfDis)) {
            child.setTranslationY(child.getTranslationY() - halfOfDis);
        } else if(halfOfDis > Math.abs(child.getTranslationY() + getHeaderOffsetRange() + getHeaderOffsetRangeHideToolBar())) {
            Log.d(TAG,"direct to top");
            child.setTranslationY(0-getHeaderOffsetRange()-getHeaderOffsetRangeHideToolBar());
        } else if(halfOfDis < child.getTranslationY()) {
            child.setTranslationY(0);
        }
        //当滑动到顶部时，继续往上滑应该是不允许滑动，但是向下应该是可以滑动
        //但是我们在onStartNestedScroll中没法判断滑动的方向，因此只好在这里判断了。

        Log.d(TAG,"Y:"+child.getTranslationY());
        if((dy>0&&child.getTranslationY() == 0-getHeaderOffsetRange()-getHeaderOffsetRangeHideToolBar()) ||
                (dy<0&&child.getTranslationY() == 0))
            consumed[1] = 0;
        else
            consumed[1] = dy;
    }

    //Header偏移量
    private int getHeaderOffsetRange() {
        return context.getResources().getDimensionPixelOffset(R.dimen.header_offset_first);
    }

    //为了保证Header滑动速率保持一直，第二段的Header移动距离我们计算出来。
    //第一段移动：header移动了header_offset_first的距离，content移动了news_header_pager_height的距离，toolbar透明了
    //第二段移动：content移动news_tool_bar_height的距离，toolbar也移动news_tool_bar_height距离，header的距离是可以比例计算的。
    private int getHeaderOffsetRangeHideToolBar() {
        return getHeaderOffsetRange() * context.getResources().getDimensionPixelOffset(R.dimen.news_tool_bar_height)
                / context.getResources().getDimensionPixelOffset(R.dimen.news_header_pager_height);
    }
}
