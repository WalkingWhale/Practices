package com.example.project_4th_2nd;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyReviewItemView extends LinearLayout {

    TextView fName;
    TextView username;
    TextView date;
    TextView review;
    RatingBar rating;
    Button review_mod;
    Button review_del;

    public MyReviewItemView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_review_item, this, true);

        fName = findViewById(R.id.myrv_festName);
        username = findViewById(R.id.myrv_username);
        date = findViewById(R.id.myrv_date);
        review = findViewById(R.id.myrv_review);
        rating = findViewById(R.id.myrv_rating);

        review_mod = findViewById(R.id.btn_review_mod);
        review_del = findViewById(R.id.btn_review_del);
    }

    public void setfName(String name){
        fName.setText(name);
    }

    public void setUsername(String name){
        username.setText(name);
    }

    public void setDate(String period){
        date.setText(period);
    }

    public void setReview(String review_in){
        review.setText(review_in);
    }

    public void setRating(float rate){
        rating.setRating(rate);
    }
}
