package com.lemin.commontools.widget.mpchat;

import android.text.TextUtils;
import android.util.SparseArray;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XLeble implements IAxisValueFormatter {
    private SparseArray<String> lebles;

    public XLeble(SparseArray<String> lebles) {
        this.lebles = lebles;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int i = (int) value;
        String s = lebles.get(i);
        if (TextUtils.isEmpty(s))
            return "";
        else
            return s;
    }
}
