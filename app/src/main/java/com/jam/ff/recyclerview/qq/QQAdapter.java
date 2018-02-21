package com.jam.ff.recyclerview.qq;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jam.ff.recyclerview.R;
import com.jam.ff.recyclerview.adapter.BaseRecyclerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by jamff on 2018/2/15 15:51.
 */
public class QQAdapter extends BaseRecyclerAdapter<QQMessage, QQAdapter.ViewHolder>
        implements ItemTouchMoveListener {

    private StartDragListener mDragListener;

    public QQAdapter(List<QQMessage> list, StartDragListener dragListener) {
        super(list);
        mDragListener = dragListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_qq, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mList.get(position));
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        // 修改数据
        Collections.swap(mList, fromPosition, toPosition);
        // 刷新UI
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public boolean onItemRemove(int position) {
        // 修改数据
        mList.remove(position);
        // 刷新UI
        notifyItemRemoved(position);
        return true;
    }

    class ViewHolder extends BaseRecyclerAdapter<QQMessage, QQAdapter.ViewHolder>.BaseViewHolder {

        private final ImageView iv_logo;
        private final TextView tv_name;
        private final TextView tv_lastMsg;
        private final TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_logo = itemView.findViewById(R.id.iv_logo);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_lastMsg = itemView.findViewById(R.id.tv_lastMsg);
            tv_time = itemView.findViewById(R.id.tv_time);
        }

        @Override
        public void setData(QQMessage qqMessage) {
            iv_logo.setImageResource(qqMessage.getLogo());
            tv_name.setText(qqMessage.getName());
            tv_lastMsg.setText(qqMessage.getLastMsg());
            tv_time.setText(qqMessage.getTime());

            iv_logo.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mDragListener.onStartDrag(ViewHolder.this);
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }
}
