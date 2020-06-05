package com.dx.log.logapplication.game;

import java.util.Timer;
import java.util.TimerTask;

public class SocketClintPoxy implements ISocketClient {
    private static SocketClintPoxy instance;
    StringDataCallback mDataCallback;
    private String url;
    private int port;
    private String path;

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

    private SocketClintPoxy() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 500, 2000);
    }

    private ISocketClient target;
    private Timer timer;

    public void setTarget(ISocketClient target) {
        try {
            if (this.target != null) {
                this.target.disConnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.target = target;
            target.setStringDataCallback(mDataCallback);
        }
    }

    @Override
    public void connect(String url, int port, String path) {
        if (target != null) {
            if (url.equals(this.url) && isConnected()) {
                return;
            } else if (!url.equals(this.url)) {
                if (isConnected()) {
                    disConnect();
                }
            }
            this.url = url;
            this.port = port;
            this.path = path;
            connect();

        }
    }

    private void connect() {
        if (target != null) {
            target.connect(url, port, path);
        }
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

    @Override
    public boolean isConnected() {
        if (target != null) {
            target.isConnected();
        }
        return false;
    }
}
