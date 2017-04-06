package com.henghao.parkland.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ViewLinearLayout extends LinearLayout {

    public ViewLinearLayout(Context context) {
        super(context);
    }

    public ViewLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
