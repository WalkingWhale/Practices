package com.example.ex07_imageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    ScrollView scrollView1;
    ScrollView scrollView2;

    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //이미지 뷰
        imageView1 = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);

        imageView1.setImageResource(R.drawable.background1);
        imageView2.setImageResource(0);

        imageView1.invalidate();
        imageView2.invalidate();

        // 스크롤바의 유무만 달라질 뿐, 실제로 스크롤은 한다.
        scrollView1 = findViewById(R.id.scrollView1);
        scrollView1.setVerticalScrollBarEnabled(true);
        scrollView1.setHorizontalScrollBarEnabled(true);
    }

    public void onBtn1Clicked(View v){   imageDown(); }
    public void onBtn2Clicked(View v){   imageUp(); }

    public void imageDown(){
        imageView1.setImageResource(0);
        imageView2.setImageResource(R.drawable.background1);
        imageView1.invalidate();
        imageView2.invalidate();
    }

    public void imageUp(){
        imageView1.setImageResource(R.drawable.background1);
        imageView2.setImageResource(0);
        imageView1.invalidate();
        imageView2.invalidate();
    }
}
