package com.dx.log.logapplication.game.io;


import com.dx.log.logapplication.LogLog;
import com.dx.log.logapplication.game.HandlerMsgListener;

public abstract class BaseListener implements HandlerMsgListener {
    @Override
    public void call(Object... args) {
        String msg = "";
        if (args.length > 0) {
            for (Object obj : args) {
                msg += (obj.toString());
            }
            try {
                hadlerData(args[0].toString());
            } catch (Exception e) {

            }
        } else {
            try {
                hadlerData("");
            } catch (Exception e) {

            }
        }
//        LogLog.d("SocketIoClient", "sendMsg:" + msg.getAction() + "==", datingId + "==" + string.toString());
        LogLog.d("SocketIoClient", this.getClass().getSimpleName(), "==" + msg);
    }
}
