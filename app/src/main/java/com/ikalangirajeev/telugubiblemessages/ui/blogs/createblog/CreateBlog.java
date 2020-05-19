package com.ikalangirajeev.telugubiblemessages.ui.blogs.createblog;

import android.text.Spannable;

import java.util.HashMap;
import java.util.Map;

public class CreateBlog {

    public CreateBlog() {
    }

    private String blogId;
    private String authorUid;
    private String authorName;
    private String timeStamp;
    private String email;
    private String blogTitle;
    private String blogDescription;
    private int likesCounter;
    private int dislikesCounter;
    private int commentsCounter;



    public CreateBlog(String blogId, String authorUid, String authorName, String timestamp, String email, String blogTitle, String blogDescription, int likesCounter, int dislikesCounter, int commentsCounter) {
        this.blogId = blogId;
        this.authorUid = authorUid;
        this.authorName = authorName;
        this.timeStamp = timestamp;
        this.email = email;
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.likesCounter = likesCounter;
        this.dislikesCounter = dislikesCounter;
        this.commentsCounter = commentsCounter;
    }

    public String getBlogId() {
        return blogId;
    }

    public String getAuthorUid() {
        return authorUid;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getEmail() {
        return email;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public int getDislikesCounter() {
        return dislikesCounter;
    }

    public int getCommentsCounter() {
        return commentsCounter;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("blogId", blogId);
        hashMap.put("authorUid", authorUid);
        hashMap.put("authorName", authorName);
        hashMap.put("timeStamp", timeStamp);
        hashMap.put("email", email);
        hashMap.put("blogTitle", blogTitle);
        hashMap.put("blogDescription", blogDescription);
        hashMap.put("likesCounter", likesCounter);
        hashMap.put("dislikesCounter", dislikesCounter);
        hashMap.put("commentsCounter", commentsCounter);
        return hashMap;
    }
}
