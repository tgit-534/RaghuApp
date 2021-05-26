package app.actionnation.actionapp.Storage;

public class UserStorageGameObject {

    public UserStorageGameObject() {

    }

    String gameDocumentId;
    int userCoinsPerDay;
    int userExellenceBar;
    String gameFullDocumentObj;

    public String getGameFullDocumentObj() {
        return gameFullDocumentObj;
    }

    public void setGameFullDocumentObj(String gameFullDocumentObj) {
        this.gameFullDocumentObj = gameFullDocumentObj;
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
}
