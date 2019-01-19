package com.example.schiver.sethings.Model;

public class Device {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private int mColor;
    private String mDeviceId;

    public Device(){}

    public Device(int mImageResource, String mText1, String mText2, int mColor, String mDeviceId) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.mColor = mColor;
        this.mDeviceId = mDeviceId;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public int getmColor() {
        return mColor;
    }

    public String getmDeviceId() {
        return mDeviceId;
    }
}
