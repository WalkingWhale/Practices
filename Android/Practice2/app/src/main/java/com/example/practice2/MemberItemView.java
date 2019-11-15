package com.example.practice2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemberItemView extends LinearLayout {
    TextView name;
    TextView pNum;
    Button call;
    Button text;

    ImageView imageView;

    public MemberItemView(Context context, boolean gender){
        super(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(gender){
            inflater.inflate(R.layout.member_list_view_female, this, true);

            name = findViewById(R.id.Name_Female);
            pNum = findViewById(R.id.Pnum_Female);
            call = findViewById(R.id.Call_Female);
            text = findViewById(R.id.Text_Female);
            imageView = findViewById(R.id.imageView_female);

        } else {

            inflater.inflate(R.layout.member_list_view_male, this, true);

            name = findViewById(R.id.Name_Male);
            pNum = findViewById(R.id.Pnum_Male);
            call = findViewById(R.id.Call_Male);
            text = findViewById(R.id.Text_Male);
            imageView = findViewById(R.id.imageView_male);
        }


    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setpNum(String pNum) {
        this.pNum.setText(pNum);
    }

    public void setImageView(int imgNum) {
        this.imageView.setImageResource(imgNum);
    }
}
