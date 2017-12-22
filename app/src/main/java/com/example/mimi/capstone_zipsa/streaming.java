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
import android.webkit.WebView;

import com.example.mimi.capstone_zipsa.socket_service.MyBinder;

/**
 * Created by mimi on 2017. 11. 22..
 */


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


public class streaming extends AppCompatActivity {

    socket_service ms; // 서비스 객체
    boolean isService = false; // 서비스 중인 확인용
    Intent intent; // bind 하기위한 객체

    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinder mb = (MyBinder) service;
            ms = mb.getService();
            isService = true;
        }

        public void onServiceDisconnected(ComponentName name) {
            isService = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setTitle("오늘의 집사");//글꼴 변겯
        //액션바 배경 색 변경
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        String strColor = "#afff3200";//이짓을 꼭 해야하나..
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(strColor)));


        intent = new Intent(getApplicationContext(), socket_service.class);
        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.streaming);


        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setPadding(0, 0, 0, 0);
        //webView.setInitialScale(100);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        String url = "http://192.168.43.88:8080/javascript_simple.html";
        webView.loadUrl(url);


    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_streaming_window:
                ms.sendSignal('w'); // 창문으로 카메라 화전
                break;

            case R.id.btn_streaming_door:
                ms.sendSignal('d'); // 문쪽으로 카메라 회전
                break;

            case R.id.btn_image_back:
                unbindService(conn);
                onBackPressed();
                break;
        }
    }
}

