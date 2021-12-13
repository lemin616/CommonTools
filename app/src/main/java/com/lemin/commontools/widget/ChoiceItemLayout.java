package com.lemin.commontools.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.lemin.commontools.R;



public class ChoiceItemLayout extends LinearLayout implements Checkable {

    private boolean mChecked;
    public ChoiceItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        setBackgroundResource(checked? R.drawable.select : R.drawable.unselect);
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
