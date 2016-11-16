package com.newbiechen.usestyle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startFirstActivity(View v){
        Intent intent = new Intent(this,FirstActivity.class);
        startActivity(intent);
    }

    public void startSecondActivity(View v){
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}
