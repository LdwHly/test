package com.ldw.test.socketclienttest.game.bean.bean;


import com.ldw.test.socketclienttest.game.bean.base.BaseResponse;
import com.ldw.test.socketclienttest.game.bean.base.MessageType;
import com.ldw.test.socketclienttest.game.io.IMessage;

public class G020Bean extends BaseResponse<G020Bean.G020Data> implements IMessage {
    public String type = MessageType.G020;

    @Override
    public String getAction() {
        return "EndVRGame";
    }


    public static class G020Data{

        /**
         * t : C0AC607F9571
         */

        private String t;

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }
    }
}
