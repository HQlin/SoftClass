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
    //webview中调用toast原生组件
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        if(toast.equals("Hello,Android!")){
            Intent intent = new Intent(mContext,ComPassActivity.class);
            mContext.startActivity(intent);
        }
    }

    @JavascriptInterface
    //webview中求和
    public int sum(int a,int b){
        return a+b;
    }

    @JavascriptInterface
    //以json实现webview与js之间的数据交互
    public String jsontohtml(){
        JSONObject map;
        JSONArray array = new JSONArray();
        try {
            map = new JSONObject();
            map.put("name","aaron");
            map.put("age", 25);
            map.put("address", "中国上海");
            array.put(map);

            map = new JSONObject();
            map.put("name","jacky");
            map.put("age", 22);
            map.put("address", "中国北京");
            array.put(map);

            map = new JSONObject();
            map.put("name","vans");
            map.put("age", 26);
            map.put("address", "中国深圳");
            map.put("phone","13888888888");
            array.put(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }
}

