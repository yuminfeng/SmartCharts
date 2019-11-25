package com.yumf.jince.smartcharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;

import com.yumf.jince.smartcharts.Option;
import com.yumf.jince.smartcharts.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineChartView extends BasicView {

    private List<Float> mDatas = new ArrayList<>();
    private PointF[] mPoints;
    private Paint mPaint;

    /**
     * 各个数据点的间距
     */
    private float dataSpacing;
    private float drawRatio; //尺寸比例

    public LineChartView(Context context) {
        super(context);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setDataList(List<Option> list) {
        mDatas.clear();
        for (Option option : list) {
            mDatas.add(Float.parseFloat(option.getTotal_equity()));
        }
        mXAxisList.add(list.get(0).getDate());
        mXAxisList.add(list.get(list.size() / 2 + 1).getDate());
        mXAxisList.add(list.get(list.size() - 1).getDate());
        postInvalidate();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.efaf34));
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPoints = initPoint();
        drawCubicTo(canvas);
    }

    /**
     * 根据传入的数据，确定绘制的点
     *
     * @return
     */
    private PointF[] initPoint() {
        float maxValue = Collections.max(mDatas);
        float minValue = Collections.min(mDatas);
        dataSpacing = frameWidth / (mDatas.size() - 1);
        drawRatio = frameHeight * 0.75f / (maxValue - minValue);
        float startX = strokeLeft;
        Log.e("yumf", "max: " + maxValue + " ,min: " + minValue + " ,ratio:" + drawRatio + ",dataSpace:" + dataSpacing);

        PointF[] points = new PointF[mDatas.size()];
        for (int i = 0; i < mDatas.size(); i++) {
            float ybean = mDatas.get(i);
            float start = startX + dataSpacing * i;
            float drawHeight = frameHeight - (ybean - minValue) * drawRatio;
            Log.e("yumf", "start: " + start + " ,drawHeight:" + drawHeight + ", " + ybean * drawRatio);
            points[i] = new PointF(start, drawHeight);
        }
        return points;
    }

    private void drawCubicTo(Canvas canvas) {
        PointF startP;
        PointF endP;
        Path path = new Path();
        Path fillPath = new Path();

        float startWhiteX = mPoints[0].x;
        float startWhiteY = mPoints[0].y;
        float preWhiteX = startWhiteX;
        float preWhiteY = startWhiteY;
        float endWhiteX = 0f;
        float endWhiteY = 0f;
        for (int i = 0; i < mPoints.length - 1; i++) {
            startP = mPoints[i];
            endP = mPoints[i + 1];
            float wt = (startP.x + endP.x) / 2;
            PointF p3 = new PointF();
            PointF p4 = new PointF();
            p3.y = startP.y;
            p3.x = wt;
            p4.y = endP.y;
            p4.x = wt;
            path.moveTo(startP.x, startP.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endP.x, endP.y);
            preWhiteX = startWhiteX;
            preWhiteY = startWhiteY;
            startWhiteX = endWhiteX;
            startWhiteY = endWhiteY;
        }
        fillPath.reset();
        fillPath.addPath(path);
//        drawFill(canvas, mPaint, fillPath, strokeLeft + strokeWidth, endWhiteX, endWhiteY, frameHeight);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);
    }

    protected void drawFill(Canvas canvas, Paint paint, Path spline,
                            float from, float to, float top, float bottom) {

        spline.lineTo(to, bottom);
        spline.lineTo(from, bottom);
        spline.close();

        paint.setStyle(Paint.Style.FILL);
        try {
            if (Build.MANUFACTURER.toLowerCase().contains("huawei") &&
                    Build.VERSION.SDK_INT >= 26) {
                paint.setAlpha(38);
                canvas.drawPath(spline, paint);
                paint.setAlpha(255);
            } else {
                paint.setShader(new LinearGradient(to, top, to, bottom,
                        Color.BLUE,
                        Color.BLUE,
                        Shader.TileMode.CLAMP));
                canvas.drawPath(spline, paint);
                paint.setShader(null);
            }
        } catch (Exception e) {
            Log.e("Exception", "drawFill", e);
        }
    }
}
