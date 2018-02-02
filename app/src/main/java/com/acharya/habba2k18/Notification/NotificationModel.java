package com.acharya.habba2k18.Notification;



public class NotificationModel {
    private String name;
    private String caption;
    private String image;

    public NotificationModel(String name, String caption, String image) {
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
