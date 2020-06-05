package com.dx.log.logapplication.game.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dx.log.logapplication.LogLog;
import com.dx.log.logapplication.game.ISocketClient;
import com.dx.log.logapplication.game.bean.base.MessageType;
import com.dx.log.logapplication.game.bean.bean.T011ToyBean;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketIoClientInstance {
    private volatile Socket mSocket;
    private static String CHAT_SERVER_URL = "https://test9.lovense.com?ntoken=2ae60a8ad948f9c246afafaf30efc396";
    private ISocketClient.StringDataCallback dataCallback;

    private static final String EVENT_MESSAGE_TOYS_INFO_VR = "ToysInfoVR";// 开始游戏
    private static final String EVENT_MESSAGE_SHAKE_VR = "ShakeVR";// 开始游戏


    private Object obj = new Object();//对象锁

    public static SocketIoClientInstance getInstance() {
        return SingletonHolder.sInstance;
    }


    /*静态内部类*/
    private static class SingletonHolder {
        private static final SocketIoClientInstance sInstance = new SocketIoClientInstance();
    }

    private SocketIoClientInstance() {
    }

    public void setDataCallback(ISocketClient.StringDataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    public void connect() {
        synchronized (obj) {
            if (mSocket == null) {
                try {
                    IO.Options opts = new IO.Options();
                    opts.path = "/vr_game_ws";
                    URI uri = new URI(CHAT_SERVER_URL);
                    mSocket = IO.socket(uri, opts);
                    initListener();
                } catch (URISyntaxException e) {
                }
            }
            if (mSocket.connected()) {
                return;
            }
            mSocket.connect();
        }
    }

    private void initListener() {
        mSocket.on(Socket.EVENT_CONNECT, new ConnectListener(Socket.EVENT_CONNECT));
        mSocket.on(EVENT_MESSAGE_TOYS_INFO_VR, new ToysInfoVRListener());
        mSocket.on(EVENT_MESSAGE_SHAKE_VR, new ShakeVRListener());
    }

    private class ShakeVRListener extends BaseListener {

        @Override
        public void hadlerData(String msg) {
            dataCallback.receMsg(msg);
        }
    }

    private class ToysInfoVRListener extends BaseListener {

        @Override
        public void hadlerData(String msg) {
            T011ToyBean t011ToyBean = JSON.parseObject(msg, T011ToyBean.class);
            t011ToyBean.type = MessageType.T011;
            Map<String, Object> map = getMap(t011ToyBean);
            Object string = JSONObject.toJSON(map);
            dataCallback.receMsg(string.toString());
        }
    }

    public void sendMsg(IMessage msg) {
        synchronized (obj) {
            if (mSocket == null) {
                return;
            }
            Map<String, Object> map = getMap(msg);
            Object string = JSONObject.toJSON(map);
            LogLog.d("SocketIoClient", "sendMsg:" + msg.getAction() + "==", "==" + string.toString());
            mSocket.emit(msg.getAction(), string);
        }
    }

    /**
     * 反射获取将javaBean转换成TreeMap，而TreeMap默认排序升序
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    private final Map<String, Object> getMap(Object javaBean) {
        Map<String, Object> map = new TreeMap<>();
        Field[] fields = javaBean.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {
            String firstLetter = fields[i].getName();
            Object value = null;
            try {
                value = fields[i].get(javaBean);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (value != null) {
                map.put(firstLetter, value);
            }
        }

        return map;
    }

}
