package com.example.mimi.capstone_zipsa;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mimi.capstone_zipsa.socket_service.MyBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    ListView listview = null;
    TextView tv;
    socket_service ms;
    boolean isService = false;
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        //  getActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setTitle("오늘의 집사");//글꼴 변겯
        //액션바 배경 색 변경
        // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        String strColor = "#afff3200";//이짓을 꼭 해야하나..
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(strColor)));

        // 별도의 홈 버튼 필요 한가

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       /* final String[] items = {"Mimic voice", "Control door", "Streaming", "Detect intrusion", "Set up(임시)", "창문 탐지(임시)"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        listview = (ListView) findViewById(R.id.drawer_menulist);
        listview.setAdapter(adapter);
        tv = (TextView) findViewById(R.id.tv);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id)

            {
                switch (position) {
                    case 0:
                        Intent mimic = new Intent(MainActivity.this, mimic_voice_Activity.class);
                        startActivity(mimic);
                        break;
                    case 1:
                        Intent control = new Intent(MainActivity.this, control_doorlock_Activity.class);
                        startActivity(control);
                        break;
                    case 2:
                        Intent streaming = new Intent(MainActivity.this, streaming.class);
                        startActivity(streaming);
                        break;
                    case 3:
                        Intent detect = new Intent(MainActivity.this, detect_intrusion_Activity.class);
                        startActivity(detect);
                        break;
                    case 4:
                        Intent setup = new Intent(MainActivity.this, set_up.class);
                        startActivity(setup);
                        break;
                    case 5: {

                    }
                    break;

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
*/

//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                FirebaseMessaging.getInstance().subscribeToTopic("news");
//                FirebaseInstanceId.getInstance().getToken();
//
//                Log.v("main Run ", FirebaseInstanceId.getInstance().getToken());
//            }
//        }).start();


    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_mimic_voice:
                Intent mimic = new Intent(MainActivity.this, mimic_voice_Activity.class);
                startActivity(mimic);
                break;
            case R.id.btn_control_doorlock:
                Intent control = new Intent(MainActivity.this, control_doorlock_Activity.class);
                startActivity(control);
                break;
            case R.id.btn_streaming:
                Intent streaming = new Intent(MainActivity.this, streaming.class);
                startActivity(streaming);
                break;
            case R.id.btn_detect_intrusion:
                Intent detect = new Intent(MainActivity.this, detect_intrusion_Activity.class);
                startActivity(detect);
                break;



        }
    }

    //액션 버튼 메뉴 액션바에 집어넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //액션 버튼 클릭했을 때의 동작 ( 여기에 set up )을 넣을 예정이다
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent intent = new Intent(this, window_picture.class);
            startActivity(intent);
            finish();

        }
        if (id == R.id.action_setup) {
            Intent intent = new Intent(this, set_up.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}