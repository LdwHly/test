package com.ldw.test.socketclienttest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_socket_test -> startActivity(Intent(this, WebSocketActivity::class.java))
                R.id.tv_video_play -> startActivity(Intent(this, VideoPlayActivity::class.java))
                R.id.tv_udp_socket -> startActivity(Intent(this, UdpClientActivity::class.java))
                R.id.tv_event_trans -> startActivity(Intent(this, EventTransActivity::class.java))
                R.id.tv_webview -> startActivity(Intent(this, WebViewActivity::class.java))
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_socket_test.setOnClickListener(this)
        tv_video_play.setOnClickListener(this)
        tv_udp_socket.setOnClickListener(this)
        tv_event_trans.setOnClickListener(this)
        tv_webview.setOnClickListener(this)
        val webView = WebView(this)
    }
}
