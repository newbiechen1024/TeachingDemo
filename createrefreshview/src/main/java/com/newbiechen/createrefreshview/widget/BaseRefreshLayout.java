package com.newbiechen.createrefreshview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.newbiechen.createrefreshview.R;

/**
 * Created by PC on 2016/11/16.
 */

public abstract class BaseRefreshLayout<T extends View> extends ViewGroup {

    private static final int STATUS_RELAX = 0;
    private static final int STATUS_REFRESH = 1;
    private static final int STATUS_FINISH = 2;

    private ImageView mIvRefreshIcon;
    private ProgressBar mPbRefreshing;
    private TextView mTvRefreshState;

    private T mContentView;

    private Scroller mScroller;

    private OnRefreshListener mRefreshListener;

    private int status = STATUS_RELAX;
    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mLastTouchX;
    private int mLastTouchY;

    private int mHeaderHeight;

    public BaseRefreshLayout(Context context) {
        this(context,null);
    }

    public BaseRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        //初始化头部刷新控件
        initHeaderView();
        //初始化ListView
        initListView();
        mScroller = new Scroller(getContext());
    }

    private void initHeaderView(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View header = inflater.inflate(R.layout.layout_base_refresh_header,this,false);

        mIvRefreshIcon = (ImageView) header.findViewById(R.id.header_iv_refresh_icon);
        mPbRefreshing = (ProgressBar) header.findViewById(R.id.header_pb_refreshing);
        mTvRefreshState = (TextView) header.findViewById(R.id.header_tv_refresh_statement);

        //添加到ViewGroup中去
        addView(header);
    }

    private void initListView(){
        mContentView = getContentView(getContext());

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

        addView(mContentView,params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        setMeasuredDimension(measureWidth,measureHeight);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childCount = getChildCount();
        int top = 0;
        for (int j=0; j<childCount; ++j){
            final View child = getChildAt(j);
            child.layout(0,top,0+child.getMeasuredWidth(),top+child.getMeasuredHeight());
            top += child.getMeasuredHeight();
        }
        //将HeaderView隐藏起来
        View header = getChildAt(0);
        mHeaderHeight = header.getMeasuredHeight();
        scrollTo(0,mHeaderHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int currentX = (int) ev.getX();
        int currentY = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = mLastInterceptX - currentX;
                //上划<0,下拉>0
                int deltaY = mLastInterceptY - currentY;
                //判断是否为竖直滑动并且为向下滑动,并且当前ListView的item在第一个
                if (Math.abs(deltaX) < Math.abs(deltaY) &&
                        deltaY < 0 && isTop()){
                    isIntercept = true;
                }
                else {
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
        }
        mLastInterceptX = currentX;
        mLastInterceptY = currentY;
        mLastTouchX = mLastInterceptX;
        mLastTouchY = mLastInterceptY;
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();
        //获取当前内部滑动的距离
        int scrollY = getScrollY();
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int deltaY = mLastTouchY - currentY;
                //当HeaderView完全显示的时候，停止滑动
                if (scrollY > 0){
                    if (scrollY < -deltaY){
                        deltaY = -scrollY;
                    }
                    scrollBy(0,deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (scrollY <= mHeaderHeight/2){
                    //弹性滑动,正在刷新
                    smoothToScroll(-scrollY);
                    status = STATUS_REFRESH;
                    //回调监听
                    if (mRefreshListener != null){
                        mRefreshListener.onRefresh();
                    }
                }
                else {
                    //返回
                   smoothToScroll(mHeaderHeight - scrollY);
                    status = STATUS_RELAX;
                }
                break;
        }
        mLastTouchX = currentX;
        mLastTouchY = currentY;
        return true;
    }

    private void smoothToScroll(int dy){
        mScroller.startScroll(0,getScrollY(),0,dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    public interface OnRefreshListener{
        void onRefresh();
    }

    public abstract T getContentView(Context context);
    //是否滑动的位置达到顶端
    public abstract boolean isTop();

    public void refreshFinish(){
        if (status == STATUS_REFRESH){
            //隐藏Header
            smoothToScroll(mHeaderHeight);
            status = STATUS_RELAX;
        }
    }

    public void setOnRefreshListener(OnRefreshListener mRefreshListener) {
        this.mRefreshListener = mRefreshListener;
    }
}
