package com.jam.ff.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jam.ff.recyclerview.R;

import java.util.List;

/**
 * Created by jamff on 2018/2/14 20:42.
 */
public class MyAdapter extends BaseRecyclerAdapter<String> {

    public MyAdapter(List<String> list) {
        super(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.ViewHolder holder, int position) {
        holder.setData(mList.get(position));
    }

    class ViewHolder extends BaseRecyclerAdapter<String>.ViewHolder {

        public TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }

        @Override
        public void setData(String s) {
            tv.setText(s);
        }
    }
}
