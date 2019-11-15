package com.example.ex52_fcmex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView log;
    String regId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log=(TextView)findViewById(R.id.log);

        Intent intent=getIntent();
        if(intent!=null &&intent.getExtras()!=null){

            for (String key : getIntent().getExtras().keySet()){
                String value=getIntent().getExtras().getString(key);
                Log.d(TAG,"Noti - "+ key + ":" + value );
            }
             /*
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Noti - " + key + ":" + value );
            }
            Noti - google.delivered_priority:high
            Noti - google.sent_time:null
            Noti - google.ttl:null
            Noti - google.original_priority:high
            Noti - from:296042452497
            Noti - google.message_id:0:1537056511730816%46105f7f46105f7f
            Noti - message:여기는 처리하고자 하는 내용입니다.
            Noti - collapse_key:com.study.android.fcm
            */

            Log.d(TAG,"알림 메시지가 있어요");


            String contents=intent.getStringExtra("message");
            if(contents!=null){
                processIntent(contents);
            }
        }

        getRegistrationId();
    }

    public void getRegistrationId(){
        println("getRegistrationId() 호출됨");

        regId = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"RegId" + regId);
        println("regId :" + regId);
    }

    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        println("onNewIntent() called.");

        if(intent !=null){
            String contents=intent.getStringExtra("message");
            processIntent(contents);
        }
    }

    private void processIntent(String contents){
        println("DATA : "+contents);
    }

    public void println(String data){
        log.append(data + "\n");
    }

}
