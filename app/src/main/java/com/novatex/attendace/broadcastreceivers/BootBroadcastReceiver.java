package com.novatex.attendace.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.novatex.attendace.utilities.Utility;

public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Context finalContext=context;
        final Intent finalIntent = intent;



        new Thread(new Runnable() {
            public void run() {
                try {
                    if (finalIntent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {


                        Utility.startLocationTrackingAlarm(finalContext);

                    }
                } catch (Exception ex) {

                }

            }
        }).start();
    }
}
