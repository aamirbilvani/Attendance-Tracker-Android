package com.novatex.attendace.broadcastreceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;


import com.novatex.attendace.api.ApiCallRequest;
import com.novatex.attendace.utilities.ChangeLocation;
import com.novatex.attendace.utilities.Constant;
import com.novatex.attendace.utilities.Utility;

import java.util.Calendar;
import static com.novatex.attendace.utilities.Constant.SEND_LOCATIONS_TO_SERVER_ALARM_TIMER;

public class LocationTracking extends BroadcastReceiver implements ApiCallRequest.CallBackListener, ChangeLocation.IOnLocationChange {


    private ApiCallRequest callRequest;
    private static final LocationTracking receiver = new LocationTracking();

    @Override
    public void onReceive(final Context context, Intent intent) {

        try {

            //Utility.generateNotifcation("location tracking is working ", context, NEAR_LOCATION_CHANNEL_ID, NEAR_LOCATION_NOTIFICATION_ID);
            // if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 1 && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 6) {

            callRequest = new ApiCallRequest(context);
            callRequest.setCallBackListener(this);

            ChangeLocation changeLocation = new ChangeLocation(context);
            changeLocation.setCallBackListener(LocationTracking.this);
            changeLocation.StartNearLocation();

            //    }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                Intent intent3 = new Intent(context, LocationTracking.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent3, 0);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + SEND_LOCATIONS_TO_SERVER_ALARM_TIMER, pendingIntent);

                try {
                    context.getApplicationContext().unregisterReceiver(receiver);
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED);
                    context.getApplicationContext().registerReceiver(receiver, filter);

                } catch (Exception ex) {

                    IntentFilter filter = new IntentFilter();
                    filter.addAction(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED);
                    context.getApplicationContext().registerReceiver(receiver, filter);
                }
            }


        } catch (Exception ex) {
            Log.e("error", ex.toString());
        }
    }


    @Override
    public void callBack(int requestType, Object object) {

        switch (requestType) {
            case Constant.REQUEST_USER_LOCATION:
                /*
                UserLocationResponse response = (UserLocationResponse) object;
                if (!response.getResponse().getStatus().equalsIgnoreCase("200")) {
                    Log.e("error", "location unable to send");

                    return;
                }

                Log.e("msg", "Inserted user location");



                 */
                break;
            case Constant.REQUEST_FAILED:


                break;
        }

    }

    @Override
    public void OnLocationChange(Location location, Context context) {

        if (Utility.isNetworkAvailable(context)) {
/*
            UserLocationRequest request = new UserLocationRequest();
            request.setUserId("0");
            request.setLat(location.getLatitude() + "");
            request.setLng(location.getLongitude() + "");
            request.setHour(Calendar.getInstance().get(Calendar.HOUR) + "");


            SharedPreferences prefs = context.getSharedPreferences(PREFS_LOCATION_TRACKING_NAME, MODE_PRIVATE);
            String uuid = prefs.getString(PREF_UUID, DOESNT_EXIST_STRING);

            if (uuid.equals(DOESNT_EXIST_STRING)) {
                prefs.edit().putString(PREF_UUID, UUID.randomUUID().toString()).apply();
                uuid = prefs.getString(PREF_UUID, DOESNT_EXIST_STRING);
            }

            request.setDeviceId(uuid);

           //request.setUserId(as.getUserId());


            callRequest.requestUserLocation(request.getUserId(),request.getDeviceId(),request.getLat(),request.getLng());


 */
        }
    }
}

