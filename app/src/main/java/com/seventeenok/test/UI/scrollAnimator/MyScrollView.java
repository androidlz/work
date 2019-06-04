package com.seventeenok.test.UI.scrollAnimator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            //获取屏幕高度
            int screen_height = getContext().getResources().getDisplayMetrics().heightPixels;
            int scrollY = getScrollY();
            if (scrollY <= screen_height / 3f) {
                onScrollListener.onChangeAlpha(1 - scrollY / (screen_height / 3f));
            }
        }
    }
}
