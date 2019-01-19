package com.example.schiver.sethings.Model;

public class EnergyDetailUsage {
    private int hour;
    private float usage;

    public EnergyDetailUsage(){}

    public EnergyDetailUsage(int hour, float usage) {
        this.hour = hour;
        this.usage = usage;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public float getUsage() {
        return usage;
    }

    public void setUsage(float usage) {
        this.usage = usage;
    }
}
