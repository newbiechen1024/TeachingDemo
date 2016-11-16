package com.newbiechen.usenightmode.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.newbiechen.usenightmode.MyApplication;
import com.newbiechen.usenightmode.R;
import com.newbiechen.usenightmode.base.BaseActivity;

/**
 * Created by PC on 2016/11/5.
 */

public class ArticleInfoActivity extends BaseActivity {

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
/*        //判断当前的Theme
        if(MyApplication.isNightMode){
            setTheme(R.style.NightMode);
        }
        else {
            setTheme(R.style.DayMode);
        }*/
        setContentView(R.layout.activity_article_info);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {

    }

    @Override
    protected void initClick() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
