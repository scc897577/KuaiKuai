package com.ccstest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/1/11.
 */
public class TreeView extends View {

    private Paint mPaint;
    private Paint mPointPaint;
    private Path mPath;

    public TreeView(Context context) {
        this(context, null);
    }

    public TreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setStrokeWidth(8);
        mPointPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPoint(getWidth() / 2, getHeight() * 3 / 4, mPointPaint);
        mPath.moveTo(getWidth() / 2, getHeight() * 3 / 4);
        mPath.lineTo(getWidth() / 2, getHeight() / 2);
        canvas.drawPath(mPath, mPaint);
    }
}
