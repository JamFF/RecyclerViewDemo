package com.jam.ff.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jam.ff.recyclerview.adapter.BaseRecyclerAdapter;
import com.jam.ff.recyclerview.adapter.MyRecyclerAdapter;
import com.jam.ff.recyclerview.bean.DataBean;
import com.jam.ff.recyclerview.widget.MyDividerGridItemDecoration;
import com.jam.ff.recyclerview.widget.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private MyRecyclerAdapter mRecyclerAdapter;

    private List<DataBean> mList = new ArrayList<>();

    private boolean isGrid = false;

    private MyDividerItemDecoration mDecoration;// Linear样式

    private MyDividerGridItemDecoration mGridDecoration;// Grid样式间隔线

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        findViewById(R.id.btn_change).setOnClickListener(this);
        findViewById(R.id.btn_staggered).setOnClickListener(this);
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            mList.add(new DataBean("item " + i, (int) Math.max(200, Math.random() * 550)));
        }
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerAdapter = new MyRecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mDecoration = new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mGridDecoration = new MyDividerGridItemDecoration(this);

        mRecyclerView.addItemDecoration(mDecoration);

        mRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(RecyclerViewActivity.this, "onClick " + position, Toast.LENGTH_SHORT).show();
                mRecyclerAdapter.add(position);
            }
        });

        mRecyclerAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onLongClick(View v, int position) {
                Toast.makeText(RecyclerViewActivity.this, "onLongClick " + position, Toast.LENGTH_SHORT).show();
                mRecyclerAdapter.remove(position);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                if (isGrid) {
                    mRecyclerView.removeItemDecoration(mGridDecoration);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                            LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.addItemDecoration(mDecoration);
                } else {
                    mRecyclerView.removeItemDecoration(mDecoration);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3,
                            GridLayoutManager.VERTICAL, false));
                    mRecyclerView.addItemDecoration(mGridDecoration);
                }
                isGrid = !isGrid;
                break;
            case R.id.btn_staggered:
                startActivity(new Intent(this, StaggeredActivity.class));
                break;
            default:
                break;
        }
    }
}
