package com.example.schiver.sethings.Model;

public class DeviceData {
    String deviceID,deviceName,deviceType;
    public DeviceData(){}
    public DeviceData(String deviceID, String deviceName, String deviceType) {
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
