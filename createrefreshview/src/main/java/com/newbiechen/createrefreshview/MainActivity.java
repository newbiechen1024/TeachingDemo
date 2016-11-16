package com.newbiechen.createrefreshview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newbiechen.createrefreshview.widget.BaseRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RefreshLayout layout = (RefreshLayout) findViewById(R.id.main_refresh_layout);
        layout.setOnRefreshListener(new BaseRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.refreshFinish();
                    }
                },2000);
            }
        });
    }
}
