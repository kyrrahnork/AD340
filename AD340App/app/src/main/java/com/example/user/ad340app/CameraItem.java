package com.example.user.ad340app;

public class CameraItem {
    private String imageURL;
    private String name;

    public CameraItem(String imageURL, String name) {
        this.imageURL = imageURL;
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }
}
