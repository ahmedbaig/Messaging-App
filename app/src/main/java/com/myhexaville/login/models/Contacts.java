package com.myhexaville.login.models;

public class Contacts {
    public String id;
    public String number;

    public Contacts(){}

    public Contacts (String id, String number){
        this.id = id;
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
