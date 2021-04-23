package app.actionnation.actionapp.adapters;

import android.app.Application;
import android.content.res.Resources;

public class AccessResourcesClass extends Application {
    private static AccessResourcesClass mInstance;
    private static Resources res;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        res = getResources();
    }

    public static AccessResourcesClass getInstance() {
        return mInstance;
    }

    public static Resources getRes() {
        return res;
    }

}
