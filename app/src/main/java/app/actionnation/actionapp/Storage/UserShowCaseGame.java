package app.actionnation.actionapp.Storage;

public class UserShowCaseGame {

    public UserShowCaseGame() {

    }

    String userName;
    float userCoins;
    int userScore;
    int coinsRemaining;
    boolean userStatus;
    int CountCoinWinners;
    int CountCoinLosers;

    public int getCountCoinWinners() {
        return CountCoinWinners;
    }

    public void setCountCoinWinners(int countCoinWinners) {
        CountCoinWinners = countCoinWinners;
    }

    public int getCountCoinLosers() {
        return CountCoinLosers;
    }

    public void setCountCoinLosers(int countCoinLosers) {
        CountCoinLosers = countCoinLosers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(float userCoins) {
        this.userCoins = userCoins;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getCoinsRemaining() {
        return coinsRemaining;
    }

    public void setCoinsRemaining(int coinsRemaining) {
        this.coinsRemaining = coinsRemaining;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }
}
