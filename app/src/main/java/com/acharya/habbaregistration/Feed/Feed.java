package com.acharya.habbaregistration.Feed;

public class Feed {
    private String name;
    private String caption;
    private String image;

    public Feed(String name, String caption, String image) {
        this.name = name;
        this.caption = caption;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCaption() {
        return caption;
    }
    public String getImage() {
        return image;
    }
}