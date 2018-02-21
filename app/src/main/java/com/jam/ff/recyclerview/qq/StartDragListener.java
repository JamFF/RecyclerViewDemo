package com.jam.ff.recyclerview.qq;

import android.support.v7.widget.RecyclerView;

/**
 * 该接口用于需要主动回调拖拽效果的
 * Created by jamff on 2018/2/21 19:12.
 */

public interface StartDragListener {

    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
