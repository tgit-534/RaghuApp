package app.actionnation.actionapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ToastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub

           Toast.makeText(k1, "Focus On Work!", Toast.LENGTH_LONG).show();

    }
}