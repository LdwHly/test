package com.ldw.test.socketclienttest.game;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.ldw.test.socketclienttest.LogLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class TWSocketClient implements ISocketClient {
    WebSocket mWebSocket;
    StringDataCallback mDataCallback;
    Handler handler = new Handler(Looper.getMainLooper());
    HashMap<String,String> object = new HashMap<>();
    @Override
    public void connect(String url, int port, String path) {
//        url = "ws://" + url + ":" + port + path;
        object.put("type","H010");
        AsyncHttpClient.getDefaultInstance().websocket(url, null, new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, WebSocket webSocket) {
                if (ex != null) {
                    ex.printStackTrace();
                    return;
                }
                mWebSocket = webSocket;
                handler.removeCallbacksAndMessages(null);
                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String s) {
                        Log.d("TWSocketClient", "String==" + s);
                        if (mDataCallback != null) {
                            mDataCallback.receMsg(s);
                        }
                    }
                });
                webSocket.setDataCallback(new DataCallback() {
                    @Override
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                        if (mDataCallback != null) {
                            mDataCallback.receMsg("Data==" + new String(bb.getAllByteArray()));
                        }
                    }
                });
                webSocket.setClosedCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception e) {
                        Log.d("TWSocketClient", "ClosedCallback==" + e.getMessage());
                        handler.removeCallbacksAndMessages(null);
                    }
                });
                webSocket.setEndCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception e) {
                        Log.d("TWSocketClient", "EndCallback==" + e.getMessage());
                        handler.removeCallbacksAndMessages(null);
                    }
                });

                ping();
            }
        });
    }

    private void ping() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                sendMsg(JSON.toJSONString(object));
//                ping();
            }
        }, 1000);
    }

    @Override
    public void sendMsg(String msg) {
        if (mWebSocket != null) {
            LogLog.d("SocketActivity", "发送：" + msg);
            mWebSocket.send(msg);
        }
    }

    @Override
    public void setStringDataCallback(StringDataCallback mDataCallback) {
        this.mDataCallback = mDataCallback;
    }

    @Override
    public void disConnect() {
        if (mWebSocket != null) {
            mWebSocket.close();
        }
        mDataCallback = null;
    }


}
