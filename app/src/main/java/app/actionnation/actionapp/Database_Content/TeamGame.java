package app.actionnation.actionapp.Database_Content;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class TeamGame {
    public TeamGame() {

    }

    String fb_Id;
    String gameDocumentId;
    String gameName;
    @ServerTimestamp Date timestamp;
    int startDay, endDay;
    int startYear, endYear;
    List<String> gamePlayers;
    String gameMasterName;
    long startDate, endDate;
    int noOfPlayers;

    public String getGameDocumentId() {
        return gameDocumentId;
    }

    public void setGameDocumentId(String gameDocumentId) {
        this.gameDocumentId = gameDocumentId;
    }

    int status;
    int noOfPlayersAccepted;
    int totalCoinsOfPlayers;
    int coinsAtStake;

    public int getCoinsAtStake() {
        return coinsAtStake;
    }

    public void setCoinsAtStake(int coinsAtStake) {
        this.coinsAtStake = coinsAtStake;
    }

    public int getNoOfPlayersAccepted() {
        return noOfPlayersAccepted;
    }

    public void setNoOfPlayersAccepted(int noOfPlayersAccepted) {
        this.noOfPlayersAccepted = noOfPlayersAccepted;
    }

    public int getTotalCoinsOfPlayers() {
        return totalCoinsOfPlayers;
    }

    public void setTotalCoinsOfPlayers(int totalCoinsOfPlayers) {
        this.totalCoinsOfPlayers = totalCoinsOfPlayers;
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public List<String> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(List<String> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public String getGameMasterName() {
        return gameMasterName;
    }

    public void setGameMasterName(String gameMasterName) {
        this.gameMasterName = gameMasterName;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
