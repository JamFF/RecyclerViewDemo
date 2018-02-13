package com.jam.ff.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jam.ff.recyclerview.adapter.BaseRecyclerAdapter;
import com.jam.ff.recyclerview.adapter.MyRecyclerAdapter;
import com.jam.ff.recyclerview.adapter.StaggeredRecyclerAdapter;
import com.jam.ff.recyclerview.bean.DataBean;
import com.jam.ff.recyclerview.widget.MyDividerGridItemDecoration;
import com.jam.ff.recyclerview.widget.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;

    private StaggeredRecyclerAdapter mStaggeredRecyclerAdapter;

    private MyRecyclerAdapter mRecyclerAdapter;

    private List<DataBean> mList = new ArrayList<>();

    private boolean isGrid = false;

    private boolean isHorizontal = false;

    private MyDividerItemDecoration mDecoration;
    private MyDividerGridItemDecoration mGridDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler);
        findViewById(R.id.change).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.change_orientation).setOnClickListener(this);
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            mList.add(new DataBean("item " + i, (int) Math.max(200, Math.random() * 550)));
        }
        setLinearLayout();
    }

    private void setLinearLayout() {
        mRecyclerAdapter = new MyRecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                isHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mDecoration = new MyDividerItemDecoration(this,
                isHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(mDecoration);

        mRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(MainActivity.this, "onClick " + position, Toast.LENGTH_SHORT).show();
                mRecyclerAdapter.add(position);
            }
        });

        mRecyclerAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, "onLongClick " + position, Toast.LENGTH_SHORT).show();
                mRecyclerAdapter.remove(position);
                return true;
            }
        });
    }

    // 瀑布流
    private void setStaggered() {
        mStaggeredRecyclerAdapter = new StaggeredRecyclerAdapter(mList);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mStaggeredRecyclerAdapter);

        mStaggeredRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(MainActivity.this, "onClick " + position, Toast.LENGTH_SHORT).show();
                mStaggeredRecyclerAdapter.add(position);
            }
        });

        mStaggeredRecyclerAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, "onLongClick " + position, Toast.LENGTH_SHORT).show();
                mStaggeredRecyclerAdapter.remove(position);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change:
                if (isGrid) {
                    mRecyclerView.removeItemDecoration(mGridDecoration);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                            isHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL,
                            false));
                    mDecoration = new MyDividerItemDecoration(this,
                            isHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
                    mRecyclerView.addItemDecoration(mDecoration);
                } else {
                    mRecyclerView.removeItemDecoration(mDecoration);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3,
                            isHorizontal ? GridLayoutManager.HORIZONTAL : GridLayoutManager.VERTICAL,
                            false));
                    mGridDecoration = new MyDividerGridItemDecoration(this);
                    mRecyclerView.addItemDecoration(mGridDecoration);
                }
                isGrid = !isGrid;
                break;
            case R.id.change_orientation:

                isHorizontal = !isHorizontal;
                if (isGrid) {
                    mRecyclerView.removeItemDecoration(mGridDecoration);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3,
                            isHorizontal ? GridLayoutManager.HORIZONTAL : GridLayoutManager.VERTICAL,
                            false));
                    mGridDecoration = new MyDividerGridItemDecoration(this);
                    mRecyclerView.addItemDecoration(mGridDecoration);
                } else {
                    mRecyclerView.removeItemDecoration(mDecoration);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                            isHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL,
                            false));
                    mDecoration = new MyDividerItemDecoration(this,
                            isHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
                    mRecyclerView.addItemDecoration(mDecoration);
                }
                break;
            case R.id.add:
                startActivity(new Intent(MainActivity.this, CustomerActivity.class));
                break;
        }
    }
}
