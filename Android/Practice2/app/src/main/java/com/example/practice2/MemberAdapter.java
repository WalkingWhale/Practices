package com.example.practice2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class MemberAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<MemberBin> members = new ArrayList<>();

    public MemberAdapter(Context context) { this.context = context; }
    public void addMember(MemberBin member) { members.add(member); }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int position) {
        return members.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MemberBin item = members.get(position);

        boolean gender = item.getGender();

        MemberItemView view = new MemberItemView(context, gender);

        view.setName(item.getName());
        view.setpNum(item.getpNum());
        if(item.getImgNum() == -9999){

        }

        return null;
    }
}
