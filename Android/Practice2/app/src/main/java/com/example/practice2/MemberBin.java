package com.example.practice2;

public class MemberBin {
    String name;
    String pNum;
    boolean gender;     // 남자면 false 여자면 true
    int imgNum;

    public MemberBin(String name, String pNum, boolean gender) {
        this.name = name;
        this.pNum = pNum;
        this.gender = gender;
        this.imgNum = -9999;
    }

    public MemberBin(String name, String pNum, boolean gender, int imgNum) {
        this.name = name;
        this.pNum = pNum;
        this.gender = gender;
        this.imgNum = imgNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getImgNum() {
        return imgNum;
    }

    public void setImgNum(int imgNum) {
        this.imgNum = imgNum;
    }
}
