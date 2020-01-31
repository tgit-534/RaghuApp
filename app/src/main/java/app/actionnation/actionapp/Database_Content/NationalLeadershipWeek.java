package app.actionnation.actionapp.Database_Content;

public class NationalLeadershipWeek {
String LeadershipStory, LeadershipTaskPic, UserId;
int status;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLeadershipStory() {
        return LeadershipStory;
    }

    public void setLeadershipStory(String leadershipStory) {
        LeadershipStory = leadershipStory;
    }

    public String getLeadershipTaskPic() {
        return LeadershipTaskPic;
    }

    public void setLeadershipTaskPic(String leadershipTaskPic) {
        LeadershipTaskPic = leadershipTaskPic;
    }

    public NationalLeadershipWeek() {
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
