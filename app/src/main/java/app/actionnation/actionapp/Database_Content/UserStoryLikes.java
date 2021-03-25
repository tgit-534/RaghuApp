package app.actionnation.actionapp.Database_Content;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserStoryLikes {

    String fb_Id;
    String userStoryId;
    @ServerTimestamp
    Date timestamp;

    public String getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(String userStoryId) {
        this.userStoryId = userStoryId;
    }

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
