package app.actionnation.actionapp.Database_Content;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class TeamGame {
    public TeamGame() {

    }

    String fb_Id;
    String gameName;
    @ServerTimestamp Date timestamp;
    int startDay, endDay;
    int startYear, endYear;
    List<String> gamePlayers;
    String gameMasterName;
    long startDate, endDate;
    int status;


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
