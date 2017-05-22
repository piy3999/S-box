package com.example.piyusharyan.s_box;

public class U {
    private String mName;
    private String mId;
    private String mImage;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public U() {
        // Needed for Firebase
    }

    public U(String name, String id, String image) {
        mName = name;
        mId = id;
        mImage = image;
    }
}