package com.ldw.test.socketclienttest;

/**
 * Time: 2019/7/11
 * Author: xuchao
 * Description: 描写描述
 */
public class MessageBean {

    public MessageBean() {
    }

    public MessageBean(int id, int protocol, String data) {
        this.id = id;
        this.protocol = protocol;
        this.data = data;
    }

    private int id;

    private int protocol;

    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
