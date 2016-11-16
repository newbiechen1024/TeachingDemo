package com.newbiechen.usenightmode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newbiechen.usenightmode.Utils.NightModeHelper;
import com.newbiechen.usenightmode.Utils.SharedPreUtils;
import com.newbiechen.usenightmode.Utils.UrlManager;
import com.newbiechen.usenightmode.activity.ArticleInfoActivity;
import com.newbiechen.usenightmode.activity.SettingActivity;
import com.newbiechen.usenightmode.entity.ArticleBrief;
import com.newbiechen.usenightmode.network.RemoteService;
import com.newbiechen.usenightmode.widget.AutoLoadingRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

import static com.newbiechen.usenightmode.network.RemoteService.*;

public class MainActivity extends AppCompatActivity {

    private AutoLoadingRecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ArticleBriefAdapter mAdapter;

    private NightModeHelper mNightModeHelper;
    private RemoteService mRemoteService;
    private RemoteCallback<List<ArticleBrief>> mCallback;
    private Gson mGson;
    //初始的页数
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        //设置当前的Theme
        if (MyApplication.isNightMode){
            setTheme(R.style.NightMode);
        }
        else {
            setTheme(R.style.DayMode);
        }*/
        mNightModeHelper = new NightModeHelper(this,R.style.AppTheme);
        setContentView(R.layout.activity_main);

        initView();
        initWidget();
        initListener();
    }

    private void initView(){
        mRecyclerView = (AutoLoadingRecyclerView) findViewById(R.id.main_recycler);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setUpRecyclerView();
        setUpToolbar();
    }

    private void setUpRecyclerView(){
        mAdapter = new ArticleBriefAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUpToolbar(){
        //设置Toolbar为ActionBar
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
    }

    private void initWidget(){
        //获取网络请求工具
        mRemoteService = RemoteService.getInstance();
        mGson = new Gson();
        //获取数据
        getData();
    }

    public void getData(){
        //设置网络加载的回调监听
        if(mCallback == null){
            mCallback = new RemoteCallback<List<ArticleBrief>>() {
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
                    mAdapter.addItems(data);
                    //当前页数
                    mPage += 1;
                    //完成加载
                    mRecyclerView.setFinish(true);
                }
            };
        }
        //设置url
        String url = UrlManager.ARTICLE_BRIEF.replace("X", mPage +"");
        //连接网络
        mRemoteService.connHttp(url,mCallback);
    }

    private void initListener(){
        //设置加载更多的监听
        mRecyclerView.setOnLoadMoreListener(new AutoLoadingRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                getData();
            }
        });

        //设置点击Item的监听
        mAdapter.setOnItemClickListener(new ArticleBriefAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(MainActivity.this, ArticleInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main,menu);
        //获取夜间模式的menu
        MenuItem nightMode = menu.findItem(R.id.main_night_mode);
        //初始化的时候切换名字
        if (NightModeHelper.getUiNightMode() == Configuration.UI_MODE_NIGHT_YES){
            nightMode.setTitle(R.string.day_mode);
        }
        else {
            nightMode.setTitle(R.string.night_mode);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_night_mode:
                if (NightModeHelper.getUiNightMode() == Configuration.UI_MODE_NIGHT_YES){
                    MyApplication.isNightMode = false;
                    item.setTitle(R.string.night_mode);
                }
                else {
                    MyApplication.isNightMode = true;
                    item.setTitle(R.string.day_mode);
                }
                mNightModeHelper.toggle();
/*                //存储到Shared中
                saveMode2Shared();
                //重绘Activity
                recreate();*/
                break;
            case R.id.main_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void saveMode2Shared(){
        SharedPreferences.Editor editor = getSharedPreferences(SharedPreUtils.FILE_NAME,
                MODE_PRIVATE).edit();
        editor.putBoolean(SharedPreUtils.KEY_NIGHT_MODE,MyApplication.isNightMode);
        editor.commit();
    }
}
