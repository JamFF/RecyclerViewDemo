package com.jam.ff.recyclerview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.jam.ff.recyclerview.R;

/**
 * 自定义控件
 * Created by jamff on 2018/2/6 10:20.
 */
public class CustomerView extends View {

    private static final String TAG = "CustomerView";

    // 文本
    private String mText;

    // 文本的颜色
    private int mTextColor;

    // 文本的大小
    private int mTextSize;

    // 绘制时控制文本绘制的范围
    private Rect mBound;
    private Paint mPaint;

    public CustomerView(Context context) {
        this(context, null);
    }

    //默认情况下，系统调用的是这个构造函数
    public CustomerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 优先获取布局文件中的TypedArray，如果没有找styles.xml中的Theme的TypedArray
        TypedArray _TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomerView, 0, 0);
        // 获取styles.xml中的Theme的TypedArray
        // TypedArray _TypedArray = context.obtainStyledAttributes(R.styleable.CustomerView);
        try {
            mText = _TypedArray.getString(R.styleable.CustomerView_text);
            mTextColor = _TypedArray.getColor(R.styleable.CustomerView_textColor, Color.BLACK);
            mTextSize = _TypedArray.getDimensionPixelSize(R.styleable.CustomerView_textSize,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        } finally {
            _TypedArray.recycle();
        }
        // 获得绘制文本的宽和高
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mBound = new Rect();
        Log.i(TAG, "TextLength:" + mText.length());
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure():");
        int _WidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int _WidthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int _HeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int _HeightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int _Width;
        int _Height;
        //宽度
        if (_WidthMode == MeasureSpec.EXACTLY) {
            _Width = _WidthSpec;
        } else {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float _TextWidth = mBound.width();
            _Width = (int) (getPaddingLeft() + _TextWidth + getPaddingRight());
        }
        //高度
        if (_HeightMode == MeasureSpec.EXACTLY) {
            _Height = _HeightSpec;
        } else {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mText, 0, mText.length(), mBound);
            float _TextHeight = mBound.height();
            _Height = (int) (getPaddingTop() + _TextHeight + getPaddingBottom());
        }

        setMeasuredDimension(_Width, _Height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw():");
        mPaint.setColor(Color.YELLOW);
        Log.i(TAG, "getMeasuredWidth():" + getMeasuredWidth() + "   " + getMeasuredHeight());
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTextColor);
        Log.i(TAG, "getWidth():" + getWidth() + "   " + getHeight());
        Log.i(TAG, "mBound.width():" + mBound.width() + "   " + mBound.height());
        canvas.drawText(mText, getWidth() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }
}
