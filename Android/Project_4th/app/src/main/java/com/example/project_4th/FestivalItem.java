package com.example.project_4th;

import android.graphics.Bitmap;

public class FestivalItem {
    private String fName;
    private String fPeriod;
    private String fLocation;
    private String fLocationOrg;
    private String fImgName;
    private Bitmap fImg;

    public FestivalItem(String fName, String fLocation, String fPeriod, String fImgName) {
        this.fName = fName;
        this.fPeriod = fPeriod;
        this.fLocation = fLocation  + " - ";
        this.fLocationOrg = fLocation;
        this.fImgName = fImgName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfPeriod() {
        return fPeriod;
    }

    public void setfPeriod(String fPeriod) {
        this.fPeriod = fPeriod;
    }

    public String getfLocation() {
        return fLocation;
    }

    public void setfLocation(String fLocation) {
        this.fLocation = fLocation;
    }

    public String getfLocationOrg() {
        return fLocationOrg;
    }

    public void setfLocationOrg(String fLocationOrg) {
        this.fLocationOrg = fLocationOrg;
    }

    public Bitmap getfImg() {
        return fImg;
    }

    public void setfImg(Bitmap fImg) {
        this.fImg = fImg;
    }

    public String getfImgName() {
        return fImgName;
    }

    public void setfImgName(String fImgName) {
        this.fImgName = fImgName;
    }
}
