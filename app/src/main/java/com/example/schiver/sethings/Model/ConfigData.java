package com.example.schiver.sethings.Model;

public class ConfigData {
    String deviceId,deviceEvent,deviceCondition,deviceAction;
    public ConfigData(){}

    public ConfigData(String deviceId, String deviceEvent, String deviceCondition, String deviceAction) {
        this.deviceId = deviceId;
        this.deviceEvent = deviceEvent;
        this.deviceCondition = deviceCondition;
        this.deviceAction = deviceAction;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceEvent() {
        return deviceEvent;
    }

    public void setDeviceEvent(String deviceEvent) {
        this.deviceEvent = deviceEvent;
    }

    public String getDeviceCondition() {
        return deviceCondition;
    }

    public void setDeviceCondition(String deviceCondition) {
        this.deviceCondition = deviceCondition;
    }

    public String getDeviceAction() {
        return deviceAction;
    }

    public void setDeviceAction(String deviceAction) {
        this.deviceAction = deviceAction;
    }
}
