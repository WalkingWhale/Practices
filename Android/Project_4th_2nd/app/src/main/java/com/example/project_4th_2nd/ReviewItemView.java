package com.example.project_4th_2nd;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewItemView extends LinearLayout {

    TextView fName;
    TextView username;
    TextView date;
    TextView review;
    RatingBar rating;

    public ReviewItemView (Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.review_item, this, true);

        fName = findViewById(R.id.rv_festName);
        username = findViewById(R.id.rv_username);
        date = findViewById(R.id.rv_date);
        review = findViewById(R.id.rv_review);
        rating = findViewById(R.id.rv_rating);
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
