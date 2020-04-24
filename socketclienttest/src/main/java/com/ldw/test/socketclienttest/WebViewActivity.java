package com.ldw.test.socketclienttest;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends Activity {
    @BindView(R.id.rl_rool)
    RelativeLayout rlRool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        try {
            try {
                webviewSetPath("com.wear.main.account.FrequentActivity");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            WebView webView = new WebView(this);
            rlRool.addView(webView);
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://www.baidu.com/");
            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress==100){
                        //TODO 在这里写加载完页面的逻辑
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void webviewSetPath(String curProcess) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (!getPackageName().equals(curProcess)) {//判断不等于默认进程名称
                WebView.setDataDirectorySuffix(curProcess);
            }
        }
    }
}
