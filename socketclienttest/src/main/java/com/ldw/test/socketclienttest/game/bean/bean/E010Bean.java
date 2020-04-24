package com.ldw.test.socketclienttest.game.bean.bean;


import com.ldw.test.socketclienttest.game.bean.base.BaseResponse;
import com.ldw.test.socketclienttest.game.bean.base.MessageType;

public class E010Bean extends BaseResponse<E010Bean.E010Data> {

    public String type = MessageType.E010;


    public static class E010Data{

        /**
         * code : 001
         * message : Authentication failed
         */

        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
