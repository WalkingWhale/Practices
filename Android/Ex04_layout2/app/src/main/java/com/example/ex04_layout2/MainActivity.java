package com.example.ex04_layout2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    //
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    ImageView imgView;
    boolean imageSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageSelected = false;

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);

    }

    public void onBtn1Clicked(View v){
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
    }

    public void onBtn2Clicked(View v){
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
        layout3.setVisibility(View.GONE);
    }

    public void onBtn3Clicked(View v){
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.VISIBLE);
    }

    public void onBtn6Clicked(View v){
        imgView = findViewById(R.id.imageView);

        if(imageSelected){
            imgView.setImageResource(R.drawable.car);
            imageSelected = false;
        } else {
            imgView.setImageResource(R.drawable.house);
            imageSelected = true;
        }
    }
}
