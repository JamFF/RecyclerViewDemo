package com.jam.ff.recyclerview.qq;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jam.ff.recyclerview.R;

/**
 * 使用ItemTouchHelper实现拖拽和侧滑删除
 * Created by jamff on 2018/2/15 17:18.
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "MyItemTouchHelper";

    private final ItemTouchMoveListener mMoveListener;

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
        mMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 长按移动，默认true
        return super.isLongPressDragEnabled();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        // 侧滑删除，默认true
        return super.isItemViewSwipeEnabled();
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (viewHolder != null) {
            // 判断选中状态
            switch (actionState) {
                case ItemTouchHelper.ACTION_STATE_SWIPE:
                    viewHolder.itemView.setBackgroundResource(android.R.color.darker_gray);
                    break;
                case ItemTouchHelper.ACTION_STATE_DRAG:
                    viewHolder.itemView.setBackgroundResource(R.color.colorPrimary);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        // dX:水平方向移动的增量（负：往左；正：往右）范围：0~View.getWidth
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // 透明度动画
            float alpha = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);// 1~0
            viewHolder.itemView.setScaleX(alpha);// 1~0
            viewHolder.itemView.setScaleY(alpha);// 1~0
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        // 当完成onMove或onSwiped后，会回调该方法
        // 在这里进行恢复
        viewHolder.itemView.setBackgroundResource(android.R.color.white);
        viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleX(1);
        viewHolder.itemView.setScaleY(1);
    }
}
