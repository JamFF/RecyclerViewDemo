package com.jam.ff.recyclerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jam.ff.recyclerview.R;
import com.jam.ff.recyclerview.qq.DataUtils;
import com.jam.ff.recyclerview.qq.QQAdapter;
import com.jam.ff.recyclerview.qq.QQItemTouchHelperCallback;
import com.jam.ff.recyclerview.qq.QQMessage;
import com.jam.ff.recyclerview.qq.StartDragListener;

import java.util.List;

/**
 * Created by jamff on 2018/2/25 10:37.
 */
public class QQFragment extends Fragment implements StartDragListener {

    private Context mContext;

    private RecyclerView mRecyclerView;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qq, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        initData();
        return view;
    }

    private void initData() {
        List<QQMessage> list = DataUtils.init();
        QQAdapter adapter = new QQAdapter(list, this);
        mRecyclerView.setAdapter(adapter);

        // item触摸帮助类
        // mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(adapter));
        mItemTouchHelper = new ItemTouchHelper(new QQItemTouchHelperCallback(adapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        // 触摸头像主动回调，拖拽效果的
        mItemTouchHelper.startDrag(viewHolder);
        // 类似的还有mItemTouchHelper.startSwipe(viewHolder);
    }
}
