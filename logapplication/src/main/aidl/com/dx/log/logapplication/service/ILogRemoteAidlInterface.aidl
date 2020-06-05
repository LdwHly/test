// ILogRemoteAidlInterface.aidl
package com.dx.log.logapplication.service;

// Declare any non-default types here with import statements

interface ILogRemoteAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
        void println(CharSequence tag,CharSequence url,CharSequence parms,CharSequence response);
        void d(CharSequence tag,CharSequence msg);
        void printlnTag(CharSequence packageName,CharSequence tag,CharSequence childTag,CharSequence msg);
        void postWsUrl(String url, int port, String path);
        void showWindow();
        void hideWindow();
}
