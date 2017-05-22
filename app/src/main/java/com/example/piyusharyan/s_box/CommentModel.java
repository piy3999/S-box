package com.example.piyusharyan.s_box;

public class CommentModel {
    private String mName;
    private String mMessage;

    public CommentModel() {
        // Needed for Firebase
    }

    public CommentModel(String name, String message) {
        mName = name;
        mMessage = message;
         }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}