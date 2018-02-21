package com.jam.ff.recyclerview.qq;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * 使用ItemTouchHelper实现拖拽和侧滑删除
 * Created by jamff on 2018/2/15 17:18.
 */

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "MyItemTouchHelper";

    private ItemTouchMoveListener mMoveListener;

    public MyItemTouchHelperCallback(ItemTouchMoveListener moveListener) {
        mMoveListener = moveListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() == target.getItemViewType()) {
            return mMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        } else {
            // 例如头部、底部不允许移动
            return false;
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG, "onSwiped: direction = " + direction);
        mMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 长按移动，默认true
        return super.isLongPressDragEnabled();
    }
}
