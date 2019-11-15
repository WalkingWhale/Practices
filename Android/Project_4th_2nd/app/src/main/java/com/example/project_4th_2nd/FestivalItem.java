package com.example.project_4th_2nd;

import android.graphics.Bitmap;

public class FestivalItem {
    private String fName;
    private String fPeriod;
    private String fLocation;
    private String fLocationOrg;
    private String fLocation_specific;
    private String fImgName;
    private Bitmap fImg;
    private double fLatitude;
    private double fLongitude;
    private String fContent;

    public FestivalItem(String fName, String fLocation, String fPeriod, String fImgName) {
        this.fName = fName;
        this.fPeriod = fPeriod;
        this.fLocation = fLocation  + " - ";
        this.fLocationOrg = fLocation;
        this.fImgName = fImgName;
    }

    public FestivalItem(String fName, String fLocation, String fLocation_specific ,String fPeriod, String fImgName, double fLatitude, double fLongitude, String fContent) {
        this.fName = fName;
        this.fPeriod = fPeriod;
        this.fLocation = fLocation  + " - ";
        this.fLocationOrg = fLocation;
        this.fLocation_specific = fLocation_specific;
        this.fImgName = fImgName;
        this.fLatitude = fLatitude;
        this.fLongitude = fLongitude;
        this.fContent = fContent;
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

    public double getfLatitude() {
        return fLatitude;
    }

    public void setfLatitude(double fLatitude) {
        this.fLatitude = fLatitude;
    }

    public double getfLongitude() {
        return fLongitude;
    }

    public void setfLongitude(double fLongitude) {
        this.fLongitude = fLongitude;
    }

    public String getfContent() {
        return fContent;
    }

    public void setfContent(String fContent) {
        this.fContent = fContent;
    }
}
