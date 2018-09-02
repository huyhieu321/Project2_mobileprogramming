package com.example.huypham.alarmproject_pro391x_fx00066.activity.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.huypham.alarmproject_pro391x_fx00066.R;

public class AlarmService extends Service {
    MediaPlayer media;
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return  null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MES","I'm in SERVICE");
        String key = intent.getExtras().getString("extra");
        int id = 0;
        media = MediaPlayer.create(this, R.raw.nhacchuong);
        if(key.equals("on")){
            id = 1;
            Log.i("MES","ON in service");
        }
        else if(key.equals("off")){
            id = 0;
            Log.i("MES","OFF in service");
        }

        if ( id == 1 ){
            media.start();
             id = 0;
        }
        else if ( id == 0){
            media.stop();
            media.reset();
        }
        return START_NOT_STICKY;
    }
}
