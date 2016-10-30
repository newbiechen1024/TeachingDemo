package com.newbiechen.useshader.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.newbiechen.useshader.R;

import static android.graphics.Shader.TileMode.CLAMP;
import static android.graphics.Shader.TileMode.MIRROR;
import static android.graphics.Shader.TileMode.REPEAT;

/**
 * Created by PC on 2016/10/15.
 * 任务：
 * 1、知道Shader的使用方式
 *
 */

public class ShaderView extends View {
    private static final int BITMAP_WIDTH = 400;
    private static final int BITMAP_HEIGHT = 300;

    private int mViewWidth;
    private int mViewHeight;

    private Paint mPaint = new Paint();

    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //设置笔触类型
        mPaint.setStyle(Paint.Style.FILL);
        //抗锯齿
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取View的大小
        mViewWidth = w;
        mViewHeight = h;

/*        //线性渐变
        LinearGradient linearGradient = new LinearGradient(
                0, 0, mViewWidth, 0, Color.RED, Color.GREEN, CLAMP
        );
        mPaint.setShader(linearGradient);*/

/*        //光束渐变
        int centerX = w / 2;
        int centerY = h / 2;
        int radium = Math.min(w, h) / 2;
        RadialGradient radialGradient = new RadialGradient(
                centerX, centerY, radium, Color.YELLOW, Color.GREEN, CLAMP
        );*/

/*        //主要代码：梯度渐变
        int centerX = w/2;
        int centerY = h/2;
        SweepGradient sweepGradient = new SweepGradient(
                centerX,centerY,Color.YELLOW,Color.BLUE
        );
        mPaint.setShader(sweepGradient);*/

        //主要代码：位图渐变
        //第一步：首先获取图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.shader);
        BitmapShader bitmapShader = new BitmapShader(bitmap,MIRROR,MIRROR);
        mPaint.setShader(bitmapShader);
       /* //由于当画布移动的时候，图片的显示的起始点会根据画布的起始点移动，所以需要通过Matrix对图片进行平移
        Matrix matrix = new Matrix();
        matrix.postTranslate(-BITMAP_WIDTH/2,-BITMAP_HEIGHT/2);
        bitmapShader.setLocalMatrix(matrix);
        //原理：将图片设置为渐变的填充物，然后设置横轴、纵轴渐变的模式。

        //线性渐变,第二种创建Shader的构造方法。
        LinearGradient linearGradient = new LinearGradient(
                0,0,BITMAP_WIDTH,BITMAP_HEIGHT,new int[]{Color.WHITE,Color.TRANSPARENT},
                null,MIRROR
        );

        //混合渐变
        ComposeShader shader = new ComposeShader(bitmapShader,linearGradient, PorterDuff.Mode.DST_IN);
        mPaint.setShader(shader);*/
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

      //线性渐变
        canvas.drawRect(0,0,mViewWidth,mViewHeight,mPaint);

/*      //光束渐变,位图渐变
        int centerX = mViewWidth/2;
        int centerY = mViewHeight/2;
        int radium = Math.min(mViewWidth,mViewHeight)/2;
        canvas.drawCircle(centerX,centerY,radium,mPaint);*/

/*        //设置画布居中
        int centerX = mViewWidth/2;
        int centerY = mViewHeight/2;
        canvas.translate(centerX,centerY);
        //设置绘制的范围
        RectF rectF = new RectF(-BITMAP_WIDTH/2,-BITMAP_HEIGHT/2,
                BITMAP_WIDTH/2,BITMAP_HEIGHT/2);
        //绘制圆角矩形
        canvas.drawRoundRect(rectF,30,30,mPaint);*/
    }
}