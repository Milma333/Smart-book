package com.example.milmaaishwarya.smartbook;

public class User {
    public String name, email;
    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name, String email){
        this.name=name;
        this.email=email;
    }
}