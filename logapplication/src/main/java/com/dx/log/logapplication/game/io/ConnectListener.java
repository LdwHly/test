package com.dx.log.logapplication.game.io;



import com.dx.log.logapplication.LogLog;

import io.socket.client.Socket;

public class ConnectListener extends BaseListener {
    private String tag;

    public ConnectListener(String tag) {
        this.tag = tag;
    }


    @Override
    public void hadlerData(String msg) {
        LogLog.d(ConnectListener.class.getSimpleName(), Socket.EVENT_CONNECT, msg);
    }
}
