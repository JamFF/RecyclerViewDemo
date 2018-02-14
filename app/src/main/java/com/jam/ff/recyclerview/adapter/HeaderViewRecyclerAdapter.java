package com.jam.ff.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 内部的Adapter，装饰者模式，注意这里ViewHolder不能使用反省
 * Created by jamff on 2018/2/13 23:04.
 */
public class HeaderViewRecyclerAdapter extends RecyclerView.Adapter {

    private static final int HEADER_TYPE = -1;

    private static final int FOOTER_TYPE = -2;

    private RecyclerView.Adapter mAdapter;

    private ArrayList<View> mHeaderViews;

    private ArrayList<View> mFooterViews;

    public HeaderViewRecyclerAdapter(ArrayList<View> headerViews,
                                     ArrayList<View> footerViews,
                                     RecyclerView.Adapter adapter) {
        mAdapter = adapter;

        if (headerViews == null) {
            mHeaderViews = new ArrayList<>();
        } else {
            mHeaderViews = headerViews;
        }

        if (footerViews == null) {
            mFooterViews = new ArrayList<>();
        } else {
            mFooterViews = footerViews;
        }
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
        } else {
            return getFootersCount() + getHeadersCount();
        }
    }

    private int getHeadersCount() {
        return mHeaderViews.size();
    }

    private int getFootersCount() {
        return mFooterViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            // 头部
            return HEADER_TYPE;
        }

        // 正常部分
        final int adjPosition = position - numHeaders;
        if (mAdapter != null) {
            if (adjPosition < mAdapter.getItemCount()) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        // 底部
        return FOOTER_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            // 传入的view没有任何意义，只是RecyclerView.Adapter结构要求
            return new HeadViewHolder(mHeaderViews.get(0));
        }

        if (viewType == FOOTER_TYPE) {
            // 传入的view没有任何意义，只是RecyclerView.Adapter结构要求
            return new HeadViewHolder(mFooterViews.get(0));
        }
        if (mAdapter != null) {
            // 由于头部、底部以及中间的ViewHolder，不是同一个，所以上面没有使用泛型
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            // 头部
            return;
        }

        // 正常部分
        final int adjPosition = position - numHeaders;
        if (mAdapter != null) {
            if (adjPosition < mAdapter.getItemCount()) {
                mAdapter.onBindViewHolder(holder, adjPosition);
            }
        }
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
