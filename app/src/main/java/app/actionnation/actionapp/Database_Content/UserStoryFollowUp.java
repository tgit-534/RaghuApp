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
    @ServerTimestamp
    Date timestamp;


}
