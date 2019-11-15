package com.example.fast_calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv_show,tv_result;
    String str;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_show = findViewById(R.id.tv_show);
        tv_show.setText("");
        tv_result = findViewById(R.id.tv_result);
        tv_result.setText("");
    }

    public void onBtn0Clicked(View v){
        str = tv_show.getText().toString() + "0";
        tv_show.setText(str);
    }

    public void onBtn1Clicked(View v){
        str = tv_show.getText().toString() + "1";
        tv_show.setText(str);
    }

    public void onBtn2Clicked(View v){
        str = tv_show.getText().toString() + "2";
        tv_show.setText(str);
    }

    public void onBtn3Clicked(View v){
        str = tv_show.getText().toString() + "3";
        tv_show.setText(str);
    }

    public void onBtn4Clicked(View v){
        str = tv_show.getText().toString() + "4";
        tv_show.setText(str);
    }

    public void onBtn5Clicked(View v){
        str = tv_show.getText().toString() + "5";
        tv_show.setText(str);
    }

    public void onBtn6Clicked(View v){
        str = tv_show.getText().toString() + "6";
        tv_show.setText(str);
    }

    public void onBtn7Clicked(View v){
        str = tv_show.getText().toString() + "7";
        tv_show.setText(str);
    }

    public void onBtn8Clicked(View v){
        str = tv_show.getText().toString() + "8";
        tv_show.setText(str);
    }

    public void onBtn9Clicked(View v){
        str = tv_show.getText().toString() + "9";
        tv_show.setText(str);
    }

    public void onBtnClearClicked(View v){
        str = "";
        tv_show.setText(str);
    }

    public void onBtnDeleteClicked(View V){
        str = str.substring(0,str.length()-1);
        tv_show.setText(str);
    }
}
