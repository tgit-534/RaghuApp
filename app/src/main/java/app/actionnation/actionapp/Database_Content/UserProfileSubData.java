package app.actionnation.actionapp.Database_Content;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserProfileSubData {
    public UserProfileSubData() {
    }



    @ServerTimestamp
    Date timestamp;
    int status;
    float userRating;
    int userNoOfRatings;
    int coinStake;
    String gameDocumentId;

    public String getGameDocumentId() {
        return gameDocumentId;
    }

    public void setGameDocumentId(String gameDocumentId) {
        this.gameDocumentId = gameDocumentId;
    }

    public int getCoinStake() {
        return coinStake;
    }

    public void setCoinStake(int coinStake) {
        this.coinStake = coinStake;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public int getUserNoOfRatings() {
        return userNoOfRatings;
    }

    public void setUserNoOfRatings(int userNoOfRatings) {
        this.userNoOfRatings = userNoOfRatings;
    }
}
