package com.example.schiver.sethings.Model;

public class Control {
    private String state,device_id;
    public Control(){}

    public Control(String state, String device_id) {
        this.state = state;
        this.device_id = device_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
