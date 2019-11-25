package com.yumf.jince.smartcharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BasicView extends View {

    private static final float DEFAULT_STROKE_WIDTH = 1;
    private static final int DEFAULT_STROKE_COLOR = 0xff363d4c;
    private static final float DEFAULT_MARGIN = 1.0f;
    /**
     * 当前控件高度
     */
    protected float viewHeight;
    /**
     * 当前控件宽度
     */
    protected float viewWidth;

    protected float strokeWidth;
    protected int strokeColor;
    /**
     * 边框边距
     */
    protected float strokeLeft;// 左边距
    protected float strokeRight;// 右边距
    protected float strokeTop;// 顶边距
    protected float strokeBottom;// 底边距

    private float xAxisTopMargin = 50;
    protected float frameHeight; //图表frame高度
    protected float frameWidth;//图表frame宽度
    protected List<String> mXAxisList = new ArrayList<>(); //x 轴

    public BasicView(Context context) {
        super(context);
    }

    public BasicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        strokeWidth = DEFAULT_STROKE_WIDTH;
        strokeColor = DEFAULT_STROKE_COLOR;
        strokeLeft = DEFAULT_MARGIN;
        strokeRight = DEFAULT_MARGIN;
        strokeTop = DEFAULT_MARGIN;
        strokeBottom = DEFAULT_MARGIN;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewHeight = getHeight();
        viewWidth = getWidth();
//        Log.e("yumf", "viewHeight:" + viewHeight + ",viewWidth:" + viewWidth);
        drawTopFrame(canvas);
        drawBottomXAxis(canvas);
    }

    private void drawTopFrame(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(strokeColor);
        paint.setAntiAlias(true);

        frameWidth = viewWidth - strokeRight - strokeLeft;
        frameHeight = viewHeight - strokeTop - strokeBottom - xAxisTopMargin;

        canvas.drawLine(strokeLeft, strokeTop, strokeLeft, frameHeight, paint);// 绘制左边竖线

        canvas.drawLine(frameWidth, strokeTop, frameWidth, frameHeight, paint);// 绘制右边竖线

        canvas.drawLine(strokeLeft, strokeTop, frameWidth, strokeTop, paint);// 绘制顶部横线

        canvas.drawLine(strokeLeft, frameHeight, frameWidth, frameHeight, paint);// 绘制底部横线
    }

    private void drawBottomXAxis(Canvas canvas) {
        if (mXAxisList.isEmpty()) return;
        TextPaint paint = new TextPaint();
        paint.setTextSize(20);
        paint.setColor(strokeColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);
        paint.setTextAlign(Paint.Align.LEFT);

        float startX = strokeLeft + strokeWidth / 2;
        float startY = viewHeight - xAxisTopMargin / 2;
        float textMargin = (viewWidth - strokeLeft - strokeRight - paint.measureText(mXAxisList.get(mXAxisList.size() - 1))) / (mXAxisList.size() - 1);

        for (int i = 0; i < mXAxisList.size(); i++) {
            String label = mXAxisList.get(i);
            if (i == mXAxisList.size() - 1) {
                float textWidth = paint.measureText(label);
                canvas.drawText(mXAxisList.get(i), viewWidth - textWidth - strokeRight, startY, paint);
            } else {
                canvas.drawText(mXAxisList.get(i), startX + textMargin * i, startY, paint);
            }
        }
    }
}
