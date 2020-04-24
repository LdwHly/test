package com.ldw.test.socketclienttest;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.dx.log.logapplication.service.ILogRemoteAidlInterface;

public class MyApplication extends Application {

    private static ILogRemoteAidlInterface myBinder;//定义AIDL
    public static ILogRemoteAidlInterface getRemoteBinder() {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myBinder = ILogRemoteAidlInterface.Stub.asInterface(iBinder);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                myBinder = null;
            }
        };
        Intent intent = new Intent();
        intent.setAction("com.dx.log.logapplication.service.MyRemoteService");
        intent.setPackage("com.dx.log.logapplication");
        bindService(intent, conn, BIND_AUTO_CREATE);//开启Service
    }
}
