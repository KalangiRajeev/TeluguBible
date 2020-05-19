package com.ikalangirajeev.telugubiblemessages.ui.blogs.detailblog;

import java.util.HashMap;
import java.util.Map;

public class Comment {

    public Comment() {
    }
    private String commentId;
    private String commentUser;
    private String timeStamp;
    private String commentUserUid;
    private String commentText;


    public Comment(String commentId, String commentUser, String timeStamp, String commentUserUid, String commentText) {
        this.commentId = commentId;
        this.commentUser = commentUser;
        this.timeStamp = timeStamp;
        this.commentUserUid = commentUserUid;
        this.commentText = commentText;
    }


    public String getTimeStamp() {
        return timeStamp;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public String getCommentUserUid() {
        return commentUserUid;
    }

    public String getCommentText() {
        return commentText;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("commentId", commentId);
        result.put("commentUser", commentUser);
        result.put("timeStamp", timeStamp);
        result.put("commentUserUid",commentUserUid);
        result.put("commentText",commentText);
     return  result;
    }
}
