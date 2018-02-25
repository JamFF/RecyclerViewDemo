package com.jam.ff.recyclerview.qq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jam.ff.recyclerview.R;

import java.util.List;

public class QQActivity extends AppCompatActivity implements StartDragListener {

    private RecyclerView mRecyclerView;

    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        List<QQMessage> list = DataUtils.init();
        QQAdapter adapter = new QQAdapter(list, this);
        mRecyclerView.setAdapter(adapter);

        // item触摸帮助类
        mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(adapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        // 触摸头像主动回调，拖拽效果的
        mItemTouchHelper.startDrag(viewHolder);
        // 类似的还有mItemTouchHelper.startSwipe(viewHolder);
    }
}
