package com.ldw.test.socketclienttest.game.bean.bean;


import com.ldw.test.socketclienttest.game.bean.base.BaseResponse;
import com.ldw.test.socketclienttest.game.bean.base.MessageType;

import java.util.HashMap;

public class T011ToyBean extends BaseResponse<HashMap<String, ToyBean>> {
    public String type = MessageType.T011;



}
