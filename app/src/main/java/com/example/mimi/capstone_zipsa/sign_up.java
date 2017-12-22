package com.example.mimi.capstone_zipsa;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by mimi on 2017. 11. 22..
 */

public class sign_up extends Activity {

    EditText idText, passwordText, passwordconfirmText, serialText, addressText;
    String final_result = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_up);

        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        passwordconfirmText = (EditText) findViewById(R.id.passwordconfirmText);
        serialText = (EditText) findViewById(R.id.serialText);


    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_signup:

                String Login_url = "http://rails-beanstalk.gb6ktuimmz.ap-northeast-2.elasticbeanstalk.com/users/sign_up";
                ContentValues cv = new ContentValues();

                Intent intent = new Intent(this, log_in.class);


                cv.put("email", idText.getText().toString());
                cv.put("encrypted_password", passwordText.getText().toString());
                cv.put("encrypted_password_confirm", passwordconfirmText.getText().toString());
                cv.put("device_uuid", FirebaseInstanceId.getInstance().getToken());
                cv.put("rasp_uuid", serialText.getText().toString());

                if (!passwordText.getText().toString().equals("") && !idText.getText().toString().equals("") && !passwordconfirmText.getText().toString().equals("")) {


                    //패스워드와 패스워드 확인이 동일한지 체크
                    if (passwordText.getText().toString().equals(passwordconfirmText.getText().toString())) {

                        sign_up.ConnectionTest ct = new sign_up.ConnectionTest(Login_url, cv);
                        ct.execute();


                        //모든 조건 충족했는데 에러나올시
                        if ("300".equals(final_result)) {
                            Toast.makeText(view.getContext(), "정확히 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(intent);
                        }

                    } else { //패드워드와 패스워드 확인이 다를시
                        Toast.makeText(view.getContext(), "동일한 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(view.getContext(), "빈칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();
                }


//                startActivity(intent);
//                finish();
                break;
            case R.id.btn_before:
                Intent intent1 = new Intent(this, log_in.class);
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
            Signup_Logic SUL = new Signup_Logic();
            result = SUL.request(url, values);


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
