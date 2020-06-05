package com.dx.log.logapplication.game;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dx.log.logapplication.game.bean.base.BaseResponse;
import com.dx.log.logapplication.game.bean.base.MessageType;
import com.dx.log.logapplication.game.bean.bean.G010Bean;
import com.dx.log.logapplication.game.bean.bean.G020Bean;
import com.dx.log.logapplication.game.bean.bean.T010ToyBean;
import com.dx.log.logapplication.game.bean.bean.T011ToyBean;

import java.util.HashMap;
import java.util.Map;


public class HandlerMessageTool {

    private final static Map<String, Class<? extends BaseResponse>> MSGOBJECT = new HashMap<String, Class<? extends BaseResponse>>() {{
        put(MessageType.T011, T011ToyBean.class);
        put(MessageType.T010, T010ToyBean.class);
        put(MessageType.G010, G010Bean.class);
        put(MessageType.G020, G020Bean.class);
    }};

    public static BaseResponse stringToObject(String msg) {
        JSONObject object = JSONObject.parseObject(msg);
        BaseResponse baseEntity = JSON.parseObject(msg, MSGOBJECT.get(object.get("type").toString()));
        return baseEntity;
    }

}
