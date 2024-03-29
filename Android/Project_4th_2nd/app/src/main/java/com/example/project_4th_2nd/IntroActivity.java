package com.example.project_4th_2nd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    private  static final String TAG = "lecture";
    private  static final int STOPSPLASH = 0;
    private  static final long SPLASHTIME = 2000;

    private Handler splashHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent;

            switch (msg.what){
                case STOPSPLASH:
                    intent = new Intent(IntroActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.hold);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg,SPLASHTIME);
    }
}
