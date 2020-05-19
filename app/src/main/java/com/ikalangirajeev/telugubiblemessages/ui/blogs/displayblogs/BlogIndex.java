package com.ikalangirajeev.telugubiblemessages.ui.blogs.displayblogs;

public class BlogIndex {
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


    public BlogIndex() {
    }

    public BlogIndex(String blogId, String authorUid, String authorName, String timeStamp, String email, String blogTitle, String blogDescription, int likesCounter, int dislikesCounter, int commentsCounter) {
        this.blogId = blogId;
        this.authorUid = authorUid;
        this.authorName = authorName;
        this.timeStamp = timeStamp;
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
}
