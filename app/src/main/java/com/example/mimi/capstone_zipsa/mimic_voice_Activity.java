package com.example.mimi.capstone_zipsa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;


import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

//import com.example.guest.today_dobi.utils.AudioWriterPCM;
//import com.naver.speech.clientapi.SpeechRecognitionResult;

import com.example.mimi.capstone_zipsa.utils.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by mimi on 2017. 11. 20..
 */

public class mimic_voice_Activity extends AppCompatActivity {


    private NaverTTSTask mNaverTTSTask;
    String[] mTextString;


    private static final String TAG = mimic_voice_Activity.class.getSimpleName();
    private static final String CLIENT_ID = "AuWjQtbxwiihw6GZ9Xb0"; // "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요.
    private RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;
    private TextView txtResult;
    private Button btnStart;
    private String mResult;
    private AudioWriterPCM writer;

    private String final_result = " ";


    public void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
//                txtResult.setText("Connected");
                Log.v("Announce ", "Connented");
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                // Extract obj property typed with String.
                mResult = (String) (msg.obj);
//                txtResult.setText(mResult);
                Log.v("mResult ", mResult);
                break;

            case R.id.finalResult:
                // Extract obj property typed with String array.
                // The first element is recognition result for speech.
                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
                List<String> results = speechRecognitionResult.getResults();
                StringBuilder strBuf = new StringBuilder();
                for (String result : results) {
                    strBuf.append(result);
                    strBuf.append("\n");
                }
                Log.v("개굴", results.get(0));
                final_result = results.get(0);
                mResult = strBuf.toString();
//                txtResult.setText(mResult);
                Log.v("mResult ", mResult);

//                mResult = (String) (msg.obj);
//                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
//                mResult = speechRecognitionResult.getResults();
//                txtResult.setText(mResult);
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
//                txtResult.setText(mResult);
                Log.v("mResult ", mResult);
                btnStart.setText(R.string.str_start);
                btnStart.setEnabled(true);
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }

                btnStart.setText(R.string.str_start);
                btnStart.setEnabled(true);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mimic_voice);

        btnStart = (Button) findViewById(R.id.btn_reset);

        handler = new RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(this, handler, CLIENT_ID);
        getSupportActionBar().setTitle("오늘의 집사");//글꼴 변겯
        //액션바 배경 색 변경
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        String strColor = "#afff3200";//이짓을 꼭 해야하나..
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(strColor)));


    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_voice: {
                String mText;
                mText = Listing_Logic.main(final_result);
                mTextString = new String[]{mText};

                mNaverTTSTask = new NaverTTSTask();
                mNaverTTSTask.execute(mTextString);
            }
            break;

            case R.id.btn_reset:
                if (!naverRecognizer.getSpeechRecognizer().isRunning()) {
                    // Start button is pushed when SpeechRecognizer's state is inactive.
                    // Run SpeechRecongizer by calling recognize().
                    mResult = "";
//                    txtResult.setText("Connecting...");
                    btnStart.setText(R.string.str_stop);
                    naverRecognizer.recognize();
                } else {
                    btnStart.setEnabled(false);

                    naverRecognizer.getSpeechRecognizer().stop();
                }
                break;

            case R.id.btn_image_back:

                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);//꼭 이 방법만 있지는 않을텐데
                finish();
                break;


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // NOTE : initialize() must be called on start time.
        naverRecognizer.getSpeechRecognizer().initialize();
    }

    @Override
    public void onResume() {
        super.onResume();

        mResult = "";
//        txtResult.setText("");
        btnStart.setText(R.string.str_start);
        btnStart.setEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        // NOTE : release() must be called on stop time.
        naverRecognizer.getSpeechRecognizer().release();
    }

    public static class RecognitionHandler extends Handler {
        private final WeakReference<mimic_voice_Activity> mActivity;

        RecognitionHandler(mimic_voice_Activity activity) {
            mActivity = new WeakReference<mimic_voice_Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            mimic_voice_Activity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    public class NaverTTSTask extends AsyncTask<String[], Void, String> {

        @Override
        public String doInBackground(String[]... strings) {
            //여기서 서버에 요청
            APIExamTTS.main(mTextString);
            return null;
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


}
