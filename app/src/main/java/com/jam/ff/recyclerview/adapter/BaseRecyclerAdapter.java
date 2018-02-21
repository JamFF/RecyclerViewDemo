package com.jam.ff.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerAdapter的基类
 * Created by jamff on 2018/2/4 11:05.
 */
public abstract class BaseRecyclerAdapter<T, VH extends BaseRecyclerAdapter.BaseViewHolder> extends RecyclerView.Adapter<VH> {

    public BaseRecyclerAdapter(List<T> list) {
        mList = list;
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        boolean onLongClick(View v, int position);
    }

    protected OnItemClickListener mItemClickListener;

    protected OnItemLongClickListener mItemLongClickListener;

    protected List<T> mList;

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mItemLongClickListener = listener;
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setData(T t);
    }
}
