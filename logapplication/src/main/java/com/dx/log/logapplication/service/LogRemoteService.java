package com.dx.log.logapplication.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.LruCache;

import com.alibaba.fastjson.JSON;
import com.dx.log.logapplication.LogFromServer;
import com.dx.log.logapplication.LogLog;
import com.dx.log.logapplication.game.ISocketClient;
import com.dx.log.logapplication.game.SocketClintPoxy;
import com.dx.log.logapplication.game.TWSocketClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LogRemoteService extends Service implements ISocketClient.StringDataCallback {
    public static WorkHandler workHandler;
    WorkThread workThread;
    SocketClintPoxy mSocketClintPoxy;
    LogRemoteIBinder logRemoteIBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        workThread = new WorkThread();
        workThread.start();
        logRemoteIBinder = new LogRemoteIBinder();
        mSocketClintPoxy = SocketClintPoxy.getInstance();
        mSocketClintPoxy.setStringDataCallback(this);
        mSocketClintPoxy.setTarget(new TWSocketClient());

    }

    @Override
    public IBinder onBind(Intent intent) {
        return logRemoteIBinder;
    }

    @Override
    public void receMsg(String msg) {
        try {
            LogLog.d("msg", msg);
            LogFromServer logFromServer = JSON.parseObject(msg, LogFromServer.class);
            if (TextUtils.isEmpty(logFromServer.url)) {
                logRemoteIBinder.d(logFromServer.tag, logFromServer.context);
            } else {
                logRemoteIBinder.println(logFromServer.tag, logFromServer.url, logFromServer.parms, logFromServer.context);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class LogRemoteIBinder extends ILogRemoteAidlInterface.Stub {

        @Override
        public void printlnTag(CharSequence packageName, CharSequence tag, CharSequence childTag, CharSequence msg) throws RemoteException {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(childTag);
            spannableStringBuilder.append(msg + "\n");
            if (!TextUtils.isEmpty(childTag)) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#ff0000"));
                spannableStringBuilder.setSpan(colorSpan, 0, childTag.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);//粗体
                spannableStringBuilder.setSpan(styleSpan, 0, childTag.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            d(tag, spannableStringBuilder);
        }

        @Override
        public void postWsUrl(String url, int port, String path) throws RemoteException {
            try {
                mSocketClintPoxy.connect(url, port, path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void println(CharSequence tag, CharSequence url, CharSequence parms, CharSequence response) throws RemoteException {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append("接口：" + url + "\n");
            spannableStringBuilder.append("参数：" + parms + "\n");
            if (!TextUtils.isEmpty(url)) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#ff0000"));
                spannableStringBuilder.setSpan(colorSpan, 3, 3 + url.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);//粗体
                spannableStringBuilder.setSpan(styleSpan, 0, 3 + url.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            try {
                if (!TextUtils.isEmpty(response)) {
                    UrlBean urlBean = JSON.parseObject(response.toString(), UrlBean.class);
                    if (!TextUtils.isEmpty(urlBean.data) && urlBean.data.length() > 300) {
                        urlBean.data = ".....";
                        spannableStringBuilder.append("返回：" + JSON.toJSONString(urlBean) + "\n\n");
                    } else {
                        spannableStringBuilder.append("返回：" + response + "\n\n");
                    }
                } else {
                    spannableStringBuilder.append("返回：" + response + "\n\n");
                }
            } catch (Exception e) {
                spannableStringBuilder.append("返回：" + response + "\n\n");
            } finally {
                d(tag, spannableStringBuilder);
            }
        }


        @Override
        public void showWindow() throws RemoteException {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !Settings.canDrawOverlays(getApplicationContext())) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return;
                }
                WindowUtils.showPopupWindow(getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void hideWindow() throws RemoteException {

        }

        @Override
        public void d(final CharSequence tag, final CharSequence msg) throws RemoteException {
            if (workHandler != null) {
                Message message = Message.obtain();
                message.what = WorkHandler.DEFAULT;
                MessageBean obj = new MessageBean();
                obj.msg = msg;
                obj.tag = tag;
                message.obj = obj;
                workHandler.sendMessage(message);
            }
        }

    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            workHandler = new WorkHandler();
            Looper.loop();
        }
    }

    public class WorkHandler extends Handler {
        public static final int DEFAULT = 0;
        public static final int CLEAR = 1;
        public static final int TAG = 2;
        public static final int CHANGE_TAG = 3;

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case DEFAULT:
                    if (!(msg.obj instanceof MessageBean)) {
                        return;
                    }
                    final MessageBean obj = (MessageBean) msg.obj;
                    boolean handlerData = handlerTextLog(obj);
                    if (handlerData) {
                        postShowData();
                    }
                    break;
                case CLEAR:
                    if (WindowUtils.ALL_TAG.equals(WindowUtils.select)) {
                        mLruCache.evictAll();
                    } else {
                        if (mLruCache.get(WindowUtils.select) != null) {
                            mLruCache.get(WindowUtils.select).sb.clear();
                            mLruCache.get(WindowUtils.select).data.clear();
                        }
                    }
                    postShowData();
                    break;
                case TAG:
                    final List<String> list = new ArrayList<>();
                    LinkedHashMap<String, LogBean> linkedHashMap = (LinkedHashMap<String, LogBean>) mLruCache.snapshot();
                    Iterator<Map.Entry<String, LogBean>> it = linkedHashMap.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = it.next();
                        list.add(entry.getKey().toString());
                    }
                    Collections.sort(list, new TagComparator());
                    WindowUtils.showTag(list);
                    break;
                case CHANGE_TAG:
                    postShowData();
                    break;
                default:
                    break;
            }
        }
    }

    private void postShowData() {
        if (mLruCache.get(WindowUtils.select) != null && !TextUtils.isEmpty(mLruCache.get(WindowUtils.select).sb.toString())) {
            SpannableStringBuilder sb = mLruCache.get(WindowUtils.select).sb;
            if (WindowUtils.ALL_TAG.equals(WindowUtils.select)) {
                WindowUtils.fleshTextLog(sb.toString());
            } else {
                WindowUtils.fleshTextLog(sb);
            }
        } else {
            WindowUtils.fleshTextLog(null);
        }
    }

    private boolean handlerTextLog(MessageBean obj) {
        if (obj.msg instanceof SpannableStringBuilder) {
            return handlerTextLog(obj.tag, obj.msg);
        } else {
            return handlerTextLog(obj.tag, obj.msg + "\n");
        }
    }

    private boolean handlerTextLog(CharSequence tag, CharSequence str) {
        preShowData(tag.toString(), str);
        tag = WindowUtils.ALL_TAG;
        preShowData(tag.toString(), str);
        return WindowUtils.isShowTextLog();
    }

    private static LruCache<String, LogBean> mLruCache = new LruCache<>((int) Runtime.getRuntime().totalMemory() / 5 * 4);

    private static void preShowData(String tag, CharSequence str) {
        LogBean logBean = mLruCache.get(tag);
        if (logBean == null) {
            logBean = new LogBean();
            logBean.tag = tag;
            if (WindowUtils.ALL_TAG.equals(tag)) {
                logBean.max = 100;
            } else {
                logBean.max = 50;
            }
            mLruCache.put(tag, logBean);
            Message message = Message.obtain();
            message.what = LogRemoteService.WorkHandler.TAG;
            workHandler.sendMessage(message);
        }
        logBean.sb.insert(0, str);
        logBean.data.add(0, str);

        if (logBean.data.size() > logBean.max) {
            int index = logBean.sb.toString().lastIndexOf(logBean.data.get(logBean.data.size() - 1).toString());
            if (index >= 0) {
                logBean.sb.replace(index, logBean.sb.length(), "");
            }
            logBean.data.remove(logBean.data.size() - 1);
        }
    }
}
