package com.jam.ff.recyclerview.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jam.ff.recyclerview.R;
import com.jam.ff.recyclerview.StaggeredActivity;
import com.jam.ff.recyclerview.adapter.BaseRecyclerAdapter;
import com.jam.ff.recyclerview.adapter.MyRecyclerAdapter;
import com.jam.ff.recyclerview.bean.DataBean;
import com.jam.ff.recyclerview.widget.MyDividerGridItemDecoration;
import com.jam.ff.recyclerview.widget.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamff on 2018/2/25 09:48.
 */
public class RecyclerViewFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    private RecyclerView mRecyclerView;

    private MyRecyclerAdapter mRecyclerAdapter;

    private List<DataBean> mList = new ArrayList<>();

    private boolean isGrid = false;

    private MyDividerItemDecoration mDecoration;// Linear样式

    private MyDividerGridItemDecoration mGridDecoration;// Grid样式间隔线

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view,
                container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.btn_change).setOnClickListener(this);
        view.findViewById(R.id.btn_staggered).setOnClickListener(this);

        initData();
        return view;
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            mList.add(new DataBean("item " + i, (int) Math.max(200, Math.random() * 550)));
        }
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerAdapter = new MyRecyclerAdapter(mList);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mDecoration = new MyDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        mGridDecoration = new MyDividerGridItemDecoration(mContext);

        mRecyclerView.addItemDecoration(mDecoration);

        mRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(mContext, "onClick " + position, Toast.LENGTH_SHORT).show();
                mRecyclerAdapter.addItem(position);
            }
        });

        mRecyclerAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onLongClick(View v, int position) {
                Toast.makeText(mContext, "onLongClick " + position, Toast.LENGTH_SHORT).show();
                mRecyclerAdapter.removeItem(position);
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
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                            LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.addItemDecoration(mDecoration);
                } else {
                    mRecyclerView.removeItemDecoration(mDecoration);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3,
                            GridLayoutManager.VERTICAL, false));
                    mRecyclerView.addItemDecoration(mGridDecoration);
                }
                isGrid = !isGrid;
                break;
            case R.id.btn_staggered:
                startActivity(new Intent(mContext, StaggeredActivity.class));
                break;
            default:
                break;
        }
    }
}
