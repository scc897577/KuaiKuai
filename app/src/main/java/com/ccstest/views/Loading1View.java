package com.ccstest.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.ccstest.R;

/**
 * Created by scc on 2017/1/9.
 */
public class Loading1View extends View {
    //logt
    private static final String TAG = "Loading1View";
    //画笔初始化
    private Paint mPaint;
    //属性动画
    private ValueAnimator mAnimator;
    //当前Y轴偏移,开始X轴坐标,开始Y轴坐标,结束Y轴坐标
    private int currentY, startX, startY, endY;
    //当前需要显示的图片的下标
    private int currentIndex;
    //当前图片的位图对象
    private Bitmap currentBitmap;
    //这里我是直接初始化好的位图对象
    Bitmap b = drawableToBitmap(getResources().getDrawable(R.drawable.gold));
    Bitmap b1 = drawableToBitmap(getResources().getDrawable(R.drawable.goldingots));
    Bitmap b2 = drawableToBitmap(getResources().getDrawable(R.drawable.goldsupplier));
    //装进数组中,也可以使用集合,都行的
    final Bitmap[] bs = new Bitmap[]{b, b1, b2};

    //构造方法
    public Loading1View(Context context) {
        this(context, null);
    }

    public Loading1View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Loading1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    /**
     * 初始化画笔
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#1B7BEF"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //getWidth是当前自定义View的宽度,除以2是获得中心点的X坐标
        startX = getWidth() / 2;
        //getHeight是当前自定义View的高度,除以2是获得中心点的Y坐标
        endY = getHeight() / 2;
        //动画开始的Y轴的值为view中心的Y轴的坐标的六分之五
        startY = endY * 5 / 6;
        //初始化属性动画
        if (currentY == 0) {
            setAnimator();
        } else {
            //画位图,一参位图对象,二参位图x坐标,三参位图y坐标,四参画笔
            canvas.drawBitmap(setBitmapSize(bs[currentIndex]), startX - 60, currentY, mPaint);
            //画下方阴影
            drawShader(canvas);
        }
    }

    /**
     * 属性动画
     */
    public void setAnimator() {
        mAnimator = ValueAnimator.ofInt(startY, endY);
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(-1);
        mAnimator.setInterpolator(new OvershootInterpolator());
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentY = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        /**
         * 在动画开始时currentIndex初始值为0;
         * 获得currentBitmap对象
         * 在循环的过程中,currentIndex++,如果为3,重置为0,咱只有三张图片
         */
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                currentIndex = 0;
                currentBitmap = bs[currentIndex];
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                currentIndex++;
                if (currentIndex == 3) {
                    currentIndex = 0;
                }
                currentBitmap = bs[currentIndex];
            }
        });
        mAnimator.start();
    }

    /**
     * 绘制阴影部分，由椭圆来支持，根据高度比来底部阴影的大小
     */
    private void drawShader(Canvas canvas) {
        // 计算差值高度
        int dx = endY - startY;
        // 计算当前点的高度差值
        int dx1 = currentY - startY;
        float ratio = (float) (dx1 * 1.0 / dx);
        if (ratio <= 0.3) {// 当高度比例小于0.3，所在比较高的时候就不进行绘制影子
            return;
        }
        int ovalRadius = (int) (60 * ratio);
        // 设置倒影的颜色
        mPaint.setColor(Color.parseColor("#1B7BEF"));
        // 绘制椭圆
        RectF rectF = new RectF(startX - ovalRadius, endY + 125, startX + ovalRadius, endY + 140);
        canvas.drawOval(rectF, mPaint);
    }

    /**
     * drawable转换为Bitmap
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

    /**
     * 设置bitMap的大小
     * @param bitMap
     * @return
     */
    public Bitmap setBitmapSize(Bitmap bitMap) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 120;
        int newHeight = 120;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix,
                true);
    }

}
