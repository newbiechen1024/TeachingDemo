package com.newbiechen.usenightmode.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by PC on 2016/11/4.
 */

public class AutoLoadingRecyclerView extends RecyclerView {
    //加载的位置
    private static final int LOAD_LOC = 4;

    private LinearLayoutManager mManager;

    //加载的监听
    private OnLoadMoreListener mListener;
    //是否允许自动加载
    private boolean isLoading = true;
    //是否加载完成
    private boolean isFinish = true;


    public AutoLoadingRecyclerView(Context context) {
        this(context,null);
    }

    public AutoLoadingRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoLoadingRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWidget();
    }

    private void initWidget(){
        //设置滑动监听
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //判断是否加载
                checkLoadMore();
            }
        });
    }

    /**
     * 判断是否加载
     */
    private void checkLoadMore(){
        //获取线性管理器
        if (getLayoutManager() instanceof LinearLayoutManager
                && mManager == null){
            //获取线性管理器
            mManager = (LinearLayoutManager) getLayoutManager();
        }

        //判断是否进行加载
        if (mManager != null){
            //获取Adapter总数
            int count = mManager.getItemCount();
            //获取当前显示的最后一个Item的序号
            int pos = mManager.findLastVisibleItemPosition();
            
            //当item未占满一页的时候不加载，当未达到加载线的时候不加载
            if (count != pos &&
                    (count - pos) <= LOAD_LOC && isLoading && isFinish){
                //进行加载
                loadData();
            }
        }
    }

    private void loadData(){
        if (mListener != null){
            mListener.loadMore();
            isFinish = false;
        }
    }

    public interface OnLoadMoreListener{
        //加载更多
        void loadMore();
    }


    /***********************************公共方法**********************************************/

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mListener) {
        this.mListener = mListener;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isFinish() {
        return isFinish;
    }
}
