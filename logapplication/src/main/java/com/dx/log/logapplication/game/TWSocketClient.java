package com.dx.log.logapplication.game;

import android.util.Log;

import com.dx.log.logapplication.LogLog;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;


public class TWSocketClient implements ISocketClient {
    WebSocket mWebSocket;
    StringDataCallback mDataCallback;

    @Override
    public void connect(String url, int port, String path) {
        url = "ws://" + url + ":" + port + path;
        AsyncHttpClient.getDefaultInstance().websocket(url, null, new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, WebSocket webSocket) {
                if (ex != null) {
                    ex.printStackTrace();
                    return;
                }
                mWebSocket = webSocket;
                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String s) {
//                        Log.d("TWSocketClient", "String==" + s);
                        if (mDataCallback != null) {
                            mDataCallback.receMsg(s);
                        }
                    }
                });
                webSocket.setDataCallback(new DataCallback() {
                    @Override
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                        if (mDataCallback != null) {
                            mDataCallback.receMsg(new String(bb.getAllByteArray()));
                        }
                    }
                });
                webSocket.setClosedCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception e) {
                        mWebSocket = null;
                    }
                });
                webSocket.setEndCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception e) {
                        mWebSocket = null;
                    }
                });
            }
        });
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
    }

    @Override
    public boolean isConnected() {
        if (mWebSocket != null) {
            mWebSocket.isOpen();
        }
        return false;
    }
}
