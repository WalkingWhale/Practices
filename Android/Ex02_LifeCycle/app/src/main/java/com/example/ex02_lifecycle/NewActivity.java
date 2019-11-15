package com.example.ex02_lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        Toast.makeText(getApplicationContext(),"onCreate() 호출됨",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onStart(){
        super.onStart();

        Toast.makeText(getApplicationContext(),"onStart() 호출됨",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();

        Toast.makeText(getApplicationContext(),"onResume() 호출됨",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();

        Toast.makeText(getApplicationContext(),"onPause() 호출됨",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();

        Toast.makeText(getApplicationContext(),"onStop() 호출됨",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        Toast.makeText(getApplicationContext(),"onDestroy() 호출됨",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");
    }

    // 버튼 1
    public void onButtonClicked(View v){
        Intent intent = new Intent();
        intent.putExtra("BackData", "강감찬");
        setResult(10,intent);

        finish();
    }
}
