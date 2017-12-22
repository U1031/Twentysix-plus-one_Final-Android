package com.example.mimi.capstone_zipsa;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mimi on 2017. 11. 22..
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


public class log_in extends Activity {

    EditText idText, passwordText;
    String final_result = "";
    private TextView token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.login);

        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        String Token = FirebaseInstanceId.getInstance().getToken();

    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_login:

                String Login_url = "http://rails-beanstalk.gb6ktuimmz.ap-northeast-2.elasticbeanstalk.com/users/login";
                ContentValues cv = new ContentValues();
                cv.put("email", idText.getText().toString());
                cv.put("password", passwordText.getText().toString());

                Log.v("email and password ", idText.getText().toString() + ", " + passwordText.getText().toString());

                Intent intent = new Intent(this, MainActivity.class);

                if (!idText.getText().toString().equals("") && !passwordText.getText().toString().equals("")) {


                    log_in.ConnectionTest ct = new log_in.ConnectionTest(Login_url, cv);
                    ct.execute();


                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    Log.v("final_result is ", final_result);
                    if ("300".equals(final_result)) {

                        Toast.makeText(view.getContext(), "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();

                    } else {
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(view.getContext(), "빈칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();

                }


//                startActivity(intent);
//                finish();
                break;
            case R.id.btn_signup:
                Intent intent1 = new Intent(this, sign_up.class);
                startActivity(intent1);//꼭 이 방법만 있지는 않을텐데
                finish();
                break;

        }
    }

    private class ConnectionTest extends AsyncTask<Void, Void, String> {


        private String url;
        private ContentValues values;

        public ConnectionTest(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result;
            Login_Logic LOL = new Login_Logic();
            result = LOL.request(url, values);


            Log.v("결과는? ", result);
            final_result = result;
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Log.v("onPostExecute ", s);
//            txtResult.setText(s);
        }
    }
}
