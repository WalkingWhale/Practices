package com.example.ex19_list5;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class SingerAdapter extends BaseAdapter {

    Context context;
    ArrayList<SingerItem> items = new ArrayList<>();

    public SingerAdapter(Context context) {
        this.context = context;
    }

    public void addItem(SingerItem item){
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

    public View getView(int position, View convertView, ViewGroup parent) {

        SingerItemView view = null;

        if(convertView == null){
            view = new SingerItemView(context);
        } else {
            view = (SingerItemView) convertView;
        }

        SingerItem item = items.get(position);
        view.setName(item.getName());
        view.setAge(item.getAge());
        view.setImage(item.getResId());

        return view;
    }
}
