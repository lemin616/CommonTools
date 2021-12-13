package com.lemin.commontools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lemin.commontools.R;
import com.lemin.commontools.helper.StringHelper;
import com.lemin.commontools.listener.OnItemViewClickListener;



public class ItemView extends LinearLayout implements View.OnClickListener {

    Drawable mLeftBackgroundDrawable;
    Drawable mRightBackgroundDrawable;
    Drawable mLeftIcon;
    Drawable mRightIcon;
    String mLeftText = "";
    String mRightText = "";
    float mLeftSize;
    float mRightSize;
    int mLeftColor;
    int mRightColor;
    int mDrawablePadding;
    TextView mLeftView;
    TextView mRightView;
    private OnItemViewClickListener onItemViewClickListener;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        mLeftIcon = a.getDrawable(R.styleable.ItemView_left_drawable);
        mRightIcon = a.getDrawable(R.styleable.ItemView_right_drawable);
        mLeftBackgroundDrawable = a.getDrawable(R.styleable.ItemView_left_background_drawable);
        mRightBackgroundDrawable = a.getDrawable(R.styleable.ItemView_right_background_drawable);
        mLeftText = a.getString(R.styleable.ItemView_left_label);
        mRightText = a.getString(R.styleable.ItemView_right_label);
        mLeftSize = a.getDimension(R.styleable.ItemView_left_size, 18);
        mRightSize = a.getDimension(R.styleable.ItemView_right_size, 18);
        mLeftColor = a.getColor(R.styleable.ItemView_left_color, Color.BLACK);
        mRightColor = a.getColor(R.styleable.ItemView_right_color, Color.BLACK);
        mDrawablePadding = a.getDimensionPixelSize(R.styleable.ItemView_drawablePadding, 0);
        a.recycle();
        init();
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    /**
     * 初始化数据
     */
    private void init() {
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        mLeftView = new TextView(getContext());
        mRightView = new TextView(getContext());
        mLeftView.setId(R.id.itemview_left);
        mLeftView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftSize);
        mLeftView.setTextColor(mLeftColor);
        mLeftView.setText(mLeftText);
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(mLeftIcon, null, null, null);
        mRightView.setId(R.id.itemview_right);
        mRightView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightSize);
        mRightView.setTextColor(mRightColor);
        mRightView.setText(mRightText);
        mRightView.setCompoundDrawablesWithIntrinsicBounds(null, null, mRightIcon, null);
        mLeftView.setCompoundDrawablePadding(mDrawablePadding);
        mRightView.setCompoundDrawablePadding(mDrawablePadding);
        mLeftView.setBackground(mLeftBackgroundDrawable);
        mRightView.setBackground(mRightBackgroundDrawable);
        mLeftView.setGravity(Gravity.CENTER_VERTICAL);
        mRightView.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams leftParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        leftParams.weight = 1;
        LayoutParams rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(mLeftView, leftParams);
        addView(mRightView, rightParams);
    }

    public ItemView setLeftText(CharSequence text) {
        setLeftText(null, text);
        return this;
    }

    public ItemView setLeftText(Drawable drawable, CharSequence text) {
        mLeftIcon = null == drawable ? mLeftIcon : drawable;
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(mLeftIcon, null, null, null);
        mLeftText = String.valueOf(text);
        mLeftView.setText(text);
        return this;
    }


    public ItemView setLeftText(String text) {
        setLeftText(null, text);
        return this;
    }

    public ItemView setLeftText(Drawable drawable, String text) {
        mLeftIcon = null == drawable ? mLeftIcon : drawable;
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(mLeftIcon, null, null, null);
        mLeftText = text;
        mLeftView.setText(text);
        return this;
    }

    public ItemView setLeftColor(int leftColor) {
        mLeftColor = leftColor;
        mLeftView.setTextColor(mLeftColor);
        return this;
    }

    public ItemView setLeftBackground(int leftBackground) {
        setLeftBackgroundDrawable(StringHelper.resDrawable(leftBackground));
        return this;
    }

    public ItemView setLeftBackgroundDrawable(Drawable leftBackgroundDrawable) {
        mLeftBackgroundDrawable = leftBackgroundDrawable;
        mLeftView.setBackground(leftBackgroundDrawable);
        return this;
    }

    public ItemView setRightText(CharSequence text) {
        mRightText = String.valueOf(text);
        mRightView.setText(text);
        return this;
    }

    public ItemView setRightText(String text) {
        setRightText(null, text);
        return this;
    }

    public ItemView setRightText(Drawable drawable, String text) {
        mRightIcon = null == drawable ? mRightIcon : drawable;
        mRightView.setCompoundDrawablesWithIntrinsicBounds(null, null, mRightIcon, null);
        mRightText = text;
        mRightView.setText(text);
        return this;
    }

    public ItemView setRightColor(int rightColor) {
        mRightColor = rightColor;
        mRightView.setTextColor(mRightColor);
        return this;
    }

    public ItemView setRightBackground(int rightBackground) {
        setRightBackgroundDrawable(StringHelper.resDrawable(rightBackground));
        return this;
    }

    public ItemView setRightBackgroundDrawable(Drawable rightBackgroundDrawable) {
        mRightBackgroundDrawable = rightBackgroundDrawable;
        mRightView.setBackground(rightBackgroundDrawable);
        return this;
    }

    public ItemView setItemViewListener(OnItemViewClickListener clickListener) {
        onItemViewClickListener = clickListener;
        if (mLeftView != null)
            mLeftView.setOnClickListener(this);
        if (mRightView != null)
            mRightView.setOnClickListener(this);
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.itemview_left) {
            onItemViewClickListener.onItemViewLeft(this);
        } else if (v.getId() == R.id.itemview_right) {
            onItemViewClickListener.onItemViewRight(this);
        }
    }

    public String getLeftText() {
        return mLeftText;
    }

    public String getRightText() {
        return TextUtils.isEmpty(mRightText)?"":mRightText;
    }

    public String getTexts() {
        if (mLeftText == null)
            mLeftText = "";
        if (mRightText == null)
            mRightText = "";
        if (mLeftText.contains(":") || mLeftText.contains("：")) {
            return mLeftText + mRightText;
        }
        return mLeftText + "：" + mRightText;
    }

    public TextView getmRightView() {
        return mRightView;
    }
}
