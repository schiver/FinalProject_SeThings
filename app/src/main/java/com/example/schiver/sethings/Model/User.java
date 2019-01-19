package com.example.schiver.sethings.Model;

public class User {

    private String username,password,name,phone,home_id;

    public User(){

    }

    public User(String username, String password, String name, String phone, String home_id) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.home_id = home_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHome_id() {
        return home_id;
    }

    public void setHome_id(String home_id) {
        this.home_id = home_id;
    }
}
