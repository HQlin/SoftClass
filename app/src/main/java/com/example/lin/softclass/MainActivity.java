package com.example.lin.softclass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class MainActivity extends Activity {

    private WebView webView;
    private ProgressDialog pbarDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);

        //载入页面
        webView.loadUrl("file:///android_asset/jsonData.html");
        //webview添加对js的支持
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        //增加对中文的支持
        setting.setDefaultTextEncodingName("GBK");//设置字符编码
        //设置支持缩放
        setting.setBuiltInZoomControls(true);
        //可使滚动条不占位
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //取消Vertical ScrollBar显示
        webView.setVerticalScrollBarEnabled(false);
        //取消Horizontal ScrollBar显示
        webView.setHorizontalScrollBarEnabled(false);
        //Webview提供的传入js的方法
        webView.addJavascriptInterface(new AndroidToastForJs(this), "JavaScriptInterface");

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
                if (progress == 100) {
                    handler.sendEmptyMessage(1);// 如果全部载入,隐藏进度对话框
                }
                super.onProgressChanged(view, progress);
            }
        });

        //进度对话框设置
        pbarDialog = new ProgressDialog(this);
        pbarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pbarDialog.setMessage("Loading...");
        pbarDialog.setCancelable(false);

    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted()){
                switch (msg.what) {
                    case 0:
                        pbarDialog.show();// 显示进度对话框
                        break;
                    case 1:
                        pbarDialog.hide();// 隐藏进度对话框，不可使用dismiss()、cancel(),否则再次调用show()时，显示的对话框小圆圈不会动。
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();//结束退出程序
        return false;
    }

}
