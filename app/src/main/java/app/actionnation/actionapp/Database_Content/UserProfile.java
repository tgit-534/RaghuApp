package app.actionnation.actionapp.Database_Content;

import java.util.List;

public class UserProfile {
    public UserProfile() {

    }
    String fb_Id;
    String userFirstName;
    String userLastName;
    String userHandle;
    String userDream;
    String userChallenge;
    String userImagePath;
    String userCountry;
    String userState;
    int status;
    List<String> teamCaptains;
    float userRating;
    int userNoOfRatings;
    int userMemberType;
    int userFollowers;
    int userFollowing;
    String gameDocumentId;
    int userCoinsPerDay;
    int userExellenceBar;
    int noOfPlayers;

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public int getUserCoinsPerDay() {
        return userCoinsPerDay;
    }

    public void setUserCoinsPerDay(int userCoinsPerDay) {
        this.userCoinsPerDay = userCoinsPerDay;
    }

    public int getUserExellenceBar() {
        return userExellenceBar;
    }

    public void setUserExellenceBar(int userExellenceBar) {
        this.userExellenceBar = userExellenceBar;
    }

    public String getGameDocumentId() {
        return gameDocumentId;
    }

    public void setGameDocumentId(String gameDocumentId) {
        this.gameDocumentId = gameDocumentId;
    }

    public int getUserFollowers() {
        return userFollowers;
    }

    public void setUserFollowers(int userFollowers) {
        this.userFollowers = userFollowers;
    }

    public int getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(int userFollowing) {
        this.userFollowing = userFollowing;
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

    public int getUserMemberType() {
        return userMemberType;
    }

    public void setUserMemberType(int userMemberType) {
        this.userMemberType = userMemberType;
    }

    public List<String> getTeamCaptains() {
        return teamCaptains;
    }

    public void setTeamCaptains(List<String> teamCaptains) {
        this.teamCaptains = teamCaptains;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getUserDream() {
        return userDream;
    }

    public void setUserDream(String userDream) {
        this.userDream = userDream;
    }

    public String getUserChallenge() {
        return userChallenge;
    }

    public void setUserChallenge(String userChallenge) {
        this.userChallenge = userChallenge;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
