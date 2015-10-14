package com.example.lin.softclass;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lin on 2015/10/13.
 */
public class AndroidToastForJs {

    private Context mContext;

    public AndroidToastForJs(Context context){
        this.mContext = context;
    }

    @JavascriptInterface
    //webview�е���toastԭ�����
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        if(toast.equals("Hello,Android!")){
            Intent intent = new Intent(mContext,ComPassActivity.class);
            mContext.startActivity(intent);
        }
    }

    @JavascriptInterface
    //webview�����
    public int sum(int a,int b){
        return a+b;
    }

    @JavascriptInterface
    //��jsonʵ��webview��js֮������ݽ���
    public String jsontohtml(){
        JSONObject map;
        JSONArray array = new JSONArray();
        try {
            map = new JSONObject();
            map.put("name","aaron");
            map.put("age", 25);
            map.put("address", "�й��Ϻ�");
            array.put(map);

            map = new JSONObject();
            map.put("name","jacky");
            map.put("age", 22);
            map.put("address", "�й�����");
            array.put(map);

            map = new JSONObject();
            map.put("name","vans");
            map.put("age", 26);
            map.put("address", "�й�����");
            map.put("phone","13888888888");
            array.put(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }
}

