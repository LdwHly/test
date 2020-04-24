package com.ldw.test.socketclienttest.game.bean.base;

public class MessageType {
    /**
     * 游戏 -> remote      //游戏要获取remote 的玩具
     * {"type":"T010","data:{}}
     */
    public static final String T010 = "T010";
    /**
     * 传送给游戏的玩具信息   remote -> 游戏
     * {"type":"T011","data":{"00130404FE58":{"id":"00130404FE58","name":"Max","status":1,"battery":28,"fVersion":"102","version":2, "nickName":"fe58"}}}
     */
    public static final String T011 = "T011";
    /**
     * 开始传感器数据  游戏 -> remote
     * {"type":"G010","data":{"t":"C0AC607F9571"}}
     */
    public static final String G010 = "G010";
    /**
     * 结束传感器数据
     * {"type":"G020","data":{"t":"C0AC607F9571"}}
     */
    public static final String G020 = "G020";
    /**
     * 玩具不动的话数据是  remote -> 游戏
     * {"type":"G030","data":{"pitch":0,"roll":null,"yaw":null,"type":"atd","data":"BT0;"}}
     */
    public static final String G030 = "G030";

    /**
     * Max 通过实体按钮的操作上传的数据: 电源按钮: MirLife:1;   充气按钮: MirLife:2;
     * 我们传给玩具的数据应该是:
     * 旋转按钮：切换姿势
     */
    public static final String G040 = "G040";
    /**
     * Max 通过实体按钮的操作上传的数据: 电源按钮: MirLife:1;   充气按钮: MirLife:2;
     * 我们传给玩具的数据应该是:
     * 充气按钮：切换视角
     */
    public static final String G050 = "G050";

    /**
     * VRgame 请求socket 的 URL        ws://192.168.0.119:1025/game?type=mirlift&usercode=aaaaaaaaaaa
     * Remote 要获取这个字符串, 判断用户的业务和 usercode, 如果不是我们的 usercode, 就主动断开连接.
     * 断开连接, 就返回错误信息
     * {"type":"E010","data":{"code":"001", "message": "Authentication failed"}}
     */
    public static final String E010 = "E010";
}
