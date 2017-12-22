package com.example.mimi.capstone_zipsa;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;
import android.content.Context;

/**
 * Created by mimi on 2017. 11. 20..
 */

public class detect_intrusion_Activity extends Activity {

    socket_service ms; // 서비스 객체
    boolean isService = false; // 서비스 중인 확인용

    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            socket_service.MyBinder mb = (socket_service.MyBinder) service;
            ms = mb.getService();
            isService = true;
        }

        public void onServiceDisconnected(ComponentName name) {
            isService = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detect_intrusion);
        Intent intent = new Intent(getApplicationContext(), socket_service.class);
        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Intent intent1 = getIntent();
       String data= intent1.getStringExtra("address");

    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_doorlock_camera:
                ms.sendSignal('c');
                Intent streaming_real = new Intent(this, streaming_two.class);
                startActivity(streaming_real);
                break;

            case R.id.btn_doorlock_sms:
                Messenger test = new Messenger(getApplicationContext());
                test.sendMessageTo("01074462805", "신고 성공!!!!");

                break;
            case R.id.btn_doorlock_on:
                ms.sendSignal('x');
                break;

            case R.id.btn_doorlock_off:
                ms.sendSignal('y');
                break;
            case R.id.btn_image_back:
                unbindService(conn);
                onBackPressed();
                break;
        }
    }
}

class Messenger {
    private Context mContext;

    public Messenger(Context mContext) {
        this.mContext = mContext;
    }//Messenger 클래스의 생성자 현재 어플의 정보를 얻어옴

    public void sendMessageTo(String phoneNum, String message) {
        SmsManager smsManager = SmsManager.getDefault();// SmsManager 객체를 생성
        smsManager.sendTextMessage(phoneNum, null, message, null, null);
        Toast.makeText(mContext, "신고 성공", Toast.LENGTH_SHORT).show();
    }
}