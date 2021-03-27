package app.actionnation.actionapp.Database_Content;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserStoryComments {

    String fb_Id;
    String strUserName;
    String userComment;
    String strUserImageUrl;


    String userStoryId;
    @ServerTimestamp
    Date timestamp;
    String strUserCommentId;

    public String getStrUserImageUrl() {
        return strUserImageUrl;
    }

    public void setStrUserImageUrl(String strUserImageUrl) {
        this.strUserImageUrl = strUserImageUrl;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(String userStoryId) {
        this.userStoryId = userStoryId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getStrUserCommentId() {
        return strUserCommentId;
    }

    public void setStrUserCommentId(String strUserCommentId) {
        this.strUserCommentId = strUserCommentId;
    }
}
