package app.actionnation.actionapp.adapters;

import java.util.Calendar;

public class ValidationData {

    public ValidationData() {

    }

    public boolean validateDateForGame(long insertedDate) {

        boolean dateBool = true;
        Calendar cal = Calendar.getInstance();
        cal.getTimeInMillis();

        if (insertedDate < cal.getTimeInMillis()) {
            dateBool = false;
        }

        return dateBool;

    }

    public boolean validateDateInBetween(long startDate, long endDate) {

        boolean dateBool = false;
        Calendar cal = Calendar.getInstance();
        long currentTime = cal.getTimeInMillis();
        if (currentTime > startDate && currentTime < endDate) {
            dateBool = true;
        }
        return dateBool;

    }


}
