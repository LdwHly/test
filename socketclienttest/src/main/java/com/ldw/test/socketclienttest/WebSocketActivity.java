package com.ldw.test.socketclienttest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ldw.test.socketclienttest.game.ISocketClient;
import com.ldw.test.socketclienttest.game.SocketClintPoxy;
import com.ldw.test.socketclienttest.game.TWSocketClient;
import com.ldw.test.socketclienttest.game.bean.base.MessageType;
import com.ldw.test.socketclienttest.game.bean.bean.G010Bean;
import com.ldw.test.socketclienttest.game.bean.bean.G020Bean;
import com.ldw.test.socketclienttest.game.bean.bean.T010ToyBean;
import com.ldw.test.socketclienttest.game.bean.bean.T011ToyBean;
import com.ldw.test.socketclienttest.game.bean.bean.ToyBean;
import com.ldw.test.socketclienttest.game.io.IMessage;
import com.ldw.test.socketclienttest.game.io.SocketIoClientInstance;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebSocketActivity extends Activity implements ISocketClient.StringDataCallback {

    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_port)
    EditText etPort;
    @BindView(R.id.tv_receive_msg)
    TextView tvReceiveMsg;
    @BindView(R.id.tv_dis_connect)
    TextView tvDisConnect;
    @BindView(R.id.et_count)
    EditText etCount;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.connect)
    TextView connect;
    @BindView(R.id.lv_toys)
    ListView lvToys;
    @BindView(R.id.tv_qiehuan)
    TextView tvQiehuan;
    private EditText mEditText;
    private TextView mTextView;
    private static final String TAG = "SocketActivity";
    SocketClintPoxy mSocketClintPoxy;
    TWSocketClient tWSocketClient;
    List<ToyBean> list = new ArrayList<>();
    ToysAdapter adapter;
    ToyBean selectToy;
    boolean isLocal = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);
        mEditText = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send(view);
            }
        });
        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect(view);
            }
        });
        tvDisConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T010ToyBean baseBean = new T010ToyBean();
                sendAllMsg(baseBean);
            }
        });
        mSocketClintPoxy = SocketClintPoxy.getInstance();
        tWSocketClient = new TWSocketClient();
        mSocketClintPoxy.setTarget(tWSocketClient);
        adapter = new ToysAdapter(list, this, R.layout.item_toy);
        lvToys.setAdapter(adapter);
        lvToys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectToy = list.get(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void connect(View view) {
//        tvDisConnect.performClick();
//        String url = "ws://192.168.0.61:1025/game?type=mirlift&usercode=ababe16bc4956d176006fea1abe32048";
        if (isLocal) {
            int port = Integer.parseInt(etPort.getText().toString());
            String url = "ws://" + etAddress.getText().toString() + ":" + port + "/game?type=mirlift&usercode=" + etCount.getText().toString();
            mSocketClintPoxy.connect(url, port, etCount.getText().toString());
            mSocketClintPoxy.setStringDataCallback(this);
        } else {
            SocketIoClientInstance.getInstance().connect();
            SocketIoClientInstance.getInstance().setDataCallback(this);
        }
    }


    public void send(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String sendMsg = mEditText.getText().toString();
                mSocketClintPoxy.sendMsg(sendMsg);
            }
        }).start();
    }


    @Override
    public void receMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvReceiveMsg.setText(msg);
            }
        });
        try {
            LogLog.d("SocketActivity", "收到：" + msg);
            JSONObject object = JSONObject.parseObject(msg);
            if (MessageType.T011.equals(object.getString("type"))) {
                T011ToyBean t011ToyBean = JSON.parseObject(msg, T011ToyBean.class);
                Iterator<Map.Entry<String, ToyBean>> it = t011ToyBean.data.entrySet().iterator();
                final ArrayList<ToyBean> tempList = new ArrayList<>();
                while (it.hasNext()) {
                    Map.Entry<String, ToyBean> entry = it.next();
                    tempList.add(entry.getValue());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        list.addAll(tempList);
                        adapter.notifyDataSetChanged();
                    }
                });
            } else if (MessageType.G020.equals(object.getString("type"))) {

            } else if (MessageType.G030.equals(object.getString("type"))) {

            } else if (MessageType.G040.equals(object.getString("type"))) {

            } else if (MessageType.G050.equals(object.getString("type"))) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.tv_t_websocket, R.id.tv_t_socket, R.id.tv_c_socket, R.id.tv_begin, R.id.tv_end, R.id.tv_show_info, R.id.tv_qiehuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_t_websocket:
                mSocketClintPoxy.setTarget(tWSocketClient);
                break;
            case R.id.tv_t_socket:

                break;
            case R.id.tv_c_socket:

                break;
            case R.id.tv_begin:
                if (selectToy == null) {
                    Toast.makeText(this, "玩具为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                G010Bean g010Bean = new G010Bean();
                G010Bean.G010Data g010Data = new G010Bean.G010Data();
                g010Data.setT(selectToy.getId());
                g010Bean.data = g010Data;
                sendAllMsg(g010Bean);
                break;
            case R.id.tv_end:
                G020Bean g020Bean = new G020Bean();
                G020Bean.G020Data g0120Data = new G020Bean.G020Data();
                if (selectToy != null) {
                    g0120Data.setT(selectToy.getId());
                }
                g020Bean.data = g0120Data;
                sendAllMsg(g020Bean);
                break;
            case R.id.tv_show_info:
                try {
                    MyApplication.getRemoteBinder().showWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_qiehuan:
                isLocal = !isLocal;
                if (isLocal) {
                    tvQiehuan.setText("切换外网");
                } else {
                    tvQiehuan.setText("切换内网");
                }
                break;
        }
    }

    private void sendAllMsg(IMessage javaBean) {
        if (isLocal) {
            Map<String, Object> map = getMap(javaBean);
            String msg = JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
            Log.d("SocketActivity", msg);
            mSocketClintPoxy.sendMsg(msg);
        } else {
            SocketIoClientInstance.getInstance().sendMsg(javaBean);
        }
    }

    /**
     * 反射获取将javaBean转换成TreeMap，而TreeMap默认排序升序
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    private final Map<String, Object> getMap(Object javaBean) {
        Map<String, Object> map = new TreeMap<>();
        Field[] fields = javaBean.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {
            String firstLetter = fields[i].getName();
            Object value = null;
            try {
                value = fields[i].get(javaBean);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (value != null) {
                map.put(firstLetter, value);
            }
        }

        return map;
    }


}
