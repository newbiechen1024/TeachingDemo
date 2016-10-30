package com.newbiechen.useporterduffxfermode.widget;

import android.bluetooth.BluetoothA2dp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.CursorAnchorInfo;

import com.newbiechen.useporterduffxfermode.R;

/**
 * Created by PC on 2016/10/28.
 */

public class ScratchView extends View {

    private final Paint mPaint = new Paint();
    private Bitmap mContentBitmap;
    private Bitmap mMaskBitmap;
    private Canvas mBitmapCanvas;
    private final Path mPath = new Path();

    private int mViewWidth;
    private int mViewHeight;

    private int mBitmapWidth;
    private int mBitmapHeight;

    public ScratchView(Context context) {
        this(context,null);
    }

    public ScratchView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScratchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget();
    }

    private void initWidget(){
        //初始化画笔
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.TRANSPARENT);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //初始化，刮刮卡的内容
        mContentBitmap = BitmapFactory.
                decodeResource(getResources(), R.mipmap.picture);

        mBitmapWidth = mContentBitmap.getWidth();
        mBitmapHeight = mContentBitmap.getHeight();

        //初始化刮刮卡的遮盖效果
        mMaskBitmap = Bitmap.createBitmap(mBitmapWidth,mBitmapHeight, Bitmap.Config.ARGB_8888);

        mBitmapCanvas = new Canvas(mMaskBitmap);
        //设置灰色的遮盖层
        mBitmapCanvas.drawColor(Color.GRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制图片
        canvas.drawBitmap(mContentBitmap,0,0, null);
        //绘制蒙版
        canvas.drawBitmap(mMaskBitmap,0,0,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                mPath.lineTo(x,y);
                break;
        }
        //设置擦除的路径
        mBitmapCanvas.drawPath(mPath,mPaint);
        //重绘
        invalidate();
        return true;
    }

}
