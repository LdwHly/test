package com.ldw.test.socketclienttest;


import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.dx.log.logapplication.service.ILogRemoteAidlInterface;

public class LogLog {
    private static final String TAG = LogLog.class.getSimpleName();
    public static final String REQUEST = "Request";

    public static void println(CharSequence tag, CharSequence url, CharSequence parms, CharSequence response) {
        try {
            MyApplication.getRemoteBinder().println(tag, url, parms, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(String tag, CharSequence msg) {
        d(tag, "", msg);
    }

    public static void d(String tag, CharSequence childTag, CharSequence msg) {
        printlnTag(tag, childTag, msg);
    }

    private static void printlnTag(String tag, CharSequence childTag, CharSequence msg) {
        try {
            if (msg == null) {
                msg = "";
            }
            ILogRemoteAidlInterface remoteAidlInterface = MyApplication.getRemoteBinder();
            if (remoteAidlInterface != null) {
                remoteAidlInterface.printlnTag("Remote", tag, childTag, msg + "\n");
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if (TextUtils.isEmpty(tag)) {
            Log.d(TAG, childTag + ":" + msg.toString());
        } else {
            Log.d(tag, childTag + ":" + msg.toString());
        }
    }

    public static void printlnThread() {
        LogLog.d("printlnThread", "当前是否是主线程==" + (Thread.currentThread() == Looper.getMainLooper().getThread()));
    }
}
