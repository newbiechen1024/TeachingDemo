package com.newbiechen.usecoordinator.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by PC on 2016/10/18.
 */

public class ScrollBehavior extends CoordinatorLayout.Behavior<View>{

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        //表示：判断当前滑动是否为竖直方向上的滑动，如果是则接受该滑动
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //获取被监听的ScrollView的内部Y的偏移量
        int scrollY = target.getScrollY();
        //将被监听View的Y的偏移量赋值给当前View
        child.setScrollY(scrollY);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        //表示child自动滑动的速度与被监听的View的速度相同
        ((NestedScrollView) child).fling((int) velocityY);
        return true;
    }
}
