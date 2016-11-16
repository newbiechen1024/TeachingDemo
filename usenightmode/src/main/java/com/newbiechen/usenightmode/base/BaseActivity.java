package com.newbiechen.usenightmode.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.newbiechen.usenightmode.R;
import com.newbiechen.usenightmode.network.RemoteService;

/**
 * Created by PC on 2016/11/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected RemoteService mRemoteService;
    protected Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(savedInstanceState);
        mRemoteService = RemoteService.getInstance();
        initWidget(savedInstanceState);
        setUpToolBar();
        initClick();
        processLogic(savedInstanceState);
    }


    private void setUpToolBar(){
        //获取Toolbar
        mToolbar = getViewById(R.id.toolbar);
        //标题的颜色的白色
        mToolbar.setTitleTextColor(Color.WHITE);
        //与Activity关联
        setSupportActionBar(mToolbar);
        //允许显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置返回键的监听
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /************************需要继承的抽象类************************************/
    /**
     * 初始化View
     */
    protected abstract void onCreateView(Bundle savedInstanceState);

    /**
     * 初始化零件
     */
    protected abstract void initWidget(Bundle savedInstanceState);
    /**
     * 初始化点击事件
     */
    protected abstract void initClick();
    /**
     * 逻辑使用区
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**************************公共类*******************************************/
    public <VT> VT getViewById(int id){
        return (VT) findViewById(id);
    }

    public void startActivity(Class<? extends AppCompatActivity> activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }
}

