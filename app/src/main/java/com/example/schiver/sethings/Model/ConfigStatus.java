package com.example.schiver.sethings.Model;

public class ConfigStatus {
    String deviceId,deviceName,deviceType;
    float energyUsage,sensorValue;
    int actuatorStatus,lampStatus;

    public ConfigStatus(){}

    public ConfigStatus(String deviceId, String deviceName, String deviceType, float energyUsage, float sensorValue, int actuatorStatus, int lampStatus) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.energyUsage = energyUsage;
        this.sensorValue = sensorValue;
        this.actuatorStatus = actuatorStatus;
        this.lampStatus = lampStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public float getEnergyUsage() {
        return energyUsage;
    }

    public void setEnergyUsage(float energyUsage) {
        this.energyUsage = energyUsage;
    }

    public float getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(float sensorValue) {
        this.sensorValue = sensorValue;
    }

    public int getActuatorStatus() {
        return actuatorStatus;
    }

    public void setActuatorStatus(int actuatorStatus) {
        this.actuatorStatus = actuatorStatus;
    }

    public int getLampStatus() {
        return lampStatus;
    }

    public void setLampStatus(int lampStatus) {
        this.lampStatus = lampStatus;
    }
}
