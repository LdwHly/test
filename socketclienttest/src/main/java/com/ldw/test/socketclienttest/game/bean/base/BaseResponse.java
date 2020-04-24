package com.ldw.test.socketclienttest.game.bean.base;


public class BaseResponse<T>   {
    public String type;
    public T data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
