package com.example.piyusharyan.s_box;


public class Post {

    public String head;
    public String pic;
    public String query;
    public int likeCount;
    public String key;
    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Post(String head, String pic, String query, int likeCount, String key) {
        this.head = head;
        this.pic = pic;
        this.query = query;
        this.key =key;
        this.likeCount = likeCount;

    }

    public int getLikeCount() {
        return likeCount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getHead() {
        return head;
    }

    public String getPic() {
        return pic;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}

