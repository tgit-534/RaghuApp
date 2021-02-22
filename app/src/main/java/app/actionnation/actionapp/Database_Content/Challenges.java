package app.actionnation.actionapp.Database_Content;

public class Challenges {

    String fb_Id, eduid ;
    String Challenge_Name, Challenge_Desc, Edu_Name, Video_Url;
    int status,challengenumber;
    String created_at;

    // constructors
    public Challenges() {
    }

    public String getVideo_Url() {
        return Video_Url;
    }

    public void setVideo_Url(String video_Url) {
        Video_Url = video_Url;
    }

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getEduid() {
        return eduid;
    }

    public void setEduid(String eduid) {
        this.eduid = eduid;
    }

    public int getChallengenumber() {
        return challengenumber;
    }

    public void setChallengenumber(int challengenumber) {
        this.challengenumber = challengenumber;
    }

    public String getChallenge_Name() {
        return Challenge_Name;
    }

    public void setChallenge_Name(String challenge_Name) {
        Challenge_Name = challenge_Name;
    }

    public String getChallenge_Desc() {
        return Challenge_Desc;
    }

    public void setChallenge_Desc(String challenge_Desc) {
        Challenge_Desc = challenge_Desc;
    }

    public String getEdu_Name() {
        return Edu_Name;
    }

    public void setEdu_Name(String edu_Name) {
        Edu_Name = edu_Name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

