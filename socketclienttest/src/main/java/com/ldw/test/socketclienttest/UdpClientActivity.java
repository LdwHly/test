package com.ldw.test.socketclienttest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UdpClientActivity extends Activity {
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.btn_send_udp_message)
    Button btnSendUdpMessage;
    @BindView(R.id.get)
    Button get;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.tv_text_client)
    TextView tvTextClient;
    @BindView(R.id.tv_connect)
    Button tvConnect;
    private DatagramSocket serverSocket = null;
    private DatagramSocket clientSocket = null;
    private InetAddress serverAddress = null;
    private final String TAG = UdpClientActivity.class.getSimpleName();
    private int serverPort = 6008;
    private int id = Cons.ID_A;

    private InetAddress clientAddress = null;
    private int clientPort = 6007;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udp_client);
        ButterKnife.bind(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //自己的发送端口
                    serverSocket = new DatagramSocket(3001);  //①
                    //对方的ip http://111.229.61.204/
//                    serverAddress = InetAddress.getByName("47.94.92.207");  //②
                    serverAddress = InetAddress.getByName("111.229.61.204");  //②
//                    serverAddress = InetAddress.getByName("192.168.0.232");  //②
                    while (true) {
                        //接收数据
                        byte[] receiveData = new byte[1024];
                        DatagramPacket datagramPacket = new DatagramPacket(receiveData, receiveData.length);
                        serverSocket.receive(datagramPacket);
                        //解析数据
                        byte[] datas = datagramPacket.getData();
                        final String json = new String(Utils.subBytes(datas, 0, datagramPacket.getLength()));
                        LogLog.d(TAG, json);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvText.setText(json);
                            }
                        });
                        MessageBean messageBean = null;
                        try {
                            messageBean = JSON.parseObject(json, MessageBean.class);
                            switch (messageBean.getProtocol()) {
                                case Cons.PROTOCOL_LOGIN_ACK:

                                    break;
                                case Cons.PROTOCOL_GET_ONLINE_ACK:
                                    HashMap<String, String> map = JSONObject.parseObject(messageBean.getData(), HashMap.class);
                                    Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                                    while (it.hasNext()) {
                                        Map.Entry<String, String> entry = it.next();
                                        String key = entry.getKey();
                                        String value = entry.getValue();
                                        if (!key.equals(id + "")) {
                                            String[] data = value.split(":");
                                            clientAddress = InetAddress.getByName(data[0]);  //②
                                            clientPort = Integer.parseInt(data[1]);
                                        } else {
                                            String[] data = value.split(":");
                                        }
                                    }
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void sendMessage(final MessageBean messageBean) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte data[] = JSONObject.toJSON(messageBean).toString().getBytes();
                    DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);   //③
                    serverSocket.send(packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    @OnClick({R.id.btn_send_udp_message, R.id.get, R.id.tv_connect})
    public void onViewClicked(View view) {
        MessageBean messageBean = null;
        id = Cons.ID_B;
        if (etAccount.getText().toString().equals("A")) {
            id = Cons.ID_A;
        }
        switch (view.getId()) {
            case R.id.btn_send_udp_message:
                messageBean = new MessageBean(id, Cons.PROTOCOL_LOGIN, "login");
                sendMessage(messageBean);
                break;
            case R.id.get:
                messageBean = new MessageBean(id, Cons.PROTOCOL_GET_ONLINE, "login");
                sendMessage(messageBean);
                break;
            case R.id.tv_connect:
                tvTextClient.setText(clientAddress.getHostAddress() + " " + clientPort);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte data[] = " from Client".getBytes();
                            clientPort = (id == Cons.ID_A ? 4002 : 4001);
                            DatagramPacket packet = new DatagramPacket(data, data.length, clientAddress, clientPort);   //③
                            LogLog.d(TAG, "发送数据：" + clientAddress.getHostAddress() + " " + clientPort);
                            if (clientSocket == null) {
                                clientSocket = new DatagramSocket(id == Cons.ID_A ? 4001 : 4002);
                            }
                            clientSocket.send(packet);

//                            serverSocket.send(packet);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
}
