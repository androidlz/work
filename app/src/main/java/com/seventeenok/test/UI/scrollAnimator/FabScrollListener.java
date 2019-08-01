package com.seventeenok.test.UI.scrollAnimator;

import androidx.recyclerview.widget.RecyclerView;

public class FabScrollListener extends RecyclerView.OnScrollListener {
    public static final int THREHOLD = 20;
    private int distance = 0;
    private HideScorllListener hideScorllListener;
    private boolean visible = true;

    public FabScrollListener(HideScorllListener hideScorllListener) {
        this.hideScorllListener = hideScorllListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (distance > THREHOLD && visible) {
            //隐藏动画
            visible = false;
            hideScorllListener.onHide();
            distance = 0;
        } else if (distance < -20 && !visible) {
            //显示动画
            visible = true;
            hideScorllListener.onShow();
            distance = 0;
        }
        if (visible && dy > 0 || !visible && dy < 0) {
            distance += dy;
        }
    }

    public interface HideScorllListener {
        void onHide();

        void onShow();
    }
}
