package com.lemin.commontools.widget.chart;

import com.lemin.commontools.R;
import com.lemin.commontools.helper.StringHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;

public class LineChartHelper {
    public LineChartHelper() {
    }

    public static void setStyle(LineChart lineChart){
        Description description = new Description();
        description.setText("");
        lineChart.getLegend().setEnabled(false);
        lineChart.setDescription(description);
        //设置是否绘制chart边框的线
        lineChart.setDrawBorders(false);
        //设置chart边框线颜色
//        lineChart.setBorderColor(Color.GRAY);
        //设置chart边框线宽度
//        lineChart.setBorderWidth(1f);
        //设置chart是否可以触摸
        lineChart.setTouchEnabled(true);
        //设置是否可以拖拽
        lineChart.setDragEnabled(true);
        //设置是否可以缩放 x和y，默认true
//        lineChart.setScaleYEnabled(false);
//        lineChart.setScaleXEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(true);

        //设置是否可以通过双击屏幕放大图表。默认是true
        lineChart.setDoubleTapToZoomEnabled(false);
        //设置chart动画
        lineChart.animateY(1000);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLimitLinesBehindData(false);
        xAxis.setLabelCount(6);
//        xAxis.setSpaceMin(50);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        //设置最小间隔，防止当放大时出现重复标签
        xAxis.setGranularity(1f);
        //设置为true当一个页面显示条目过多，X轴值隔一个显示一个
        xAxis.setGranularityEnabled(true);
        //禁用Y
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setBackgroundColor(StringHelper.resColor(R.color.white));
    }

}
