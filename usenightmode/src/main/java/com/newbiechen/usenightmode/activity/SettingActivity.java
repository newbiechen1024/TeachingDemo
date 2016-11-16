package com.newbiechen.usenightmode.activity;

import android.os.Bundle;

import com.newbiechen.usenightmode.R;
import com.newbiechen.usenightmode.base.BaseActivity;

/**
 * Created by PC on 2016/11/5.
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        //Toolbar显示的名字
        mToolbar.setTitle(R.string.setting);
    }

    @Override
    protected void initClick() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
