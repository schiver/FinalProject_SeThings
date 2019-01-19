package com.example.schiver.sethings;

import android.content.res.ColorStateList;

public class ExampleItem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private int mColor;
    public ExampleItem(){}

    public ExampleItem(int mImageResource, String mText1, String mText2 , int mColor) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.mColor = mColor;
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
}
