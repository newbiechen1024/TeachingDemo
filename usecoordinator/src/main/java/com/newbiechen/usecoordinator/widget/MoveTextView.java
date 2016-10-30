package com.newbiechen.usecoordinator.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by PC on 2016/10/17.
 */

public class MoveTextView extends TextView {
    private OnMoveListener mMoveListener;
    private int lastY = 0;

    public MoveTextView(Context context) {
        super(context);
    }

    public MoveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取左上角的Y值
        int currentY = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                //获取距离差
                int dy = currentY - lastY;
                //获取移动后的Y值
                int tvY = (int) this.getY() + dy;
                //设置tv的位置
                this.setY(tvY);
                if (mMoveListener != null){
                    mMoveListener.onMove(tvY);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastY = currentY;
        return true;
    }

    public interface OnMoveListener{
        void onMove(int y);
    }

    public void setOnMoveListener(OnMoveListener listener){
        mMoveListener = listener;
    }
}
