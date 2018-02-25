package com.jam.ff.recyclerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jam.ff.recyclerview.adapter.MyAdapter;
import com.jam.ff.recyclerview.widget.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * HeaderView、FooterView
 * Created by jamff on 2018/2/24 17:42.
 */
public class HeaderViewFragment extends Fragment {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        WrapRecyclerView recyclerView = new WrapRecyclerView(mContext);
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView headerView = new TextView(mContext);
        headerView.setLayoutParams(params);
        headerView.setText("HeaderView");
        recyclerView.addHeaderView(headerView);

        TextView footerView = new TextView(mContext);
        footerView.setLayoutParams(params);
        footerView.setText("FooterView");
        recyclerView.addFooterView(footerView);

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            list.add("item " + i);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new MyAdapter(list));
        return recyclerView;
    }
}
