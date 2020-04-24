package com.ldw.test.socketclienttest.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ldw.test.socketclienttest.LogLog;

public class ChildView2 extends View {
    public final String TAG = ChildView2.class.getSimpleName();

    public ChildView2(Context context) {
        super(context);
    }

    public ChildView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildView2(Context context, AttributeSet attrs, int defStyleAttr) {
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


    float downx, downy;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                LogLog.d("Event_Test", TAG + " " + "onTouchEvent + 按下");
                break;
            case MotionEvent.ACTION_MOVE:
                int movex = (int) (event.getX() - downx);
                int movey = (int) (event.getY() - downy);
                int left = getLeft();
                int top = getTop();
                int right = getRight();
                int bottom = getBottom();
                if (movex < 0) {
                    if (left < (-movex)) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                }
                if (movex > 0) {
                    if (right < movex) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                }

                if (movey < 0) {
                    if (top < (-movey)) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                }
                if (movey > 0) {
                    if (bottom < movey) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    }
                }
                View viewGroup = (View) getParent();
                //开启滑动
                ((View) getParent()).scrollBy( -movex, -movey);
                LogLog.d("Event_Test", TAG + " " + right + " " + bottom + " " + movex + "  " + movey);
//                offsetLeftAndRight((int) movex);
//                offsetTopAndBottom((int) movey);
                LogLog.d("Event_Test", TAG + " " + "onTouchEvent + 滑动");
                break;
            case MotionEvent.ACTION_UP:
                LogLog.d("Event_Test", TAG + " " + "onTouchEvent + 抬起");
                break;
        }
        return true;
    }
}
