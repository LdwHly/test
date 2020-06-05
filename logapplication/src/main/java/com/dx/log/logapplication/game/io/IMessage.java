package com.dx.log.logapplication.game.io;

import com.alibaba.fastjson.annotation.JSONField;

public interface IMessage {
    @JSONField(serialize = false)
    String getAction();
}
