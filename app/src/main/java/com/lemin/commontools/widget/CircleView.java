package com.lemin.commontools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.lemin.commontools.R;

/**
 * @实现圆角和圆形控件
 * @type： 0 圆角图形 1 圆形图形
 * @borderRadius 圆角的半径
 */
public class CircleView extends ImageView {
    private static final int TYPE_CIRCLE = 1;// 圆形图形
    private static final int TYPE_ROUND = 0;// 圆角图形
    private static final int BORDER_RADIUS_DEFAULT = 10;// 默认圆角大小
    private Context context;
    private Paint mPaint;
    private Matrix mMatrix;
    private RectF mRoundRect;// 圆角矩阵
    private int mRadius;// 圆角半径
    private int mBorderRadius = -1;// 圆角大小
    private int type;// 类型
    private int mWidth;// 视图宽度
    private BitmapShader mBitmapShader;// 渲染图像，使用图像为绘制图形着色

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        TypedArray a = this.context.obtainStyledAttributes(attrs, R.styleable.RoundImageViewAttr, defStyle, defStyle);
        type = a.getInt(R.styleable.RoundImageViewAttr_type, TYPE_CIRCLE);
        mBorderRadius = a
                .getDimensionPixelSize(R.styleable.RoundImageViewAttr_borderradius, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, BORDER_RADIUS_DEFAULT, getResources().getDisplayMetrics()));
        a.recycle();
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleView(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        this.context = context;
        mMatrix = new Matrix();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null)
            return;
        Bitmap bmp = drawable2Bitmap(drawable);
        mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        float scaleY = 1.0f;
        if (type == TYPE_CIRCLE) {
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = mWidth * 1.0f / bSize;
            scaleY = mWidth * 1.0f / bmp.getHeight();
        } else if (type == TYPE_ROUND) {
            scale = Math.max(getWidth() * 1.0f / bmp.getWidth(), getHeight() * 1.0f / bmp.getHeight());
            scaleY = scale;
        }
        mMatrix.setScale(scale, scaleY);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);
    }

    /**
     * Drawable转换成 bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawable2Bitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null)
            return;
        setUpShader();
        if (mBorderRadius == 0)
            mBorderRadius = getWidth() < getHeight() ? getHeight() / 2 : getWidth() / 2;
        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mPaint);
        } else if (type == TYPE_CIRCLE) {
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        }
    }

    /**
     * 改变视图大小回调 修改圆角矩阵大小
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (type == TYPE_ROUND) {
            mRoundRect = new RectF(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * 设置ImageView类型
     *
     * @param type
     *            0 圆角图形 1圆形图形
     */
    public void setType(int type) {
        if (this.type != type) {
            this.type = type;
            if (this.type != TYPE_CIRCLE && this.type != TYPE_ROUND) {
                this.type = TYPE_CIRCLE;
            }
            // 该view本身调用这个方法要求parent view重新调用他的onMeasure onLayout来对重新设置自己位置。
            // 特别的当view的layoutparameter发生改变，并且它的值还没能应用到view上，这时候适合调用这个方法。
            requestLayout();
        }
    }

    /**
     * 设置圆角图片圆角
     *
     * @param borderRadius
     *            不设置或设置为-1 那么圆角为宽高中较大的一半
     */
    public void setBorderRadius(int borderRadius) {
        int pxValue = dp2px(borderRadius);
        if (this.mBorderRadius != pxValue) {
            this.mBorderRadius = pxValue;
            invalidate();
        }
    }

    public int dp2px(int dpValue) {
        return (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    /**
     * 保存状态
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("state_instance", super.onSaveInstanceState());
        bundle.putInt("state_type", type);
        bundle.putInt("state_border_radius", mBorderRadius);
        return bundle;
    }

    /**
     * 恢复状态
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("state_instance"));
            this.type = bundle.getInt("state_type");
            this.mBorderRadius = bundle.getInt("state_border_radius");
        } else {
            super.onRestoreInstanceState(state);
        }
    }
}
