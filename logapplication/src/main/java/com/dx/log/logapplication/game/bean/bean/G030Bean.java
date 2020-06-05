package com.dx.log.logapplication.game.bean.bean;


import com.dx.log.logapplication.game.bean.base.BaseResponse;
import com.dx.log.logapplication.game.bean.base.MessageType;

public class G030Bean extends BaseResponse<G030Bean.G030Data> {
    public String type = MessageType.G030;


    public static class G030Data {

        /**
         * pitch : 0
         * roll : null
         * yaw : null
         * type : atd
         * data : BT0;
         */

        private int pitch;
        private Object roll;
        private Object yaw;
        private String type = "atd";
        private String data;

        public int getPitch() {
            return pitch;
        }

        public void setPitch(int pitch) {
            this.pitch = pitch;
        }

        public Object getRoll() {
            return roll;
        }

        public void setRoll(Object roll) {
            this.roll = roll;
        }

        public Object getYaw() {
            return yaw;
        }

        public void setYaw(Object yaw) {
            this.yaw = yaw;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
