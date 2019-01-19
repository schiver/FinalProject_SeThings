package com.example.schiver.sethings.Model;

import java.util.ArrayList;

public class ConfigDevice {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private int mColor;
    private String mId; // type
    private String mName;
    private String mType;
    public ConfigDevice(){}

    public ConfigDevice(int mImageResource, String mText1, String mText2, int mColor, String mId, String mName, String mType) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.mColor = mColor;
        this.mId = mId;
        this.mName = mName;
        this.mType = mType;
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

    public String getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmType() {
        return mType;
    }
}
