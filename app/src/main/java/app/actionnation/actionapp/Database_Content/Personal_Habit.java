package app.actionnation.actionapp.Database_Content;

public class Personal_Habit {

   public Personal_Habit()
   {

   }

    String fb_Id;
    String habit, habitWorks, habitLevel;
    int status;
    int habitDays, powerLimit;
    long habitDate;
    int habitDayOfTheYear;
    int habitInspire;

    public String getHabitLevel() {
        return habitLevel;
    }

    public void setHabitLevel(String habitLevel) {
        this.habitLevel = habitLevel;
    }

    public int getHabitInspire() {
        return habitInspire;
    }

    public void setHabitInspire(int habitInspire) {
        this.habitInspire = habitInspire;
    }

    public int getPowerLimit() {
        return powerLimit;
    }

    public void setPowerLimit(int powerLimit) {
        this.powerLimit = powerLimit;
    }

    public int getHabitDayOfTheYear() {
        return habitDayOfTheYear;
    }

    public void setHabitDayOfTheYear(int habitDayOfTheYear) {
        this.habitDayOfTheYear = habitDayOfTheYear;
    }

    public long getHabitDate() {
        return habitDate;
    }

    public void setHabitDate(long habitDate) {
        this.habitDate = habitDate;
    }

    public int getHabitDays() {
        return habitDays;
    }

    public void setHabitDays(int habitDays) {
        this.habitDays = habitDays;
    }

    public String getFb_Id() {
        return fb_Id;
    }

    public void setFb_Id(String fb_Id) {
        this.fb_Id = fb_Id;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getHabitWorks() {
        return habitWorks;
    }

    public void setHabitWorks(String habitWorks) {
        this.habitWorks = habitWorks;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
