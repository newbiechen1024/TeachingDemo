package com.newbiechen.usenightmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newbiechen.usenightmode.Utils.UrlManager;
import com.newbiechen.usenightmode.entity.ArticleBrief;
import com.newbiechen.usenightmode.network.RemoteService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

import static com.newbiechen.usenightmode.network.RemoteService.*;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArticleBriefAdapter mAdapter;

    private RemoteService mRemoteService;
    private Gson mGson;
    //初始的页数
    private int mPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initWidget();
    }

    private void initView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        mAdapter = new ArticleBriefAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initWidget(){
        //获取网络请求工具
        mRemoteService = RemoteService.getInstance();
        mGson = new Gson();
        getData();
    }


    public void getData(){
        RemoteCallBack<List<ArticleBrief>> callBack = new RemoteCallBack<List<ArticleBrief>>() {
            @Override
            public List<ArticleBrief> parse(Response response) {
                List<ArticleBrief> data = new ArrayList<>();
                try {
                    data = mGson.fromJson(response.body().string(),
                            new TypeToken<List<ArticleBrief>>(){}.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return data;
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this,"网络连接失败", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onResponse(List<ArticleBrief> data) {
                mPage += 1;
                mAdapter.addItems(data);
            }
        };
        //设置url
        String url = UrlManager.ARTICLE_BRIEF.replace("X", mPage +"");
        //连接网络
        mRemoteService.connHttp(url,callBack);
    }
}
