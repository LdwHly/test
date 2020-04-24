package com.ldw.test.socketclienttest.game;

public interface ISocketClient {
    void connect(String url, int port, String path);

    void sendMsg(String msg);

    void setStringDataCallback(StringDataCallback mDataCallback);

    void disConnect();

    interface StringDataCallback {
        void receMsg(String msg);
    }
}
