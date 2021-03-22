package app.actionnation.actionapp.Database_Content;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class UserCaptainRatings {


    public UserCaptainRatings() {

    }

    String fb_Id;
    String userCaptainFb_Id;
    String userComments;
    float captainRating;
    @ServerTimestamp Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getUserCaptainFb_Id() {
        return userCaptainFb_Id;
    }

    public void setUserCaptainFb_Id(String userCaptainFb_Id) {
        this.userCaptainFb_Id = userCaptainFb_Id;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public float getCaptainRating() {
        return captainRating;
    }

    public void setCaptainRating(float captainRating) {
        this.captainRating = captainRating;
    }

}
