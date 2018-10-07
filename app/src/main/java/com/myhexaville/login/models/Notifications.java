package com.myhexaville.login.models;

public class Notifications {

    public String id;
    public String notification;

    public Notifications(){}

    public Notifications (String id, String notification){
        this.notification = notification;
        this.id = id;
    }

    public String getNotification(){
        return this.notification;
    }

    public void setNotification(String notification){
        this.notification = notification;
    }
}

