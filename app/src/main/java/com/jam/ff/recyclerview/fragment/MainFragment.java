package com.jam.ff.recyclerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jam.ff.recyclerview.R;

/**
 * Created by jamff on 2018/2/24 15:34.
 */
public class MainFragment extends ListFragment {

    public interface OnListItemClickListener {
        void onListItemClick(int position);
    }

    private OnListItemClickListener mItemClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListItemClickListener) {
            mItemClickListener = (OnListItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListItemClickListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String[] items = getResources().getStringArray(R.array.main_items);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mItemClickListener != null) {
            mItemClickListener.onListItemClick(position);
        }
    }
}
