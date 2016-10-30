package com.newbiechen.usecoordinator.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.newbiechen.usecoordinator.widget.MoveTextView;

/**
 * Created by PC on 2016/10/17.
 */

public class MoveBehavior extends CoordinatorLayout.Behavior<View> {

    public MoveBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof MoveTextView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(dependency.getY());
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
