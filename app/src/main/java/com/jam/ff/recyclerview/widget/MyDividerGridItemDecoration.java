package com.jam.ff.recyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jam.ff.recyclerview.R;

/**
 * 绘制RecyclerView，Grid样式的间隔线
 * Created by jamff on 2018/2/8 11:28.
 */

public class MyDividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public MyDividerGridItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.item_divider);
    }

    /**
     * 需要使用position，这里使用过时的方法
     */
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();
        if (isLastColumn(itemPosition, parent)) {
            // 最后一列
            right = 0;
        }
        if (isLastRow(itemPosition, parent)) {
            // 最后一行
            bottom = 0;
        }
        // 右、下添加偏移
        outRect.set(0, 0, right, bottom);
    }

    /**
     * 是否为最后一列
     *
     * @param position 当前item的position
     */
    private boolean isLastColumn(int position, RecyclerView recyclerView) {
        final int spanCount = getSpanCount(recyclerView);
        return spanCount != 0 && (position + 1) % spanCount == 0;
    }

    /**
     * 是否为最后一行
     *
     * @param position 当前item的position
     */
    private boolean isLastRow(int position, RecyclerView recyclerView) {
        final int spanCount = getSpanCount(recyclerView);
        if (spanCount == 0) {
            return false;
        }
        final int childCount = recyclerView.getAdapter().getItemCount();
        if (childCount % spanCount == 0 && position + 1 > childCount - spanCount) {
            // 完整一行时，最后一行
            return true;
        }
        if (childCount / spanCount == position / spanCount) {
            // 不完整一行，最后一行
            return true;
        }
        return false;
    }

    /**
     * 返回列数
     */
    private int getSpanCount(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            return lm.getSpanCount();
        }
        return 0;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null) {
            return;
        }
        final int childCount = parent.getChildCount();
        final int spanCount = getSpanCount(parent);
        if (spanCount == 0) {
            return;
        }
        drawVertical(c, parent, childCount, spanCount);
        drawHorizontal(c, parent, childCount, spanCount);
    }

    // 绘制垂直隔线
    private void drawVertical(Canvas c, RecyclerView parent, int childCount, int spanCount) {

        for (int i = 0; i < childCount; i++) {
            if ((i + 1) % spanCount == 0) {
                // 最后一列
                continue;
            }
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    // 绘制水平间隔线
    private void drawHorizontal(Canvas c, RecyclerView parent, int childCount, int spanCount) {
        for (int i = 0; i < childCount; i++) {
            if (childCount % spanCount == 0 && i == childCount - spanCount) {
                // 完整一行时，最后一行
                break;
            } else if (childCount / spanCount == i / spanCount) {
                // 不完整一行，最后一行
                break;
            }
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int top = child.getBottom() + params.bottomMargin;
            final int right = child.getRight() + params.rightMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
