package com.example.project_4th_2nd;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FestivalItemAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<FestivalItem> items = new ArrayList<>();

    FestivalItem item;

    FirebaseFirestore db;
    private StorageReference mStorageRef;

    public FestivalItemAdapter(Context context) {
        this.context = context;
    }

    public void addItem(FestivalItem item){
        items.add(item);
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

    public boolean hasItem() {
        if(items.size() > 1){
            return true;
        } else {
            return false;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        db = FirebaseFirestore.getInstance();
        FestivalItemView view = null;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        item = items.get(position);

        if(convertView == null){
            view = new FestivalItemView(context, item.getfImgName());
        } else {
            view = (FestivalItemView) convertView;
        }

        view.setfName(item.getfName());
        view.setfPeriod(item.getfPeriod());
        view.setfLocation(item.getfLocation());
        return view;
    }

}
