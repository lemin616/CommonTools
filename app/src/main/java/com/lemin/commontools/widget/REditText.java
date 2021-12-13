package com.lemin.commontools.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;



public class REditText extends AppCompatEditText {

    private OnRightClickListener onRightClickListener;

    public REditText(Context context) {
        this(context, null);
    }

    public REditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle); // Attention here !
    }

    public REditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[2];
            //如果右边没有图片，不再处理
            if (drawable == null)
                return false;

            boolean touchable = event.getX() > getWidth() - getTotalPaddingRight()
                    && event.getX() < getWidth() - getPaddingRight();

            if (touchable) {
                if (onRightClickListener != null)
                    onRightClickListener.onRightClick(this);
            }
        }
        return super.onTouchEvent(event);
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public interface OnRightClickListener{
        void onRightClick(View view);
    }
}
