package com.seventeenok.test.UI.custom;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public class CustomBehavior extends CoordinatorLayout.Behavior<View> {
    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     * 用来决定哪些控件需要监听
     * */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        /*
         * child ：观察者View
         * dependency: 被观察者View
         * */
        return dependency.getTag().equals("tv") || super.layoutDependsOn(parent, child, dependency);
    }

    /*
     *
     * */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int offset = dependency.getTop() - child.getTop();
        ViewCompat.offsetTopAndBottom(child, offset);
        child.animate().rotation(child.getTop()*15);
        return true;
    }

}
