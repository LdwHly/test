package com.ldw.test.socketclienttest.game.bean.bean;


import com.ldw.test.socketclienttest.game.bean.base.BaseResponse;
import com.ldw.test.socketclienttest.game.bean.base.MessageType;
import com.ldw.test.socketclienttest.game.io.IMessage;

public class T010ToyBean extends BaseResponse<String> implements IMessage {
    public String type = MessageType.T010;

    @Override
    public String getAction() {
        return "GetToysVR";
    }
}
