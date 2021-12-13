package com.lemin.commontools.widget.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;



public class ChartView extends View {
    //xy坐标轴颜色
    private int xylinecolor = 0xffe2e2e2;
    //xy坐标轴宽度
    private int xylinewidth = dpToPx(1);
    //xy坐标轴文字颜色
    private int xytextcolor = 0xff7e7e7e;
    //xy坐标轴文字大小
    private int xytextsize = spToPx(12);
    //折线图中折线的颜色
    private int linecolor = 0xffe2e2e2;
    //x轴各个坐标点水平间距
    private int interval = dpToPx(45);
    //背景颜色
    private int bgcolor = 0xffffffff;
    //绘制XY轴坐标对应的画笔
    private Paint xyPaint;
    //绘制XY轴的文本对应的画笔
    private Paint xyTextPaint;
    //画折线对应的画笔
    private Paint linePaint;
    private int width;
    private int height;
    //x轴的原点坐标
    private int xOri;
    //y轴的原点坐标
    private int yOri;
    //第一个点X的坐标
    private float xInit;
    //第一个点对应的最大Y坐标
    private float maxXInit;
    //第一个点对应的最小X坐标
    private float minXInit;
    //x轴坐标对应的数据
//    private List<String> xValue = new ArrayList<>();
//    //折线对应的数据
//    private Map<String, Integer> value = new HashMap<>();

    private float maxPoint;

    private float avgPoint;

    //    private List<ChartData> chartData;
    private List<? extends List<? extends ChartData>> chartList;

