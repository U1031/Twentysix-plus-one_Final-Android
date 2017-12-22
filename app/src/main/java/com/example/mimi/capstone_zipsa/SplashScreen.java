package com.example.mimi.capstone_zipsa;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Window;

/**
 * Created by mimi on 2017. 11. 16..
 */

public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, log_in.class));
        finish();
    }


}

