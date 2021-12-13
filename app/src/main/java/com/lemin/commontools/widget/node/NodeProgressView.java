package com.lemin.commontools.widget.node;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.lemin.commontools.R;
import com.lemin.commontools.helper.DisplayHelper;
import com.lemin.commontools.helper.StringHelper;

import java.util.List;



public class NodeProgressView extends View {

    float width;
    float nodeRadius;

    Paint paint;

    Context context;

    /**
     * 节点间隔
     */
    int nodeInterval;

    /**
     * 边距
     */
    int left = 20;
    int top = 30;

    int dWidth;
    int dHeight;
    private NodeProgressAdapter nodeProgressAdapter;

    public NodeProgressView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public NodeProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NodeProgressView);
        width = typedArray.getDimension(R.styleable.NodeProgressView_width, 5);
        nodeRadius = typedArray.getDimension(R.styleable.NodeProgressView_nodeRadius, 10);
        init();
    }

    public NodeProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(StringHelper.resColor(R.color.gray_cc));
        paint.setAntiAlias(true);

        nodeInterval = DisplayHelper.dip2px(80);

        DisplayMetrics displayMetrics = DisplayHelper.displayMetrics(context);
        dWidth = displayMetrics.widthPixels;
        dHeight = displayMetrics.heightPixels;
    }

    /**
     * 设置适配数据
     */
    public void setNodeProgressAdapter(NodeProgressAdapter nodeProgressAdapter) {
        this.nodeProgressAdapter = nodeProgressAdapter;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (nodeProgressAdapter == null || nodeProgressAdapter.getCount() == 0)
            return;

        List data = nodeProgressAdapter.getData();
        canvas.drawRect(left, top, width + left, nodeProgressAdapter.getCount() * nodeInterval + top, paint);
        for (int i = 0; i < nodeProgressAdapter.getCount(); i++) {
            if (i == 0) {
                Paint mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setColor(StringHelper.resColor(R.color.yellow_fc));
                //画文字
                mPaint.setTextSize(30);
                canvas.drawText(((NodeData) data.get(i)).getStatus(), left * 2 + nodeRadius * 2 + 10, (i + 1) * nodeInterval + top - 10, mPaint);

                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setColor(StringHelper.resColor(R.color.black));
                //画文字
                mPaint.setTextSize(30);
                canvas.drawText(((NodeData) data.get(i)).getTime(), left * 2 + nodeRadius * 2 + 10, (i + 1) * nodeInterval + top - 20, mPaint);
                //文字换行
                TextPaint textPaint = new TextPaint();
                textPaint.setColor(StringHelper.resColor(R.color.black));
                textPaint.setTextSize(35.0F);
                textPaint.setAntiAlias(true);
                StaticLayout layout = new StaticLayout(((NodeData) data.get(i)).getContent(), textPaint, (int) (dWidth * 0.8), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                canvas.save();
                canvas.translate(left * 2 + nodeRadius * 2 + 10, i * nodeInterval + top + (nodeInterval / 4));
                layout.draw(canvas);
                canvas.restore();//重置

                //画圆
                canvas.drawCircle(width / 2 + left, i * nodeInterval + top, nodeRadius + 2, mPaint);
                mPaint.setStyle(Paint.Style.STROKE);//设置为空心
                mPaint.setStrokeWidth(8);//空心宽度
                mPaint.setAlpha(88);
                canvas.drawCircle(width / 2 + left, i * nodeInterval + top, nodeRadius + 4, mPaint);
            } else {
                paint.setColor(StringHelper.resColor(R.color.gray_cc));
                canvas.drawCircle(width / 2 + left, i * nodeInterval + top, nodeRadius, paint);
                canvas.drawLine(left * 2 + nodeRadius * 2, i * nodeInterval + top, dWidth, i * nodeInterval + top, paint); //画线

                //画文字
                paint.setTextSize(30);
                canvas.drawText(((NodeData) data.get(i)).getStatus(), left * 2 + nodeRadius * 2 + 10, (i + 1) * nodeInterval + top - 10, paint);
                canvas.drawText(((NodeData) data.get(i)).getTime(), left * 2 + nodeRadius * 2 + 10, (i + 1) * nodeInterval + top - 20, paint);
                //文字换行
                TextPaint textPaint = new TextPaint();
                textPaint.setColor(StringHelper.resColor(R.color.gray_cc));
                textPaint.setTextSize(35.0F);
                textPaint.setAntiAlias(true);
                StaticLayout layout = new StaticLayout(((NodeData) data.get(i)).getContent(), textPaint, (int) (dWidth * 0.8), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                canvas.save();//很重要，不然会样式出错
                canvas.translate(left * 2 + nodeRadius * 2 + 10, i * nodeInterval + top + (nodeInterval / 4));
                layout.draw(canvas);
                canvas.restore();//重置
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (nodeProgressAdapter == null || nodeProgressAdapter.getCount() == 0)
            return;
        setMeasuredDimension(widthMeasureSpec, nodeProgressAdapter.getCount() * nodeInterval + top);
    }
}
