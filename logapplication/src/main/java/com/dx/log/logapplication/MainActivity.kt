package com.dx.log.logapplication

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.view.View

import com.dx.log.logapplication.service.WindowUtils

class MainActivity : Activity() {
    internal var handler = Handler()
    private var bookbinders = Intent()
    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (WindowUtils.startGetPermission(this)) {
        }
        findViewById<View>(R.id.tv_show).setOnClickListener {
            WindowUtils.showPopupWindow(applicationContext)
            startActivity(Intent(this, WebSocketActivity::class.java))
        }

    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
