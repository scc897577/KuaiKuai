package com.ccstest.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2017/1/5.
 */
public class WaveView extends View {


    //其实点坐标
    private float mStartPointX;
    private float mStartPointY;
    //终止点坐标
    private float mEndPointX;
    private float mEndPointY;
    //控制点坐标1
    private float mFlagPointOneX;
    private float mFlagPointOneY;
    //控制点坐标2
    private float mFlagPointTwoX;
    private float mFlagPointTwoY;

    private Path mPath;

    private Paint mPaint;
    private Paint mPaintFlag;
    private Paint mPaintText;

    private int mWaveLength;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mCenterY;
    private int mWaveCount;

    private ValueAnimator mValueAnimator;
    private int mOffset;


    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿的flag
        mPaint.setStrokeWidth(9);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWaveLength = 800;
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 可以控制自定义View的点的坐标
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
//        setOnClickListener(this);

        mScreenHeight = h;
        mScreenWidth = w;
        mCenterY = h / 2;

        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);
    }

    /**
     * 开画
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        setAnim();
        mPath.moveTo(-mWaveLength + mOffset, mCenterY);

        for (int i = 0; i < mWaveCount; i++) {
            mPath.quadTo(-mWaveLength * 3 / 4 + i * mWaveLength + mOffset, mCenterY + 60, 
-mWaveLength / 2 + i * mWaveLength + mOffset, mCenterY);
            mPath.quadTo(-mWaveLength * 1 / 4 + i * mWaveLength + mOffset, mCenterY - 60, 
i * mWaveLength + mOffset, mCenterY);
        }
//        mPath.lineTo(mScreenWidth, mScreenHeight);
//        mPath.lineTo(0, mScreenHeight);
//        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    public void setAnim() {
        mValueAnimator = ValueAnimator.ofInt(0, mWaveLength);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mOffset = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }
}
