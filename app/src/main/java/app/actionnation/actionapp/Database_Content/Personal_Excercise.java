package app.actionnation.actionapp.Database_Content;

public class Personal_Excercise {

    public Personal_Excercise()
    {

    }

    String fb_Id;
    String excerciseName;
    int status;

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getExcerciseName() {
        return excerciseName;
    }

    public void setExcerciseName(String excerciseName) {
        this.excerciseName = excerciseName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
