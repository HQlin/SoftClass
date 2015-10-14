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

        //����ҳ��
        webView.loadUrl("file:///android_asset/jsonData.html");
        //webview��Ӷ�js��֧��
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);//֧��js
        //���Ӷ����ĵ�֧��
        setting.setDefaultTextEncodingName("GBK");//�����ַ�����
        //����֧������
        setting.setBuiltInZoomControls(true);
        //��ʹ��������ռλ
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //ȡ��Vertical ScrollBar��ʾ
        webView.setVerticalScrollBarEnabled(false);
        //ȡ��Horizontal ScrollBar��ʾ
        webView.setHorizontalScrollBarEnabled(false);
        //Webview�ṩ�Ĵ���js�ķ���
        webView.addJavascriptInterface(new AndroidToastForJs(this), "JavaScriptInterface");

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {// ������ȸı������
                if (progress == 100) {
                    handler.sendEmptyMessage(1);// ���ȫ������,���ؽ��ȶԻ���
                }
                super.onProgressChanged(view, progress);
            }
        });

        //���ȶԻ�������
        pbarDialog = new ProgressDialog(this);
        pbarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pbarDialog.setMessage("Loading...");
        pbarDialog.setCancelable(false);

    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {// ����һ��Handler�����ڴ��������߳���UI��ͨѶ
            if (!Thread.currentThread().isInterrupted()){
                switch (msg.what) {
                    case 0:
                        pbarDialog.show();// ��ʾ���ȶԻ���
                        break;
                    case 1:
                        pbarDialog.hide();// ���ؽ��ȶԻ��򣬲���ʹ��dismiss()��cancel(),�����ٴε���show()ʱ����ʾ�ĶԻ���СԲȦ���ᶯ��
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
    //���û���
    //����Activity���onKeyDown(int keyCoder,KeyEvent event)����
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()��ʾ����WebView����һҳ��
            return true;
        }
        finish();//�����˳�����
        return false;
    }

}
