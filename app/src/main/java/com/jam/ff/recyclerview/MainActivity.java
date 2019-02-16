package com.jam.ff.recyclerview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jam.ff.recyclerview.fragment.CustomViewFragment;
import com.jam.ff.recyclerview.fragment.HeaderViewFragment;
import com.jam.ff.recyclerview.fragment.MainFragment;
import com.jam.ff.recyclerview.fragment.QQFragment;
import com.jam.ff.recyclerview.fragment.RecyclerViewFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnListItemClickListener {

    private FrameLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRoot = new FrameLayout(this);
        mRoot.setId(View.generateViewId());// API 17以上
        mRoot.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mRoot);

        if (savedInstanceState == null) {
            MainFragment fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(mRoot.getId(), fragment)
                    .commit();
        }
    }

    @Override
    public void onListItemClick(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new RecyclerViewFragment();
                break;
            case 1:
                fragment = new CustomViewFragment();
                break;
            case 2:
                fragment = new HeaderViewFragment();
                break;
            case 3:
                fragment = new QQFragment();
                break;
            default:
                return;

        }
        getSupportFragmentManager().beginTransaction()
                .replace(mRoot.getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
}
