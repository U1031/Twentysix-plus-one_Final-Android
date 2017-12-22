package com.example.mimi.capstone_zipsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by mimi on 2017. 11. 22..
 */

public class set_up extends Activity {
    EditText addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_up);


        addressText = (EditText) findViewById(R.id.addressText);

    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register:
                String address = addressText.getText().toString();
                break;

            case R.id.btn_image_back:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                finish();
                break;

        }
    }

}
