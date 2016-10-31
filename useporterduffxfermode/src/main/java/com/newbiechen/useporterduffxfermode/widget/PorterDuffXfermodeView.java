package com.newbiechen.useporterduffxfermode.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.CursorAnchorInfo;

import com.newbiechen.useporterduffxfermode.R;

/**
 * Created by PC on 2016/10/16.
 */

public class PorterDuffXfermodeView extends View {

    private final Paint mPaint = new Paint();
    private Bitmap mBitmap;
    private Bitmap mOut;
    private int mViewWidth;
    private int mViewHeight;

    public PorterDuffXfermodeView(Context context) {
        this(context,null);
    }

    public PorterDuffXfermodeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PorterDuffXfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget();
    }

    private void initWidget(){
        //初始化画笔
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
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
        canvas.translate(mViewWidth/2,mViewHeight/2);
        thirdExample(canvas);
    }

    /**
     * 制作具有相交部分的圆和正方形
     */
    private void firstExample(Canvas canvas){
        //绘制一个正方型
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(-300,-300,0,0,mPaint);
        //绘制一个圆
        mPaint.setColor(Color.RED);
        canvas.drawCircle(0,0,200,mPaint);
    }

    /**
     * 错误的使用：将正方形显示在顶部，圆形显示在底部
     * @param canvas
     */
    private void secondExample(Canvas canvas){
        //绘制一个正方型
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(-300,-300,0,0,mPaint);
        //设置相交时候，图层显示的模式(表示当相交的时候，圆形为先绘制的图形)
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        //绘制一个圆
        mPaint.setColor(Color.RED);
        canvas.drawCircle(0,0,200,mPaint);
    }


    /**
     * 正确的使用：将正方形显示在顶部，圆形显示在底部
     * @param canvas
     */
    private void thirdExample(Canvas canvas){
        //创建一个Bitmap
        Bitmap out = Bitmap.createBitmap(600,600, Bitmap.Config.ARGB_8888);
        //创建该Bitmap的画布
        Canvas bitmapCanvas = new Canvas(out);
        //绘制一个正方型
        mPaint.setColor(Color.BLUE);
        bitmapCanvas.drawRect(0,0,300,300,mPaint);
        //设置相交时候，图层显示的模式(表示当相交的时候，圆形为先绘制的图形)
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        //绘制一个圆
        mPaint.setColor(Color.RED);
        bitmapCanvas.drawCircle(300,300,200,mPaint);
        //最后，将完成的图片绘制在View上
        canvas.drawBitmap(out,-300,-300,null);
    }

    /**
     * 绘制圆形的Bimtap
     * @return
     */
    private void forthExample(Canvas canvas){
        //创建自定义的Bitmap
        Bitmap out = Bitmap.createBitmap(300,300, Bitmap.Config.ARGB_8888);
        //获取图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.picture);
        //获取Bitmap的画笔
        Canvas bitmapCanvas = new Canvas(out);
        //在Bitmap上绘制一个圆形
        bitmapCanvas.drawCircle(150,150,150,mPaint);
        //设置显示后画图形的交集
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制需要显示的图片
        bitmapCanvas.drawBitmap(bitmap,0,0,mPaint);

        canvas.drawBitmap(out,-150,-150,null);
    }

    /**
     * 说明Canvas的图层关系
     * @param canvas
     */
    private void fifthExample(Canvas canvas){
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(-300,-300,0,0,mPaint);
        Bitmap out = Bitmap.createBitmap(400,400, Bitmap.Config.ARGB_8888);
        Canvas bitmapCanvas = new Canvas(out);
        mPaint.setColor(Color.RED);
        bitmapCanvas.drawCircle(200,200,200,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        float squareLeft = 200 - 100 * (float)Math.sqrt(2);
        float squareTop = squareLeft;
        float squareRight = squareLeft + 200 * (float)Math.sqrt(2);
        float squareBottom = squareRight;
        bitmapCanvas.drawRect(squareLeft,squareTop,squareRight,squareBottom,mPaint);
        canvas.drawBitmap(out,-200,-200,null);
    }
}
