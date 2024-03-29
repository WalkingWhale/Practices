package com.example.ex18_list4;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout {
    TextView textView1;
    TextView textView2;

    ImageView imageView1;

    public SingerItemView(Context context){
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item_view, this, true);

        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        imageView1 = findViewById(R.id.imageView);
    }

    public void setName(String name){
        textView1.setText(name);
    }

    public void setAge(String age) {
        textView2.setText(age);
    }

    public void setImage(int imgNum){
        imageView1.setImageResource(imgNum);
    }
}
