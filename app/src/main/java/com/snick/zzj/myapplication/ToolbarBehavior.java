package com.snick.zzj.myapplication;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by zzj on 17-5-24.
 */

public class ToolbarBehavior extends CoordinatorLayout.Behavior<FrameLayout> {
    private static final String TAG = "ToolbarBehavior";

    private Context context;

    public ToolbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        return isDependOn(dependency);
    }

    //不要忘了content是有初始偏移的，getContentInitOffset
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child, View dependency) {
        float delta = 1 - (getContentInitOffset() - dependency.getTranslationY())/getContentOffsetRange();
        if(dependency.getTranslationY() > getToolbarHeight() && dependency.getTranslationY() <= getContentInitOffset()) {
            child.setTranslationY(0);//为了避免在content划过 header与toolbar分界处的跳变，这里每次都将其Y的偏移设为0，具体现象把这句注释掉，快速来回滑动就知道了。
            child.setAlpha(delta);
        } else if(dependency.getTranslationY() <= getToolbarHeight()){
            child.setTranslationY(dependency.getTranslationY()-getToolbarHeight());
        }
        return false;
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.recycler;
    }

    private int getContentInitOffset() {
        return context.getResources().getDimensionPixelOffset(R.dimen.content_offset_init);
    }

    private int getContentOffsetRange() {
        return context.getResources().getDimensionPixelOffset(R.dimen.news_header_pager_height);
    }

    private int getToolbarHeight() {
        return context.getResources().getDimensionPixelOffset(R.dimen.news_tool_bar_height);
    }
}
