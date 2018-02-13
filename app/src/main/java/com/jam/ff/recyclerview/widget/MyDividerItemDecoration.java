package com.jam.ff.recyclerview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 绘制RecyclerView的间隔线
 * android.support.v7.widget.DividerItemDecoration有写好的分割线
 * Created by jamff on 2018/2/6 09:07.
 */

public class MyDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "MyDividerItemDecoration";

    // 使用list的分割线样式，可以在styles中AppTheme替换listDivider的样式
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private int mOrientation = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    public MyDividerItemDecoration(Context context, int orientation) {

        final TypedArray t = context.obtainStyledAttributes(ATTRS);
        try {
            mDivider = t.getDrawable(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            t.recycle();
        }
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.HORIZONTAL &&
                orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 1.调用此方法，首先会获取条目之间的间隔距离——Rect矩形区域
        // 获得条目的偏移量，所有条目都会调用一次该方法
        // super里面做的outRect.set(0, 0, 0, 0);
        // super.getItemOffsets(outRect, view, parent, state);
        if (mDivider == null) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            // 向下偏移
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            // 向右偏移
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // 2.调用这个绘制方法，RecyclerView会回调该方法，需要自己去绘制条目的间隔线
        // 如果只调用1，不调用该方法，只会留出间隔，并不会有间隔线
        // super里面是空方法
        // super.onDraw(c, parent, state);
        if (mDivider == null) {
            return;
        }
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    // 画水平分割线
    private void drawVertical(Canvas c, RecyclerView parent) {

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            // 获取item相对于父控件RecyclerView的属性
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // item底部的距离父控件的距离+item的layout_marginBottom+item移动时的偏移量
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(child.getTranslationY());

            final int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    // 画垂直分割线
    private void drawHorizontal(Canvas c, RecyclerView parent) {

        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            final int left = child.getRight() + params.rightMargin +
                    Math.round(child.getTranslationX());

            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
