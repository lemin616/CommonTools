package com.lemin.commontools.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;

public class MViewPage extends ViewPager{

    public MViewPage(@NonNull Context context) {
        super(context);
    }

    public MViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canScrollHorizontally(int direction) {
        return super.canScrollHorizontally(direction);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return v instanceof LineChart || super.canScroll(v, checkV, dx, x, y);
    }
}
