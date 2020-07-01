package com.ldw.test.socketclienttest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UdpClientActivity extends Activity {
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_friend)
    TextView tvFriend;
    @BindView(R.id.ll_list)
    ListView llList;
    @BindView(R.id.tv_i)
    TextView tvI;

    private DatagramSocket serverSocket = null;
    private InetAddress serverAddress1 = null;
    private int serverPort1 = 6007;
    private InetAddress serverAddress2 = null;
    private int serverPort2 = 443;


    private DatagramSocket clientSocket = null;
    private InetAddress clientAddress = null;

    private final String TAG = UdpClientActivity.class.getSimpleName();
    FriendAdapter adapter;
    List<FriendBean> list = new ArrayList<>();

    String friendIp;
    int testFriendPort;
    int friendPort = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udp_client);
        ButterKnife.bind(this);

        adapter = new FriendAdapter(list, this, R.layout.item_friends);
        llList.setAdapter(adapter);
        llList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                friendIp = list.get(position).ip;
                testFriendPort = list.get(position).port;
                showText();
            }
        });
        try {
            serverSocket = new DatagramSocket(3002);  //①
            serverAddress2 = InetAddress.getByName("120.53.118.119");  //②
            serverAddress1 = InetAddress.getByName("47.94.92.207");  //②http://120.53.118.119:8080/demo_war/
        } catch (Exception e) {
            e.printStackTrace();
        }
        ThreadPoolFactory.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //自己的发送端口

                    while (true) {
                        //接收数据
                        byte[] receiveData = new byte[1024];
                        DatagramPacket datagramPacket = new DatagramPacket(receiveData, receiveData.length);
                        serverSocket.receive(datagramPacket);
                        //解析数据
                        DataInputStream istream = new DataInputStream(new ByteArrayInputStream(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength()));
                        String msg = istream.readUTF();
                        String ip = ((InetSocketAddress) datagramPacket.getSocketAddress()).getAddress().getHostAddress();
                        int port = datagramPacket.getPort();
                        handlerData(ip + "port", msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void showText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvFriend.setText("对方ip " + friendIp + ":" + testFriendPort);
            }
        });
    }

    private void handlerData(String serverAddress, String json) {
        LogLog.d(TAG, serverAddress + "返回：" + json);
        ResponseBean responseBean = null;
        try {
            responseBean = JSON.parseObject(json, ResponseBean.class);
            switch (responseBean.type) {
                case Cons.LOGIN:
                    final ResponseBean finalResponseBean = responseBean;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvI.setText("我的ip " + finalResponseBean.ip + "：" + finalResponseBean.port);
                        }
                    });
                    break;
                case Cons.GETPORTFROMIP:
                    break;
                case Cons.NOINFO:
                    break;
                case Cons.ERROR:
                    break;
                case Cons.GETALLUSER:
                    final String str = responseBean.data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(str)) {
                                return;
                            }
                            HashMap<String, Integer> map = JSON.parseObject(str, HashMap.class);
                            list.clear();
                            for (String key : map.keySet()) {
                                FriendBean friendBean = new FriendBean();
                                friendBean.ip = key;
                                friendBean.port = map.get(key);
                                list.add(friendBean);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(
                             final InetAddress serverAddress, final RequestBean messageBean, final int port) {
        ThreadPoolFactory.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    byte data[] = JSONObject.toJSON(messageBean).toString().getBytes();
                    DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);   //③
                    serverSocket.send(packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @OnClick({R.id.btn_login, R.id.btn_server_1, R.id.btn_server_2, R.id.btn_connect, R.id.btn_test, R.id.btn_clear})
    public void onViewClicked(View view) {
        RequestBean requestBean = new RequestBean();
        switch (view.getId()) {
            case R.id.btn_login:
                requestBean.type = Cons.LOGIN;
                sendMessage(serverAddress1, requestBean, serverPort1);
                sendMessage(serverAddress2, requestBean, serverPort2);
                break;
            case R.id.btn_server_1:
                requestBean.type = Cons.GETALLUSER;
                sendMessage(serverAddress1, requestBean, serverPort1);
                break;
            case R.id.btn_server_2:
                requestBean.type = Cons.GETALLUSER;
                sendMessage(serverAddress2, requestBean, serverPort2);
                break;
            case R.id.btn_connect:
                ThreadPoolFactory.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            clientAddress = InetAddress.getByName(friendIp);  //②
                            if (clientSocket == null) {
                                clientSocket = new DatagramSocket(3004);
                                while (true) {
                                    //接收数据
                                    byte[] receiveData = new byte[1024];
                                    DatagramPacket datagramPacket = new DatagramPacket(receiveData, receiveData.length);
                                    clientSocket.receive(datagramPacket);
                                    //解析数据
                                    byte[] datas = datagramPacket.getData();
                                    final String json = new String(Utils.subBytes(datas, 0, datagramPacket.getLength()), "utf-8");
                                    int port = datagramPacket.getPort();
                                    friendPort = port;
                                    LogLog.d(TAG, "收到数据啦：" + json);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvText.setText("收到数据啦：" + json);
                                        }
                                    });
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_test:
                ThreadPoolFactory.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (friendPort == 0) {
                                testFriendPort = testFriendPort + 1;
                            } else {
                                testFriendPort = friendPort;
                            }
                            byte data[] = " from Client".getBytes();
                            DatagramPacket packet = new DatagramPacket(data, data.length, clientAddress, testFriendPort);   //③
                            showText();
                            clientSocket.send(packet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;
            case R.id.btn_clear:
                requestBean.type = Cons.CLEAR;
                sendMessage(serverAddress1, requestBean, serverPort1);
                sendMessage(serverAddress2, requestBean, serverPort2);
                break;
        }
    }
}
