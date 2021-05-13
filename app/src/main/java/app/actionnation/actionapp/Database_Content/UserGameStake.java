package app.actionnation.actionapp.Database_Content;

public class UserGameStake {

    public UserGameStake() {

    }

    String fb_Id;
    String gameDocumentId;
    String gameName;
    String gameMasterId;
    int Status;

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getGameDocumentId() {
        return gameDocumentId;
    }

    public void setGameDocumentId(String gameDocumentId) {
        this.gameDocumentId = gameDocumentId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameMasterId() {
        return gameMasterId;
    }

    public void setGameMasterId(String gameMasterId) {
        this.gameMasterId = gameMasterId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    String captainName;


}
