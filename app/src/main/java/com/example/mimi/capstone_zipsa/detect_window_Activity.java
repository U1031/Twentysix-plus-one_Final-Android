package com.example.mimi.capstone_zipsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by mimi on 2017. 11. 23..
 */

public class detect_window_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_window);


    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_window_picture:
                Toast.makeText(this, "창문 괴한 탐지 시 카메라 확인(사진)인데 아직 없음", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_window_sms:
                Toast.makeText(this, "창문 괴한 탐지 시 sms전송 인데 아직 없음", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_window_ignore:
                Toast.makeText(this, "침입 괴한 탐지 시 해당 신호를 무시하는 건데 아직 없음", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_image_back:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);//꼭 이 방법만 있지는 않을텐데
                finish();
                break;


        }
    }


}
