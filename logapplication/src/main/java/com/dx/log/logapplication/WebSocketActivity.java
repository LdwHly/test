package com.dx.log.logapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dx.log.logapplication.game.ISocketClient;
import com.dx.log.logapplication.game.SocketClintPoxy;
import com.dx.log.logapplication.game.TWSocketClient;
import com.dx.log.logapplication.service.LogBean;
import com.dx.log.logapplication.service.WindowUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebSocketActivity extends Activity {

    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_port)
    EditText etPort;
    @BindView(R.id.connect)
    TextView connect;
    private static final String TAG = "SocketActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);
        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect(view);
            }
        });
        etAddress.setText(NetWorkUtils.getLocalIpAddress(this));
    }

    public void connect(View view) {
        int port = Integer.parseInt(etPort.getText().toString());
        String url = etAddress.getText().toString();
        SocketClintPoxy.getInstance().connect(url, port, "/log");

    }



    @OnClick({R.id.tv_show_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_show_info:
                WindowUtils.showPopupWindow(getApplicationContext());
                break;
        }
    }


}
