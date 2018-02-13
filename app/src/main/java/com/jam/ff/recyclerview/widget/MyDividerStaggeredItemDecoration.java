package com.jam.ff.recyclerview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jam.ff.recyclerview.R;

/**
 * 绘制RecyclerView，Grid样式的间隔线
 * Created by jamff on 2018/2/8 11:28.
 */

public class MyDividerStaggeredItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public MyDividerStaggeredItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.item_divider);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null) {
            return;
        }
        final int childCount = parent.getChildCount();
        drawVertical(c, parent, childCount);
        drawHorizontal(c, parent, childCount);
    }

    // 绘制垂直隔线
    private void drawVertical(Canvas c, RecyclerView parent, int childCount) {

        for (int i = 0; i < childCount; i++) {
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
    private void drawHorizontal(Canvas c, RecyclerView parent, int childCount) {
        for (int i = 0; i < childCount; i++) {
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
