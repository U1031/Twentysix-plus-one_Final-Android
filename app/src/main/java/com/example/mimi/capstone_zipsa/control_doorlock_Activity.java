package com.example.mimi.capstone_zipsa;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mimi on 2017. 11. 20..
 */

public class control_doorlock_Activity extends AppCompatActivity {
    private ImageView Change_image;
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
        setContentView(R.layout.control_doorlock);
        Intent intent = new Intent(getApplicationContext(), socket_service.class);
        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        Change_image = (ImageView) findViewById(R.id.image_state);

        getSupportActionBar().setTitle("오늘의 집사");//글꼴 변겯
        //액션바 배경 색 변경
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        String strColor = "#afff3200";//이짓을 꼭 해야하나..
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(strColor)));

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                Change_image.setImageResource(R.drawable.close);
                ms.sendSignal('o');
                break;

            case R.id.btn_close:
                ms.sendSignal('o');
                break;


            case R.id.btn_image_back:
                unbindService(conn);
                onBackPressed();
                break;
        }
    }
}


