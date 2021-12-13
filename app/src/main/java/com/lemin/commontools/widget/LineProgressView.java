package com.lemin.commontools.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;



public class LineProgressView extends View {
    private Paint paint;
    private float progress;
    public LineProgressView(Context context) {
        this(context, null);
    }

    public LineProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffFCCA16);
        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 8, getContext().getResources().getDisplayMetrics()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0,0, getWidth() * progress, getHeight(), paint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
