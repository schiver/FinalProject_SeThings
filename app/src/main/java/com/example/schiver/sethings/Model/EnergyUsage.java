package com.example.schiver.sethings.Model;

public class EnergyUsage {
    private float usage;
    private int day;
    public EnergyUsage(){}

    public EnergyUsage(float usage, int day) {
        this.usage = usage;
        this.day = day;
    }

    public float getUsage() {
        return usage;
    }

    public void setUsage(float usage) {
        this.usage = usage;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
