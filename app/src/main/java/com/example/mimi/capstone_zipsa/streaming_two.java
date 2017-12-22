package com.example.mimi.capstone_zipsa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
/**
 * Created by mimi on 2017. 12. 4..
 */

public class streaming_two extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {




        getSupportActionBar().setTitle("오늘의 집사");//글꼴 변겯
        //액션바 배경 색 변경
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        String strColor = "#afff3200";//이짓을 꼭 해야하나..
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(strColor)));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.streaming_two);

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


            case R.id.btn_image_back:

                onBackPressed();
                break;
        }
    }
}
