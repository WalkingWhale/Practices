package com.example.ex02_lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("lecture", "콜백 함수 호출됨");

        if(requestCode == 1 && resultCode == 10){
            String sData = "";
            String str = "onActivityResult() called : " +
                    requestCode + " : " +
                    resultCode;
            sData = data.getStringExtra("BackData");
            str = str + " : " + sData;

            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }


    }

    // 버튼 1
    public void onButtonClicked(View v){
        Intent intent = new Intent(getApplicationContext(), NewActivity.class);
        startActivityForResult(intent, 1);
    }
}
