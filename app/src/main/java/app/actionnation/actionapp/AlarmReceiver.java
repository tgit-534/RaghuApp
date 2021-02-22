package app.actionnation.actionapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub
        Boolean bl = k2.getExtras().getBoolean("on");


        Intent service_intent = new Intent(k1, RingtonePlayingService.class);
        service_intent.putExtra("on",bl);

        k1.startService(service_intent);




           /*     Toast.makeText(k1, "Alarm received!", Toast.LENGTH_LONG).show();
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                mp = MediaPlayer.create(k1, notification);
                mp.start();*/

    }
}
