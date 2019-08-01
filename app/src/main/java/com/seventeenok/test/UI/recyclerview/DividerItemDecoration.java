package com.seventeenok.test.UI.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.seventeenok.test.R;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private int[] attr = {
            android.R.attr.listDivider, R.drawable.item_divider
    };
    private int mOritation;
    private Drawable mDivider;

    public DividerItemDecoration(Context context, int mOritation) {
        TypedArray typedArray = context.obtainStyledAttributes(attr);
        mDivider = typedArray.getDrawable(attr[0]);
        typedArray.recycle();
        setmOritation(mOritation);
    }

    public void setmOritation(int mOritation) {
        if (mOritation != LinearLayoutManager.HORIZONTAL && mOritation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("没有此方向类型");
        }
        this.mOritation = mOritation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //2.调用这个方法  RecyclerView会回调该方法，需要自己绘制间隔线
        if (mOritation == LinearLayoutManager.VERTICAL) {
            drawVertical(c,parent);

        } else {
            drawHorizontal(c,parent);
        }

    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
    }

    private void drawVertical(Canvas c, RecyclerView parent) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //1.设置条目间距  获取条目的偏移量
        if (mOritation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }


    }
}
