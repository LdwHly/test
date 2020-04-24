package com.ldw.test.socketclienttest

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_video_play.*

class VideoPlayActivity : FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        tv_start.setOnClickListener(View.OnClickListener {  enterPictureInPictureMode() })
    }
}