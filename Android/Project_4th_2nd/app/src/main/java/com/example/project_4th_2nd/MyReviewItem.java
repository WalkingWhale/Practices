package com.example.project_4th_2nd;

public class MyReviewItem {
    String id;
    String fName;
    String userId;
    String nickname;
    String date;
    String review;
    float rating;

    public MyReviewItem(String id, String fName, String userId, String nickname, String date, String review, float rating) {
        this.id = id;
        this.fName = fName;
        this.userId = userId;
        this.nickname = nickname;
        this.date = date;
        this.review = review;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
