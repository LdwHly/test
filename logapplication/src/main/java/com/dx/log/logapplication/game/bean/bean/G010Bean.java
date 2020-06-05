package com.dx.log.logapplication.game.bean.bean;


import com.dx.log.logapplication.game.bean.base.BaseResponse;
import com.dx.log.logapplication.game.bean.base.MessageType;
import com.dx.log.logapplication.game.io.IMessage;

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
