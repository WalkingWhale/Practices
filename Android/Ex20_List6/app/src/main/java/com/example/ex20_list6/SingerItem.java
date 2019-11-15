package com.example.ex20_list6;

public class SingerItem {
    private String name;
    private String telnum;
    private int resId;

    public SingerItem(String name, String tel, int resId) {
        this.name = name;
        this.telnum = tel;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

}
