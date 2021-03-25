package app.actionnation.actionapp.Database_Content;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserStoryFollowUp {

    String fb_Id;
    String userName;
    String userStory;
    int userStoryId;
    String userComment;
    int userLikeCount;
    int userReshareCount;
    int userCommentCount;
    @ServerTimestamp Date timestamp;

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStory() {
        return userStory;
    }

    public void setUserStory(String userStory) {
        this.userStory = userStory;
    }

    public int getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(int userStoryId) {
        this.userStoryId = userStoryId;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public int getUserLikeCount() {
        return userLikeCount;
    }

    public void setUserLikeCount(int userLikeCount) {
        this.userLikeCount = userLikeCount;
    }

    public int getUserReshareCount() {
        return userReshareCount;
    }

    public void setUserReshareCount(int userReshareCount) {
        this.userReshareCount = userReshareCount;
    }

    public int getUserCommentCount() {
        return userCommentCount;
    }

    public void setUserCommentCount(int userCommentCount) {
        this.userCommentCount = userCommentCount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
