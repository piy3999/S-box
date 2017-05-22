package com.example.piyusharyan.s_box;


public class Like {

    public int cnt;

    public Like() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Like(int cnt) {
        this.cnt = cnt;

    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}

