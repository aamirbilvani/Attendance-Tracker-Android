package com.novatex.attendace.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.novatex.attendace.utilities.Utility;

import static android.content.Context.MODE_PRIVATE;
import static com.novatex.attendace.utilities.Constant.PREFS_LOCATION_TRACKING_NAME;
import static com.novatex.attendace.utilities.Constant.PREF_IS_FIRST_RUN;

public class UpdatePackageBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Context finalContext=context;

        new Thread(new Runnable() {
            public void run() {

                try {
                    SharedPreferences prefs = finalContext.getSharedPreferences(PREFS_LOCATION_TRACKING_NAME, MODE_PRIVATE);

                    Utility.startLocationTrackingAlarm(finalContext);

                    prefs.edit().putInt(PREF_IS_FIRST_RUN, 1).apply();

                    SharedPreferences  mPrefs = finalContext.getSharedPreferences("SyncPreferences", MODE_PRIVATE);
                    if (mPrefs.getInt("isMillSyncing", 0) != 1) {
                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        prefsEditor.putString("millSyncedDate", "");
                        prefsEditor.apply();
                    }

                } catch (Exception ex) {
                    Log.e("error",ex.toString());
                }

            }
        }).start();

    }
}
