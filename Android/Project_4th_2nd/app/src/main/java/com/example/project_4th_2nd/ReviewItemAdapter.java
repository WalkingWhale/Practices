package com.example.project_4th_2nd;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReviewItemAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<ReviewItem> items = new ArrayList<>();

    FirebaseFirestore db;

    public ReviewItemAdapter(Context context) {
        this.context = context;
    }

    public void addItem(ReviewItem item){
        //Log.d(TAG, "add item 실행 id : " + item.getUserId());
        items.add(item);
        //Log.d(TAG, "cccc + " + items.size());
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void resetItem() { items.clear(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewItemView view = null;

        final ReviewItem item = items.get(position);

        if(convertView == null){
            view = new ReviewItemView(context);
        } else {
            view = (ReviewItemView) convertView;
        }

        view.setfName(item.getfName());
        view.setUsername(item.getNickname());
        view.setDate(item.getDate());
        view.setReview(item.getReview());
        view.setRating(item.getRating());

        return view;
    }
}
