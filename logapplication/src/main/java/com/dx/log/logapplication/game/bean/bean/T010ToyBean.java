package com.dx.log.logapplication.game.bean.bean;


import com.dx.log.logapplication.game.bean.base.BaseResponse;
import com.dx.log.logapplication.game.bean.base.MessageType;
import com.dx.log.logapplication.game.io.IMessage;

public class T010ToyBean extends BaseResponse<String> implements IMessage {
    public String type = MessageType.T010;

    @Override
    public String getAction() {
        return "GetToysVR";
    }
}
