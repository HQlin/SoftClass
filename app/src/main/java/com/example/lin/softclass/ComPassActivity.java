package com.example.lin.softclass;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by lin on 2015/10/13.
 */
public class ComPassActivity extends Activity implements SensorEventListener {
    private ImageView imageView;
    private float currentDegree = 0f;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compass);
        imageView = (ImageView) findViewById(R.id.compass_imageView);

        // ������������
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        // ע�ᴫ����(Sensor.TYPE_ORIENTATION(���򴫸���);SENSOR_DELAY_FASTEST(0�����ӳ�);
        // SENSOR_DELAY_GAME(20,000�����ӳ�)��SENSOR_DELAY_UI(60,000�����ӳ�))
        sm.registerListener(ComPassActivity.this,
                sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_FASTEST);

    }
    //�����������µ�ֵ(����ı�)
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float degree = event.values[0];

            /*
            RotateAnimation�ࣺ��ת�仯������

            ����˵��:

            fromDegrees����ת�Ŀ�ʼ�Ƕȡ�
            toDegrees����ת�Ľ����Ƕȡ�
            pivotXType��X�������ģʽ������ȡֵΪABSOLUTE��RELATIVE_TO_SELF��RELATIVE_TO_PARENT��
            pivotXValue��X���������ֵ��
            pivotYType��Y�������ģʽ������ȡֵΪABSOLUTE��RELATIVE_TO_SELF��RELATIVE_TO_PARENT��
            pivotYValue��Y���������ֵ
            */
            RotateAnimation ra = new RotateAnimation(currentDegree, -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            //��ת���̳���ʱ��
            ra.setDuration(200);
            //����ͼƬʹ����ת����
            imageView.startAnimation(ra);

            currentDegree = -degree;
        }
    }
    //���������ȵĸı�
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    //���û���
    //����Activity���onKeyDown(int keyCoder,KeyEvent event)����
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(ComPassActivity.this,MainActivity.class);
            startActivity(intent);
            finish();//�����˳�����
            return true;
        }
        return false;
    }
}
