package com.shixia.serialpageradapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ShiXiuwen on 2016/12/6.
 * <p>
 * Description: ViewPager之类的页面滑动指示器,使用的时候只需要设置指示器高度，
 * 显示宽度和页面个数相关,注意请勿将RelativeLayout作为该控件的直接父布局，否则
 * 可能导致宽度显示不全
 * PS：页数从第0页开始
 */

public class PageChangeIndicatorView extends View {

    private int pageCount = 3;      //默认3页
    private int pageSelected = 0;   //默认选中第0页

    private Paint paint;
    private int height;

    public PageChangeIndicatorView(Context context) {
//        super(context);
        this(context, null);
    }

    public PageChangeIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.argb(255, 143, 143, 143));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }

    /**
     * 设置页面数量
     *
     * @param pageCount 页面数量
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
        requestLayout();        //页面数量改变，控件宽度随着变动，重新请求布局
    }

    /**
     * 更新选中页面
     *
     * @param pageSelected 选中页面脚标
     */
    public void onPageSelectedUpdate(int pageSelected) {
        this.pageSelected = pageSelected;
        invalidate();           //刷新选中页面
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < pageCount; i++) {
            if (i == pageSelected) {                             //选中的点设置为白色，未选中的点为灰色
                paint.setColor(Color.argb(255, 255, 255, 255));
                drawDot(canvas, i);
            } else {
                paint.setColor(Color.argb(255, 143, 143, 143));
                drawDot(canvas, i);
            }
        }
    }

    /**
     * 绘制点的方法
     */
    private void drawDot(Canvas canvas, int i) {
        float cx = i * height + (i + 1) * height - height / 2f;   //点之间的距离是点的直径
        canvas.drawCircle(cx, height / 2, height / 2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasureLength(heightMeasureSpec);
        int width = (pageCount - 1) * height + pageCount * height;
        Log.i("amos->", "height:" + height + " " + "width:" + width);
        setMeasuredDimension(width, height);
    }

    private int getMeasureLength(int height) {
        int mode = MeasureSpec.getMode(height);     //获得模式
        int size = MeasureSpec.getSize(height);     //获得大小
        int meaResult;                              //测量结果
        if (mode == MeasureSpec.EXACTLY) {
            meaResult = size;                       //高度跟随布局中设置
        } else {
            meaResult = 20;                         //高度默认值为20
        }
        return meaResult;
    }
}
