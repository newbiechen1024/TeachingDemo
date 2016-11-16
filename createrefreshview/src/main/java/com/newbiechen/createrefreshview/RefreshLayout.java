package com.newbiechen.createrefreshview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.newbiechen.createrefreshview.widget.BaseRefreshLayout;

import java.util.ArrayList;

/**
 * Created by PC on 2016/11/16.
 */

public class RefreshLayout extends BaseRefreshLayout<ListView> {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public ListView getContentView(Context context) {
        mListView = new ListView(context);
        ArrayList<String> strings = new ArrayList<>();
        for(int i=0; i<20; ++i){
            strings.add("测试+"+i);
        }
        mAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,strings);
        mListView.setAdapter(mAdapter);
        return mListView;
    }

    @Override
    public boolean isTop() {
        if (mListView.getFirstVisiblePosition() == 0){
            return true;
        }
        else {
            return false;
        }
    }
}
