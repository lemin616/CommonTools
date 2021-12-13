package com.lemin.commontools.widget.chart;

import android.support.annotation.ColorInt;



public abstract class ChartData {
    @ColorInt
    private int color;

    public abstract String getChartXValue();

    public abstract float getChartYValue();

    @ColorInt
    public int getPointColor() {
        return color;
    }

    public void setPointColor(@ColorInt int color) {
        this.color = color;
    }
}
