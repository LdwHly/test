package com.ldw.test.socketclienttest.game;

public class SocketClintPoxy implements ISocketClient {
    private static SocketClintPoxy instance;
    StringDataCallback mDataCallback;

    public static SocketClintPoxy getInstance() {
        if (instance == null) {
            synchronized (SocketClintPoxy.class) {
                if (instance == null) {
                    instance = new SocketClintPoxy();
                }
            }
        }
        return instance;
    }

    private ISocketClient target;

    public void setTarget(ISocketClient target) {
        this.target = target;
        target.setStringDataCallback(mDataCallback);
    }

    @Override
    public void connect(String url, int port, String path) {
        if (target != null)
            target.connect(url, port, path);
    }

    @Override
    public void sendMsg(String msg) {
        target.sendMsg(msg);
    }

    @Override
    public void setStringDataCallback(StringDataCallback mDataCallback) {
        this.mDataCallback = mDataCallback;
        if (target != null) {
            target.setStringDataCallback(mDataCallback);
        }
    }

    @Override
    public void disConnect() {
        if (target != null) {
            target.disConnect();
        }
    }


}
