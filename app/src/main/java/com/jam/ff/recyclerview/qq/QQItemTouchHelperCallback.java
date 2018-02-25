package com.jam.ff.recyclerview.qq;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ViewGroup;

import com.jam.ff.recyclerview.R;

/**
 * 仿QQ侧滑删除
 * Created by jamff on 2018/2/15 17:18.
 */
public class QQItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "QQItemTouchHelper";

    private final ItemTouchMoveListener mMoveListener;

    public QQItemTouchHelperCallback(ItemTouchMoveListener moveListener) {
        mMoveListener = moveListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d(TAG, "onMove: ");
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

    @Override
    public boolean isItemViewSwipeEnabled() {
        // 侧滑删除，默认true
        return super.isItemViewSwipeEnabled();
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        Log.d(TAG, "onSelectedChanged: ");
        super.onSelectedChanged(viewHolder, actionState);
        if (viewHolder != null) {
            // 判断选中状态
            switch (actionState) {
                case ItemTouchHelper.ACTION_STATE_SWIPE:
                    // viewHolder.itemView.setBackgroundResource(android.R.color.darker_gray);
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
        // dX:水平方向移动的增量（负：往左；正：往右）范围：0~View.getWidth
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            int slideLimitation = getSlideLimitation(viewHolder);
            if (-dX < slideLimitation) {
                // 如果dX小于等于删除方块的宽度，那么我们把该方块滑出来
                viewHolder.itemView.scrollTo(-(int) dX, 0);
            } else if (-dX < recyclerView.getWidth() / 2) {
                ((QQAdapter.ViewHolder) viewHolder).tv_delete.setText("继续右滑");
                viewHolder.itemView.scrollTo(slideLimitation, 0);
                ((QQAdapter.ViewHolder) viewHolder).tv_delete.setScaleX(-dX / getSlideLimitation(viewHolder));
                ((QQAdapter.ViewHolder) viewHolder).tv_delete.setScaleY(-dX / getSlideLimitation(viewHolder));
            } else {
                ((QQAdapter.ViewHolder) viewHolder).tv_delete.setText("松手删除");
                viewHolder.itemView.scrollTo(slideLimitation, 0);
                ((QQAdapter.ViewHolder) viewHolder).tv_delete.setScaleX((recyclerView.getWidth() / 2.0f) / getSlideLimitation(viewHolder));
                ((QQAdapter.ViewHolder) viewHolder).tv_delete.setScaleY((recyclerView.getWidth() / 2.0f) / getSlideLimitation(viewHolder));
            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.d(TAG, "clearView: ");
        // 恢复
        viewHolder.itemView.setBackgroundResource(android.R.color.white);
        viewHolder.itemView.setScrollX(0);
        ((QQAdapter.ViewHolder) viewHolder).tv_delete.setText("右滑删除");
        ((QQAdapter.ViewHolder) viewHolder).tv_delete.setScaleX(1f);
        ((QQAdapter.ViewHolder) viewHolder).tv_delete.setScaleY(1f);
    }

    /**
     * 获取删除方块的宽度
     */
    private int getSlideLimitation(RecyclerView.ViewHolder viewHolder) {
        ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
        return viewGroup.getChildAt(2).getLayoutParams().width;
    }
}
