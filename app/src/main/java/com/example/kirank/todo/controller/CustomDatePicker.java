package com.example.kirank.todo.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.DatePicker;

/**
 * Created by kirank on 8/12/17.
 */

public class CustomDatePicker extends DatePicker {

    public CustomDatePicker(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDatePicker(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomDatePicker(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomDatePicker(final Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        /* Prevent parent controls from stealing our events once we've
gotten a touch down */
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN)
        {
            ViewParent p = getParent();
            if (p != null)
                p.requestDisallowInterceptTouchEvent(true);
        }

        return false;
    }

}
