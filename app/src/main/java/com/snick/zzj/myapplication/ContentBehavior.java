package com.snick.zzj.myapplication;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zzj on 17-5-24.
 */

public class ContentBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    private static final String TAG = "ContentBehavior";

    private Context context;

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        //初始设置，content要在header之下，因此初始需要有一个Y的偏移
        if(dependency.getTranslationY() == 0)
            child.setTranslationY(getContentInitOffset());
        else {
            child.setTranslationY(getContentInitOffset() +
                    dependency.getTranslationY() * dependency.getHeight() / getHeaderOffsetRange());
        }
        return false;
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.iv_header;
    }

    //Header偏移量
    private int getHeaderOffsetRange() {
        return context.getResources().getDimensionPixelOffset(R.dimen.header_offset_first);
    }

    private int getContentInitOffset() {
        return context.getResources().getDimensionPixelOffset(R.dimen.content_offset_init);
    }

    //为了保证Header滑动速率保持一直，第二段的Header移动距离我们计算出来。
    //第一段移动：header移动了header_offset_first的距离，content移动了news_header_pager_height的距离，toolbar透明了
    //第二段移动：content移动news_tool_bar_height的距离，toolbar也移动news_tool_bar_height距离，header的距离是可以比例计算的。
    private int getHeaderOffsetRangeHideToolBar() {
        return getHeaderOffsetRange() * context.getResources().getDimensionPixelOffset(R.dimen.news_tool_bar_height)
                / context.getResources().getDimensionPixelOffset(R.dimen.news_header_pager_height);
    }
}
