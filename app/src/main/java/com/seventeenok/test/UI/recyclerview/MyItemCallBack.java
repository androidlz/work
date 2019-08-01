package com.seventeenok.test.UI.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.seventeenok.test.R;

public class MyItemCallBack extends ItemTouchHelper.Callback {

    private ItemMoveListener itemMoveListener;

    public MyItemCallBack(ItemMoveListener itemMoveListener) {
        this.itemMoveListener = itemMoveListener;
    }

    //call回调监听先回调的方法  用来判断什么动作 比如判断方向（那个方向的拖拽）
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // left=1<<2  right=1<<3  up=1 down=1<<1
//        int dragFlags = 0;
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swpipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//        int swipeFlags = 0;
        int flags = makeMovementFlags(dragFlags, swpipeFlags);
        return flags;
    }

    //移动的时候调用  拖拽
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        boolean b = itemMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return b;
    }

    //侧滑的时候
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        //判断选中状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.colorAccent));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //松开手恢复颜色
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
        viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleX(1);
        viewHolder.itemView.setScaleY(1);
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //dx 水平方向增量
        float alpha = 0;

//        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
//
//        } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
        //
//            alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
//            viewHolder.itemView.setAlpha(alpha);
//            viewHolder.itemView.setScaleX(alpha);
//            viewHolder.itemView.setScaleY(alpha);

//        }
//        if (alpha == 0) {
//            viewHolder.itemView.setAlpha(1);
//            viewHolder.itemView.setScaleX(1);
//            viewHolder.itemView.setScaleY(1);
//        }

        if (Math.abs(dX) <= viewHolder.itemView.getWidth() / 2) {
            viewHolder.itemView.setTranslationX(-0.5f * viewHolder.itemView.getWidth());
        }else{
            viewHolder.itemView.setTranslationX(dX);
        }
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
