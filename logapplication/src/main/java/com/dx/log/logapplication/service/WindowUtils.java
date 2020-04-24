package com.dx.log.logapplication.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dx.log.logapplication.R;

import java.util.List;

public class WindowUtils {
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;
    public static Boolean isShown = false;
    private static WindowManager.LayoutParams params;
    private static TextView tvContent;
    private static boolean isPause = false;
    public static final String ALL_TAG = "所有";
    public static String select = ALL_TAG;
    private static ListView listView;
    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 显示弹出框
     *
     * @param context
     */
    public static void showPopupWindow(final Context context) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isShown) {
                    return;
                }
                isShown = true;
                // 获取应用的Context
                mContext = context.getApplicationContext();
                // 获取WindowManager
                mWindowManager = (WindowManager) mContext
                        .getSystemService(Context.WINDOW_SERVICE);
                mView = setUpView(context);
                params = new WindowManager.LayoutParams();
                // 类型
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    params.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                // 设置flag
                int flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
                params.flags = flags;
                // 不设置这个弹出框的透明遮罩显示为黑色
                params.format = PixelFormat.TRANSLUCENT;
                // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
                // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
                // 不设置这个flag的话，home页的划屏会有问题
//        params.width = dp2px(context, 320);
                params.height = dp2px(context, 300);
                params.gravity = Gravity.TOP;
                mWindowManager.addView(mView, params);
                Message message = Message.obtain();
                message.what = LogRemoteService.WorkHandler.TAG;
                sendMsgToWorkThread(message);
            }
        });
    }

    private static void sendMsgToWorkThread(Message message) {
        if (LogRemoteService.workHandler != null) {
            LogRemoteService.workHandler.sendMessage(message);
        }
    }


    public static boolean isShowTextLog() {
        if (isShown && !isPause) {
            return true;
        }
        return false;
    }


    public static void fleshTextLog(final CharSequence spannableStringBuilder) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isShown) {
                    if (spannableStringBuilder != null) {
                        tvContent.setText(spannableStringBuilder);
                    } else {
                        tvContent.setText("");
                    }
                }
            }
        });

    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        if (isShown && null != mView) {
            isShown = false;
            mWindowManager.removeView(mView);
        }
    }

    private static View setUpView(final Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.popupwindow,
                null);
        listView = view.findViewById(R.id.lv_list);
        Button positiveBtn = (Button) view.findViewById(R.id.btn_all);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = LogRemoteService.WorkHandler.TAG;
                sendMsgToWorkThread(message);
            }
        });
        final Button negativeBtn = (Button) view.findViewById(R.id.btn_close);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtils.hidePopupWindow();
            }
        });
        view.setOnTouchListener(new FloatingOnTouchListener());
        tvContent = view.findViewById(R.id.tv_content);
        view.findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = LogRemoteService.WorkHandler.CLEAR;
                sendMsgToWorkThread(message);
            }
        });
        final Button btnPause = view.findViewById(R.id.btn_pause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPause = !isPause;
                if (isPause) {
                    btnPause.setText("开始");
                } else {
                    btnPause.setText("暂停");
                }
            }
        });
        if (isPause) {
            btnPause.setText("开始");
        } else {
            btnPause.setText("暂停");
        }
        view.post(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = LogRemoteService.WorkHandler.TAG;
                sendMsgToWorkThread(message);
            }
        });
        return view;
    }

    public static void showTag(final List<String> list) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (listView == null) {
                    return;
                }
                final TagAdapter adapter = new TagAdapter(list, mContext, R.layout.item_tag);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        select = list.get(position);
                        adapter.notifyDataSetChanged();
                        Message message = Message.obtain();
                        message.what = LogRemoteService.WorkHandler.CHANGE_TAG;
                        sendMsgToWorkThread(message);
                    }
                });
            }
        });
    }


    /**
     * dpתpx
     *
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    private static class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    params.x = params.x + movedX;
                    params.y = params.y + movedY;

                    // 更新悬浮窗控件布局
                    mWindowManager.updateViewLayout(view, params);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean startGetPermission(Context activity) {
        try {
            if (!Settings.canDrawOverlays(activity)) {
                activity.startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName())));
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
