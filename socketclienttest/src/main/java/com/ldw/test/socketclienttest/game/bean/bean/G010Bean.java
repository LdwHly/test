package com.ldw.test.socketclienttest.game.bean.bean;


import com.ldw.test.socketclienttest.game.bean.base.BaseResponse;
import com.ldw.test.socketclienttest.game.bean.base.MessageType;
import com.ldw.test.socketclienttest.game.io.IMessage;

public class G010Bean extends BaseResponse<G010Bean.G010Data> implements IMessage {
    public String type = MessageType.G010;

    @Override
    public String getAction() {
        return "BeginVRGame";
    }


    public static class G010Data{

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
