package app.actionnation.actionapp.Database_Content;

public class Personal_Distraction {
    public Personal_Distraction()
    {

    }

    String fb_Id;
    String distrationName;
    int status;

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getDistrationName() {
        return distrationName;
    }

    public void setDistrationName(String distrationName) {
        this.distrationName = distrationName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
