package app.actionnation.actionapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RingtonePlayingService extends Service {

    MediaPlayer mp;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
      /*  Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();*/
        Boolean bl;
        Log.e("rps", "Enter Ring Tone Playing Service");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            bl = extras.getBoolean("on");
            Log.e("rps", "Enter Ring Tone Playing Service inside.");

            if (bl) {
                // Toast.makeText(k1, "Alarm received!", Toast.LENGTH_LONG).show();
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                mp = MediaPlayer.create(getApplicationContext(), notification);
                mp.start();

            } else {
                if(mp!=null) {
                    mp.stop();
                    mp.reset();
                }
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.

        // Tell the user we stopped.
        Toast.makeText(this, "on destroyed called!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.


}

