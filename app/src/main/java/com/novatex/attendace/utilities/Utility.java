package com.novatex.attendace.utilities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.novatex.attendace.BuildConfig;
import com.novatex.attendace.broadcastreceivers.LocationTracking;
import com.novatex.attendace.models.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;
import static com.novatex.attendace.utilities.Constant.DATE_TIME_FORMAT;
import static com.novatex.attendace.utilities.Constant.LOGIN_PREF;
import static com.novatex.attendace.utilities.Constant.PREF_UUID;
import static com.novatex.attendace.utilities.Constant.SEND_LOCATIONS_TO_SERVER_ALARM_TIMER;

public class Utility {


    public static void navigateToActivity(Activity navigateFrom, Class navigateTo) {

        Intent i = new Intent(navigateFrom, navigateTo);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        navigateFrom.startActivity(i);
    }


    public static void navigateToActivity(Activity navigateFrom, Class navigateTo, Bundle args) {

        Intent i = new Intent(navigateFrom, navigateTo);
        i.putExtras(args);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        navigateFrom.startActivity(i);
    }


    public static boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }


    public static void FirstTimeWork(Context context) {

        final Context finalContext = context;
        new Thread(new Runnable() {
            public void run() {
                try {
                    SharedPreferences prefs = finalContext.getSharedPreferences(Constant.PREFS_LOCATION_TRACKING_NAME, MODE_PRIVATE);
                    int isFirstRun = prefs.getInt(Constant.PREF_IS_FIRST_RUN, Constant.DOESNT_EXIST);

                    if (isFirstRun == Constant.DOESNT_EXIST) {

                        prefs.edit().putString(PREF_UUID, UUID.randomUUID().toString()).apply();

                        Utility.startLocationTrackingAlarm(finalContext.getApplicationContext());

                        prefs.edit().putInt(Constant.PREF_IS_FIRST_RUN, 1).apply();
                    }
                } catch (Exception ex) {
                    Log.e("error", ex.toString());
                }

            }
        }).start();

    }


    public static String getToken(Context context) {

        SharedPreferences mPrefs = context.getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);

        if (BuildConfig.DEBUG) {
            Log.e("token", mPrefs.getString("token", ""));
        }

        String token = "";
        try {
            token = CryptoKeystore.decText(mPrefs.getString("token", ""));
        } catch (Exception ex) {
            token = "";
        }
        return token;

    }


    public static boolean initLoggedInParam(Activity activity, User user) {

        SharedPreferences mPrefs = activity.getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        String token=user.getToken();

        try {
            token = CryptoKeystore.encText(user.getToken());
        } catch (Exception ex) {

        }


        prefsEditor.putString("token", token);
        prefsEditor.putString("username", user.getUsername());
        prefsEditor.putString("email", user.getFullname());
        prefsEditor.putString("contact_no", user.getPhone());
        prefsEditor.putString("brokerChk", user.getOffice_id() + "");
        prefsEditor.putString("companyName", user.getUser_id()+"");
        prefsEditor.apply();

        //FavRepository favRepository=new FavRepository(activity.getApplication());
        //YarnRequestRepository yarnRequestRepository=new YarnRequestRepository(activity.getApplication());

        //favRepository.deleteAllCache();
        //yarnRequestRepository.deleteAllCache();


/*
        ApiCallRequest callRequest;

        callRequest = new ApiCallRequest(activity);
        callRequest.setCallBackListener(new Utility());

        callRequest.requestGetUserSubscriptionList(100, 1);


 */
        return false;
    }




    public static String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        return dateFormat.format(calendar.getTime());
    }


    public static void startLocationTrackingAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, LocationTracking.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + SEND_LOCATIONS_TO_SERVER_ALARM_TIMER, pendingIntent);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), SEND_LOCATIONS_TO_SERVER_ALARM_TIMER, pendingIntent);
        }

    }



    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    public static boolean isBetterLocation(Location location, Location currentBestLocation) {

        int TWO_MINUTES = 1000 * 60 * 2;

        if (currentBestLocation == null) {
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }


    public static boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }






}
