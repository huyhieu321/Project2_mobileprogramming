package com.example.huypham.alarmproject_pro391x_fx00066.activity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;


import com.example.huypham.alarmproject_pro391x_fx00066.activity.service.AlarmService;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("I","IM in receiver");
        // Send data intent to service
        Intent intentAlarmService = new Intent(context, AlarmService.class);
        intentAlarmService.putExtras(intent.getExtras());
        intentAlarmService.putExtra("extra",intent.getExtras().getString("extra"));
        context.startService(intentAlarmService);


    }
}
