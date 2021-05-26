package app.actionnation.actionapp.Database_Content;

public class TeamGameSubData {
    public TeamGameSubData() {

    }
    int status;
    int noOfPlayersAccepted;
    int totalCoinsOfPlayers;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
