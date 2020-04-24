package com.ldw.test.socketclienttest.game.io;

import com.alibaba.fastjson.annotation.JSONField;

public interface IMessage {
    @JSONField(serialize = false)
    String getAction();
}
