package com.jam.ff.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jam.ff.recyclerview.adapter.BaseRecyclerAdapter;
import com.jam.ff.recyclerview.adapter.StaggeredRecyclerAdapter;
import com.jam.ff.recyclerview.bean.DataBean;
import com.jam.ff.recyclerview.widget.MyDividerStaggeredItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 瀑布流
 * Created by jamff on 2018/2/13 16:17.
 */
public class StaggeredActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private StaggeredRecyclerAdapter mStaggeredRecyclerAdapter;

    private List<DataBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler);
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            mList.add(new DataBean("item " + i, (int) Math.max(200, Math.random() * 550)));
        }
        setStaggered();
    }

    private void setStaggered() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        mStaggeredRecyclerAdapter = new StaggeredRecyclerAdapter(mList);
        mRecyclerView.setAdapter(mStaggeredRecyclerAdapter);

        mRecyclerView.addItemDecoration(new MyDividerStaggeredItemDecoration(this));

        mStaggeredRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(StaggeredActivity.this, "onClick " + position, Toast.LENGTH_SHORT).show();
                mStaggeredRecyclerAdapter.add(position);
            }
        });

        mStaggeredRecyclerAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onLongClick(View v, int position) {
                Toast.makeText(StaggeredActivity.this, "onLongClick " + position, Toast.LENGTH_SHORT).show();
                mStaggeredRecyclerAdapter.remove(position);
                return true;
            }
        });
    }
}
