package com.myhexaville.login.models;

public class User {
    private String id;
    private String phone;
    private String pass;

    public User(){}

    public User(String id, String phone, String pass) {
        this.id = id;
        this.phone = phone;
        this.pass = pass;
    }

    public String getId(){
        return this.id;
    }

    public String getPhone(){
        return this.phone;
    }

    public String getPass(){
        return this.pass;
    }

}
