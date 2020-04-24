package com.ldw.test.socketclienttest.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.ldw.test.socketclienttest.LogLog;

public class CListView extends ListView {
    public CListView(Context context) {
        super(context);
    }

    public CListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private float dwonY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //重点在这里
        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                super.onInterceptTouchEvent(ev);
                //不允许上层viewGroup拦截事件.
                getParent().requestDisallowInterceptTouchEvent(true);
                dwonY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //满足listView滑动到顶部，如果继续下滑，那就让scrollView拦截事件
                if (getFirstVisiblePosition() == 0 && (ev.getY() - dwonY) > 0) {
                    //允许上层viewGroup拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                //满足listView滑动到底部，如果继续上滑，那就让scrollView拦截事件
                else if (getLastVisiblePosition() == (getCount() - 1) && (ev.getY() - dwonY) < 0) {
                    //允许上层viewGroup拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    //不允许上层viewGroup拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                //不允许上层viewGroup拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }

        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
