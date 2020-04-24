package com.ldw.test.socketclienttest.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ldw.test.socketclienttest.LogLog;

public class ChildView1 extends View {
    public final String TAG = ChildView1.class.getSimpleName();
    public ChildView1(Context context) {
        super(context);
    }

    public ChildView1(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView1(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogLog.d("Event_Test", TAG + " " + "dispatchTouchEvent + 按下");
                break;
            case MotionEvent.ACTION_MOVE:
                LogLog.d("Event_Test", TAG + " " + "dispatchTouchEvent + 滑动");
                break;
            case MotionEvent.ACTION_UP:
                LogLog.d("Event_Test", TAG + " " + "dispatchTouchEvent + 抬起");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogLog.d("Event_Test", TAG + " " + "onTouchEvent + 按下");
                break;
            case MotionEvent.ACTION_MOVE:
                LogLog.d("Event_Test", TAG + " " + "onTouchEvent + 滑动");
                break;
            case MotionEvent.ACTION_UP:
                LogLog.d("Event_Test", TAG + " " + "onTouchEvent + 抬起");
                break;
        }
        return super.onTouchEvent(event);
    }
}
