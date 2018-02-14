package com.jam.ff.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jam.ff.recyclerview.adapter.MyAdapter;
import com.jam.ff.recyclerview.widget.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HeaderViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_view);
        WrapRecyclerView recyclerView = findViewById(R.id.recyclerView);

        TextView headerView = new TextView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(params);
        headerView.setText("HeaderView");
        recyclerView.addHeaderView(headerView);

        TextView footerView = new TextView(this);
        footerView.setLayoutParams(params);
        footerView.setText("FooterView");
        recyclerView.addFooterView(footerView);

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            list.add("item " + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));
    }
}
