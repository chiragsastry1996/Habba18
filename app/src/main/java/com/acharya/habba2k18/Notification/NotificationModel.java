package com.acharya.habba2k18.Notification;



public class NotificationModel {
    private String name;
    private String caption;

    public NotificationModel(String name, String caption) {
        this.name = name;
        this.caption = caption;
    }

    public String getName() {
        return name;
    }
    public String getCaption() {
        return caption;
    }
}
