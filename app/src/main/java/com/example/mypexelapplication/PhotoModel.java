package com.example.mypexelapplication;

public class PhotoModel {

    private int id;
    private String smallUrl;

    public PhotoModel() {
    }

    public PhotoModel(int id, String smallUrl, String mediumUrl) {
        this.id = id;
        this.smallUrl = smallUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }
}