    private Rect xValueRect;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化畫筆
     */
    private void initPaint() {
        xyPaint = new Paint();
        xyPaint.setAntiAlias(true);
        xyPaint.setStrokeWidth(xylinewidth);
        xyPaint.setStrokeCap(Paint.Cap.ROUND);
        xyPaint.setColor(xylinecolor);

        xyTextPaint = new Paint();
        xyTextPaint.setAntiAlias(true);
        xyTextPaint.setTextSize(xytextsize);
        xyTextPaint.setStrokeCap(Paint.Cap.ROUND);
        xyTextPaint.setColor(xytextcolor);
        xyTextPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(xylinewidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setColor(linecolor);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            //这里需要确定几个基本点，只有确定了xy轴原点坐标，第一个点的X坐标值及其最大最小值
            width = getWidth();
            height = getHeight();
            int dp4 = dpToPx(4);
            int dp6 = dpToPx(6);
//            //X轴文本最大高度
            xValueRect = getTextBounds("12-12", xyTextPaint);
            float textXHeight = xValueRect.height();
            yOri = (int) (height - dp4 - textXHeight - dp6 - xylinewidth);//dp3是x轴文本距离底边，dp2是x轴文本距离x轴的距离
            xInit = interval + xOri;
            maxXInit = xInit;

            if (chartList == null || chartList.size() == 0) {
                return;
            }
            List<? extends ChartData> chartData = chartList.get(0);
            for (int i = 0; i < chartData.size(); i++) {//求取x轴文本最大的高度
                Rect rect = getTextBounds(chartData.get(i).getChartXValue(), xyTextPaint);
                if (rect.height() > textXHeight)
                    textXHeight = rect.height();
                if (rect.width() > xValueRect.width())
                    xValueRect = rect;
            }

            minXInit = width - (width - xOri) * 0.1f - interval * (chartData.size() - 1);//减去0.1f是因为最后一个X周刻度距离右边的长度为X轴可见长度的10%

        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(bgcolor);
        drawXY(canvas);
        drawBrokenLineAndPoint(canvas);
    }

    /**
     * 绘制折线和折线交点处对应的点
     *
     * @param canvas
     */
    private void drawBrokenLineAndPoint(Canvas canvas) {
        if (chartList == null || chartList.size() == 0 || chartList.get(0).size() <= 0)
            return;
        //重新开一个图层
        int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        if (chartList != null)
            for (List<? extends ChartData> chartData : chartList) {
                drawBrokenLine(canvas, chartData);
                drawBrokenPoint(canvas, chartData);
            }


        // 将折线超出x轴坐标的部分截取掉
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(bgcolor);
        linePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        RectF rectF = new RectF(0, 0, xOri, height);
        canvas.drawRect(rectF, linePaint);
        linePaint.setXfermode(null);
        //保存图层
        canvas.restoreToCount(layerId);
    }

    /**
     * 绘制折线对应的点
     *
     * @param canvas
     */
    private void drawBrokenPoint(Canvas canvas, List<? extends ChartData> chartData) {
        float dp2 = dpToPx(3);
        float dp4 = dpToPx(4);
        //绘制节点对应的原点
        float max = maxValue(chartData);
        for (int i = 0; i < chartData.size(); i++) {
            float x = xInit + interval * i;
            float y = yOri - yOri * (1 - 0.1f) * chartData.get(i).getChartYValue() / max;
            if (max == 0)
                y = yOri;
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setColor(linecolor);
            canvas.drawCircle(x, y, dp4, linePaint);
            linePaint.setStyle(Paint.Style.FILL);
            linePaint.setColor(chartData.get(i).getPointColor() == 0 ? Color.RED : chartData.get(i).getPointColor());
            canvas.drawCircle(x, y, dp2, linePaint);

        }
    }

    /**
     * 绘制折线
     *
     * @param canvas
     */
    private void drawBrokenLine(Canvas canvas, List<? extends ChartData> chartData) {
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(linecolor);
        //绘制折线
        Path path = new Path();
        float max = maxValue(chartData);
        float x = xInit + interval * 0;
        float y = yOri - yOri * (1 - 0.1f) * (chartData.size() > 0 ? chartData.get(0).getChartYValue() : 0) / max;
        if (max == 0) {
            y = yOri;
        }
        path.moveTo(x, y);
        for (int i = 1; i < chartData.size(); i++) {
            x = xInit + interval * i;
            y = yOri - yOri * (1 - 0.1f) * chartData.get(i).getChartYValue() / max;
            if (max == 0)
                y = yOri;
            path.lineTo(x, y);
        }
        canvas.drawPath(path, linePaint);
    }

    /**
     * 绘制XY坐标
     *
     * @param canvas
     */
    private void drawXY(Canvas canvas) {
        xyPaint.setStyle(Paint.Style.STROKE);
        //顶部线
        canvas.drawLine(xOri, yOri * (1 - 0.9f), width, yOri * (1 - 0.9f), xyPaint);

        //avg
//        canvas.drawLine(xOri, yOri * (1-0.1f) * avgPoint/maxPoint, width, yOri * (1-0.1f) * avgPoint/maxPoint, xyPaint);

        //底部线
        canvas.drawLine(xOri, yOri + xylinewidth / 2, width, yOri + xylinewidth / 2, xyPaint);

        xyPaint.setStyle(Paint.Style.STROKE);

        if (chartList == null || chartList.size() == 0) return;
        //绘制x轴文本
        for (int i = 0; i < chartList.get(0).size(); i++) {
            float x = xInit + interval * i;
            if (x >= xOri) {//只绘制从原点开始的区域
                xyTextPaint.setColor(xytextcolor);
                //绘制X轴文本
                String text = chartList.get(0).get(i).getChartXValue();
                Rect rect = getTextBounds(text, xyTextPaint);
                canvas.drawText(text, 0, text.length(), x - rect.width() / 2, yOri + xylinewidth + dpToPx(2) + rect.height(), xyTextPaint);
            }
        }
    }

    private float startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);//当该view获得点击事件，就请求父控件不拦截事件

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (chartList == null || chartList.size() == 0)
                    break;
                if (interval * chartList.get(0).size() > width - xOri) {//当期的宽度不足以呈现全部数据
                    float dis = event.getX() - startX;
                    startX = event.getX();
                    if (xInit + dis < minXInit) {
                        xInit = minXInit;
                    } else if (xInit + dis > maxXInit) {
                        xInit = maxXInit;
                    } else {
                        xInit = xInit + dis;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                this.getParent().requestDisallowInterceptTouchEvent(false);

                break;
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

//    public void setxValue(List<String> xValue) {
//        this.xValue = xValue;
//    }
//
//    public void setValue(Map<String, Integer> value) {
//        this.value = value;
//        invalidate();
//    }
//
//    public void setChartData(@NonNull List<? extends ChartData> chartData) {
//        if (chartList == null) chartList = new ArrayList<>();
//        chartList.add(chartData);
//        invalidate();
//    }

    public void setChartList(@NonNull List<? extends List<? extends ChartData>> chartList) {
        this.chartList = chartList;
        invalidate();
    }

    public void getYMaxValue(List<ChartData> chartData) {
        double temp = 0;
        for (ChartData data : chartData) {
            if (data.getChartYValue() > temp)
                temp = data.getChartYValue();
        }
        this.maxPoint = (float) temp;
    }

    public float maxValue(List<? extends ChartData> chartData) {
        float temp = 0;
        for (ChartData data : chartData) {
            if (data.getChartYValue() > temp)
                temp = data.getChartYValue();
        }
        return temp;
    }

//    public void setValue(Map<String, Integer> value, List<String> xValue) {
//        this.value = value;
//        this.xValue = xValue;
//        invalidate();
//    }

    public void setMaxAndAvg(float max, float avg) {
        this.maxPoint = max;
        this.avgPoint = avg;
    }

//    public List<String> getxValue() {
//        return xValue;
//    }
//
//    public Map<String, Integer> getValue() {
//        return value;
//    }

    /**
     * 获取丈量文本的矩形
     *
     * @param text
     * @param paint
     * @return
     */
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    /**
     * dp转化成为px
     *
     * @param dp
     * @return
     */
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    /**
     * sp转化为px
     *
     * @param sp
     * @return
     */
    private int spToPx(int sp) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * sp + 0.5f * (sp >= 0 ? 1 : -1));
    }
}
