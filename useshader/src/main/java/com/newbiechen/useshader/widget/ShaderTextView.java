package com.newbiechen.useshader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by PC on 2016/10/16.
 */

public class ShaderTextView extends TextView {

    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;

    private float mViewWidth;
    private int mTranslate;
    public ShaderTextView(Context context) {
        this(context,null);
    }

    public ShaderTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget();
    }

    private void initWidget(){
        mPaint = getPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        //设置线性渐变
        mLinearGradient = new LinearGradient(0,0,mViewWidth,0,
                new int[]{Color.RED,Color.GREEN,Color.BLUE},null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        //创建矩阵
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置位移
        if (mTranslate > mViewWidth){
            mTranslate = (int)-mViewWidth;
        }
        else {
            mTranslate += mViewWidth/120;
        }
        mMatrix.setTranslate(mTranslate,0);
        mLinearGradient.setLocalMatrix(mMatrix);
        invalidate();
    }
}
