package com.dx.log.logapplication.game;

public interface ISocketClient {
    void connect(String url, int port, String path);

    void sendMsg(String msg);

    void setStringDataCallback(StringDataCallback mDataCallback);

    void disConnect();

    boolean isConnected();

    interface StringDataCallback {
        void receMsg(String msg);
    }
}
