package com.jam.ff.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_recyclerView).setOnClickListener(this);
        findViewById(R.id.btn_customer).setOnClickListener(this);
        findViewById(R.id.btn_header).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recyclerView:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.btn_customer:
                startActivity(new Intent(this, CustomerActivity.class));
                break;
            case R.id.btn_header:
                startActivity(new Intent(this, HeaderViewActivity.class));
                break;
            default:
                break;
        }
    }
}
