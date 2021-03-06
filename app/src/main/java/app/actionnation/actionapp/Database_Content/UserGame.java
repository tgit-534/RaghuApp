package app.actionnation.actionapp.Database_Content;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class UserGame {
    public UserGame() {

    }

    String fb_Id;
    String userName;
    int userSelfWinScore;
    int userPlaceWinScore;
    int userWordWinScore;
    int userWorkWinScore;
    int userDistractionScore;
    int userTractionScore;
    int userMeditationScore;
    int userTrueLearningScore;
    int userGratitudeScore;
    int userForgivenessSelfScore;
    int userForgivenessOutsideScore;
    int userAbundanceScore;
    int userEatHealthyScore;
    int userAvoidForHealthScore;
    int userExerciseScore;
    int userHabitsScore;
    int userExperienceNatureScore;
    int userRevealStoryScore;
    int userOurBeliefScore;
    int userTotatScore;
    int dayOfTheYear;
    int year;
    List<String> teamCaptains;
    int status;
    String gameDocumentId;
    int userCoinsPerDay;
    int userExellenceBar;
    @ServerTimestamp
    Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTeamCaptains(List<String> teamCaptains) {
        this.teamCaptains = teamCaptains;
    }

    public List<String> getTeamCaptains() {
        return teamCaptains;
    }

    public int getUserAvoidForHealthScore() {
        return userAvoidForHealthScore;
    }

    public void setUserAvoidForHealthScore(int userAvoidForHealthScore) {
        this.userAvoidForHealthScore = userAvoidForHealthScore;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public int getUserSelfWinScore() {
        return userSelfWinScore;
    }

    public void setUserSelfWinScore(int userSelfWinScore) {
        this.userSelfWinScore = userSelfWinScore;
    }

    public int getUserPlaceWinScore() {
        return userPlaceWinScore;
    }

    public void setUserPlaceWinScore(int userPlaceWinScore) {
        this.userPlaceWinScore = userPlaceWinScore;
    }

    public int getUserWordWinScore() {
        return userWordWinScore;
    }

    public void setUserWordWinScore(int userWordWinScore) {
        this.userWordWinScore = userWordWinScore;
    }

    public int getUserWorkWinScore() {
        return userWorkWinScore;
    }

    public void setUserWorkWinScore(int userWorkWinScore) {
        this.userWorkWinScore = userWorkWinScore;
    }

    public int getUserDistractionScore() {
        return userDistractionScore;
    }

    public void setUserDistractionScore(int userDistractionScore) {
        this.userDistractionScore = userDistractionScore;
    }

    public int getUserTractionScore() {
        return userTractionScore;
    }

    public void setUserTractionScore(int userTractionScore) {
        this.userTractionScore = userTractionScore;
    }

    public int getUserMeditationScore() {
        return userMeditationScore;
    }

    public void setUserMeditationScore(int userMeditationScore) {
        this.userMeditationScore = userMeditationScore;
    }

    public int getUserTrueLearningScore() {
        return userTrueLearningScore;
    }

    public void setUserTrueLearningScore(int userTrueLearningScore) {
        this.userTrueLearningScore = userTrueLearningScore;
    }

    public int getUserGratitudeScore() {
        return userGratitudeScore;
    }

    public void setUserGratitudeScore(int userGratitudeScore) {
        this.userGratitudeScore = userGratitudeScore;
    }

    public int getUserForgivenessSelfScore() {
        return userForgivenessSelfScore;
    }

    public void setUserForgivenessSelfScore(int userForgivenessSelfScore) {
        this.userForgivenessSelfScore = userForgivenessSelfScore;
    }

    public int getUserForgivenessOutsideScore() {
        return userForgivenessOutsideScore;
    }

    public void setUserForgivenessOutsideScore(int userForgivenessOutsideScore) {
        this.userForgivenessOutsideScore = userForgivenessOutsideScore;
    }

    public int getUserAbundanceScore() {
        return userAbundanceScore;
    }

    public void setUserAbundanceScore(int userAbundanceScore) {
        this.userAbundanceScore = userAbundanceScore;
    }

    public int getUserEatHealthyScore() {
        return userEatHealthyScore;
    }

    public void setUserEatHealthyScore(int userEatHealthyScore) {
        this.userEatHealthyScore = userEatHealthyScore;
    }

    public int getUserExerciseScore() {
        return userExerciseScore;
    }

    public void setUserExerciseScore(int userExerciseScore) {
        this.userExerciseScore = userExerciseScore;
    }

    public int getUserHabitsScore() {
        return userHabitsScore;
    }

    public void setUserHabitsScore(int userHabitsScore) {
        this.userHabitsScore = userHabitsScore;
    }

    public int getUserExperienceNatureScore() {
        return userExperienceNatureScore;
    }

    public void setUserExperienceNatureScore(int userExperienceNatureScore) {
        this.userExperienceNatureScore = userExperienceNatureScore;
    }

    public int getUserRevealStoryScore() {
        return userRevealStoryScore;
    }

    public void setUserRevealStoryScore(int userRevealStoryScore) {
        this.userRevealStoryScore = userRevealStoryScore;
    }

    public int getUserOurBeliefScore() {
        return userOurBeliefScore;
    }

    public void setUserOurBeliefScore(int userOurBeliefScore) {
        this.userOurBeliefScore = userOurBeliefScore;
    }

    public int getUserTotatScore() {
        return userTotatScore;
    }

    public void setUserTotatScore(int userTotatScore) {
        this.userTotatScore = userTotatScore;
    }

    public int getDayOfTheYear() {
        return dayOfTheYear;
    }

    public void setDayOfTheYear(int dayOfTheYear) {
        this.dayOfTheYear = dayOfTheYear;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}


















