package com.example.mimi.capstone_zipsa;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by dongsung on 2017-12-03.
 */

public class socket_service extends Service {

    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private int port = 1234;
    private String ip = "192.168.43.88";
    private MyHandler myHandler;
    private MyThread myThread;

    IBinder mBinder = new MyBinder();
    class MyBinder extends Binder {
        socket_service getService() {
            return socket_service.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent){
        return mBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            clientSocket = new Socket(ip, port);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        myHandler = new MyHandler();
        myThread = new MyThread();
        myThread.start();

    }
    void sendSignal(char sig){
        socketOut.println(sig);
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        myThread.stop();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    // InputStream의 값을 읽어와서 data에 저장
                    String data = socketIn.readLine();
                    // Message 객체를 생성, 핸들러에 정보를 보낼 땐 이 메세지 객체를 이용
                    Message msg = myHandler.obtainMessage();
                    msg.obj = data;
                    myHandler.sendMessage(msg);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyHandler extends Handler {
        //@Override
        public void handleMessage(Message msg) {
            if(msg.equals("a\n"))
            Toast.makeText(getApplicationContext(), "침입 감지",Toast.LENGTH_LONG).show();

        }
    }
}
