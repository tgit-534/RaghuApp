package app.actionnation.actionapp.Database_Content;

public class Person_Integrity {

    public Person_Integrity() {

    }

    String fb_Id;
    String promiseDescription;
    long promiseDate;
    int status;

    public long getPromiseDate() {
        return promiseDate;
    }

    public void setPromiseDate(long promiseDate) {
        this.promiseDate = promiseDate;
    }

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }
    public String getPromiseDescription() {
        return promiseDescription;
    }

    public void setPromiseDescription(String promiseDescription) {
        this.promiseDescription = promiseDescription;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
