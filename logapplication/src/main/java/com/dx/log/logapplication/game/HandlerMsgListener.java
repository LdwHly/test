package com.dx.log.logapplication.game;


import io.socket.emitter.Emitter;

public interface HandlerMsgListener extends Emitter.Listener {
    /**
     * 子类处理数据
     */
    void hadlerData(String msg);
}
