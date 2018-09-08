package com.myhexaville.login.views.lists.notifications;

public class NotificationItem {
    private String image;
    private String notification;

    public NotificationItem(String image, String notification) {
        this.image = image;
        this.notification = notification;
    }

    public String getImage() {
        return image;
    }

    public String getNotification() {
        return notification;
    }
}
