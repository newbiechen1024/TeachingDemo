package com.newbiechen.usecoordinator;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.newbiechen.usecoordinator.widget.MoveTextView;

public class MainActivity extends AppCompatActivity {
    private MoveTextView mTvLeft;
    private TextView mTvRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_3);

        findViewById(R.id.fab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(v,"Hello EveryOne",Snackbar.LENGTH_LONG)
                                .setAction("cancel", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //点击事件
                                    }
                                }).show();
                    }
                });

       /* mTvLeft = (MoveTextView) findViewById(R.id.main_tv_left);
        mTvRight = (TextView) findViewById(R.id.main_tv_right);
        initClick();*/
    }

    private void initClick(){
        //设置监听的回调
        mTvLeft.setOnMoveListener(new MoveTextView.OnMoveListener() {
            @Override
            public void onMove(int y) {
                mTvRight.setY(y);
            }
        });
    }
}
