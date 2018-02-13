package com.jam.ff.recyclerview.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jam.ff.recyclerview.R;
import com.jam.ff.recyclerview.bean.DataBean;

import java.util.List;

/**
 * LinearLayout和GridLayout样式
 * Created by jamff on 2018/2/4 11:05.
 */

public class MyRecyclerAdapter extends BaseRecyclerAdapter<DataBean> {

    public MyRecyclerAdapter(List<DataBean> list) {
        super(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                // 注意这里不要使用图方便，使用View.inflate()，该方法设置不了attachToRoot
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item, parent, false
                )
                /*LayoutInflater.from(parent.getContext()).inflate(
                        android.R.layout.simple_list_item_1, parent, false
                )*/
        );
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerAdapter.ViewHolder holder, int position) {
        holder.setData(mList.get(position));
        if (mItemClickListener != null) {
            // 避免点击事件错位，可以将position传入，保证位置准确
            // holder.itemView.setOnClickListener(new MyOnClickListener(position));
            // 但是当增加和删除条目时不要使用，真实position会改变，但是保存的变量pos不会改变
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // AS建议不要使用position，使用holder.getAdapterPosition()更为准确
                    mItemClickListener.onClick(v, holder.getAdapterPosition());
                }
            });
        }
        if (mItemLongClickListener != null) {
            // holder.itemView.setOnLongClickListener(new MyOnClickListener(position));
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemLongClickListener.onLongClick(v, holder.getAdapterPosition());
                    return true;
                }
            });
        }
    }

    class ViewHolder extends BaseRecyclerAdapter<DataBean>.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            // tv = itemView.findViewById(android.R.id.text1);
        }

        final TextView tv;

        @Override
        public void setData(DataBean dataBean) {
            tv.setText(dataBean.getText());
            tv.setBackgroundColor(Color.rgb(100,
                    (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }
    }

    /**
     * 当有item删除，插入时，不要使用，position不会改变
     * 当没有item变化时，建议使用，防止position错位
     */
    class MyOnClickListener implements View.OnClickListener, View.OnLongClickListener {

        private int pos;

        MyOnClickListener(int pos) {
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onClick(v, pos);
        }

        @Override
        public boolean onLongClick(View v) {
            return mItemLongClickListener.onLongClick(v, pos);
        }
    }

    /**
     * 增加item
     */
    public void add(int position) {
        mList.add(position, new DataBean("additem " + position, 200));
        notifyItemInserted(position);
    }

    /**
     * 移除item
     */
    public void remove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }
}
