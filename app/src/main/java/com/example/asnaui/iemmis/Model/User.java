package com.example.asnaui.iemmis.Model;

public class User {
    private String name, userid;

    public User(String name, String userid) {
        this.name = name;
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

}
