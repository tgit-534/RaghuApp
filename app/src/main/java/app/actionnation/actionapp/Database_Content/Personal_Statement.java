package app.actionnation.actionapp.Database_Content;

public class Personal_Statement {

   public Personal_Statement()
   {

   }

    String Fb_Id;
    String PersonnalPurpose, PersonalMission, PersonalVision, personalVisualization;
    int Status;

    public String getFb_Id() {
        return Fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        Fb_Id = fb_Id;
    }

    public String getPersonnalPurpose() {
        return PersonnalPurpose;
    }

    public void setPersonnalPurpose(String personnalPurpose) {
        PersonnalPurpose = personnalPurpose;
    }

    public String getPersonalMission() {
        return PersonalMission;
    }

    public void setPersonalMission(String personalMission) {
        PersonalMission = personalMission;
    }

    public String getPersonalVision() {
        return PersonalVision;
    }

    public void setPersonalVision(String personalVision) {
        PersonalVision = personalVision;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
