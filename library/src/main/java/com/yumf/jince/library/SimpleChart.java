package com.yumf.jince.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yumf on 30/10/2018.
 */

public class SimpleChart extends View {

    private String TAG = "SimpleChart";
    private static final int BOTTOM_PADDING = 10;
    private static final int LEFT_PADDING = 20;
    private static final int RIGHT_PADDING = 20;


    // x 坐标轴
    private Paint mPaint_x;
    private int mLineColor;

    private int mLineStrokeWidth;
    private Paint mPaintCategory;
    private int mCategoryTextColor;
    private int mCategoryTextSize;
    private List<Data> mCategoryData = new ArrayList<>();
    private List<Double> nums = new ArrayList<>();

    private Paint mPaintChart;

    private int mStringHeight;

    public SimpleChart(Context context) {
        this(context, null);
    }

    public SimpleChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SimpleChart, defStyleAttr, 0);
        if (a != null) {
            mLineColor = a.getInt(R.styleable.SimpleChart_horizontalLineColor, Color.parseColor("#000000"));
            mLineStrokeWidth = a.getDimensionPixelSize(R.styleable.SimpleChart_horizontalLineStrokeWidth, 1);
            mCategoryTextColor = a.getColor(R.styleable.SimpleChart_categoryTextColor, Color.parseColor("#000000"));
            mCategoryTextSize = a.getDimensionPixelSize(R.styleable.SimpleChart_categoryTextSize, 12);
            a.recycle();
        }

        initViews();
    }

    private void initViews() {
        mPaint_x = new Paint();
        mPaint_x.setColor(mLineColor);
        mPaint_x.setStrokeWidth(mLineStrokeWidth);

        mPaintCategory = new Paint();
        mPaintCategory.setAntiAlias(true);
        mPaintCategory.setColor(mCategoryTextColor);
        mPaintCategory.setTextSize(mCategoryTextSize);

        mPaintChart = new Paint();
        mPaintChart.setAntiAlias(true);
        mPaintChart.setTextSize(mCategoryTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        if (widthSpecMode == MeasureSpec.EXACTLY) {
            width = widthSpecSize;
        } else {
            width = 0;
        }

        int height;
        if (heightSpecMode == MeasureSpec.EXACTLY) {
            height = heightSpecSize;
        } else {
            height = 0;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        Log.e(TAG, "width:" + width + " ,height:" + height);

        //绘制分组类别数据
        drawCategoryText(canvas, width, height);

        //居中绘制水平线
        drawHorizontalLine(canvas, width, (height - getStringHeight() - BOTTOM_PADDING) / 2);

    }

    public void setCategoryData(List<Data> categories) {
        if (!mCategoryData.isEmpty()) mCategoryData.clear();
        mCategoryData.addAll(categories);

        invalidate();
    }

    public int getStringHeight() {
        return mStringHeight;
    }

    private void drawHorizontalLine(Canvas canvas, int width, int h) {
        canvas.drawLine(LEFT_PADDING, h, width - RIGHT_PADDING, h, mPaint_x);
    }

    private void drawCategoryText(Canvas canvas, int width, int height) {

        if (!mCategoryData.isEmpty()) {
            int startLeft = LEFT_PADDING * 3;
            int w = width - LEFT_PADDING * 2;
            int stringWidth = 0;
            nums.clear();
            for (int i = 0; i < mCategoryData.size(); i++) {
                String label = mCategoryData.get(i).getLabel();
                double num = Math.abs(mCategoryData.get(i).getNum());
                Rect rect = new Rect();
                mPaintCategory.getTextBounds(label, 0, label.length(), rect);
                if (stringWidth < rect.width()) {
                    stringWidth = rect.width();
                    mStringHeight = rect.height();

                    Log.e("yumf", "mString height:" + mStringHeight);
                }
                nums.add(num);
            }
            double numMax = Collections.max(nums);
            int space = (w - stringWidth * 4) / mCategoryData.size(); //计算label间的间隔
            for (int i = 0; i < mCategoryData.size(); i++) {
                String label = mCategoryData.get(i).getLabel();
                double num = mCategoryData.get(i).getNum();
                canvas.drawText(label, startLeft, height - BOTTOM_PADDING, mPaintCategory);
                drawCharts(canvas, (height - getStringHeight() - BOTTOM_PADDING) / 2, startLeft, stringWidth, num, numMax);
                startLeft += stringWidth + space;
            }
        }

    }

    private void drawCharts(Canvas canvas, int h, int l, int w, double num, double max) {
        Log.e(TAG, "max:" + max);

        float baseTop = h / 3;
        float rectTop;
        float rectBottom;
        float textY;
        String textNum;

        if (num > 0) {
            if (num == max) {
                rectTop = h - baseTop;
            } else {
                rectTop = (float) (h - baseTop * (num / max));
            }
            rectBottom = h - mLineStrokeWidth / 2;
            textY = rectTop - 15;
            textNum = "+" + num;
            mPaintChart.setColor(Color.parseColor("#FC4430"));
        } else {
            rectTop = h + mLineStrokeWidth / 2;
            if (num == max) {
                rectBottom = rectTop + baseTop - 20;
                ;
            } else {
                rectBottom = (float) (baseTop * Math.abs(num / max) + rectTop);
            }
            textY = rectBottom + 30;
            textNum = String.valueOf(num);
            mPaintChart.setColor(Color.parseColor("#00FF00"));
        }
        RectF rect = new RectF(l, rectTop, w + l, rectBottom);
        canvas.drawRect(rect, mPaintChart);
        canvas.drawText(textNum, l - 10, textY, mPaintChart);
    }

}
