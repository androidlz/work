package com.seventeenok.test.UI.recyclerview;

public interface ItemMoveListener {
    boolean onItemMove(int fromPosition, int toPosition);

    boolean onItemRemove(int positon);
}
