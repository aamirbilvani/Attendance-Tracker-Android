package com.novatex.attendace;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.novatex.attendace.api.ApiCallRequest;
import com.novatex.attendace.utilities.Constant;
import com.novatex.attendace.utilities.Utility;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;
import static com.novatex.attendace.utilities.Constant.APPLICATION_CONFIG_PREF;
import static com.novatex.attendace.utilities.Constant.GENERIC_ERROR_API;
import static com.novatex.attendace.utilities.Constant.NEAR_LOCATION_GEOFENCE_RADIUS;
import static com.novatex.attendace.utilities.Constant.NEAR_LOCATION_GEOFENCE_RADIUS_GRACE;

public class MarkAttendanceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonMarkAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        init();
        Utility.FirstTimeWork(this);
        getPermissions();
        getLocation();

    }

    public void init() {
        buttonMarkAttendance = findViewById(R.id.buttonMarkAttendance);
        buttonMarkAttendance.setOnClickListener(this);
        buttonMarkAttendance.setEnabled(false);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            buttonMarkAttendance.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.submitbuttonshapedisabled) );
        } else {
            buttonMarkAttendance.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.submitbuttonshapedisabled));
        }
    }

    private void getPermissions() {

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, INTERNET) != PackageManager.PERMISSION_GRANTED) {

            SharedPreferences mPrefs = getSharedPreferences(APPLICATION_CONFIG_PREF, MODE_PRIVATE);

            int notShowLocationPermission = mPrefs.getInt("notShowLocationPermission", 0);

            if (notShowLocationPermission == 0) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Location permission necessary");
                alertBuilder.setMessage(getResources().getString(R.string.app_name) + " would like to access your location to provide you with better information about products and prices based on your current location.");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ActivityCompat.requestPermissions(MarkAttendanceActivity.this, new String[]{
                                ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, INTERNET
                        }, 10);

                        new Thread(new Runnable() {

                            public void run() {
                                try {
                                    Utility.startLocationTrackingAlarm(getApplicationContext());
                                    getLocation();

                                } catch (Exception ex) {
                                    Log.e("error", ex.toString());
                                }
                            }
                        }).start();

                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        } else {

            new Thread(new Runnable() {

                public void run() {
                    try {

                        Utility.startLocationTrackingAlarm(getApplicationContext());
                        getLocation();

                    } catch (Exception ex) {
                        Log.e("error", ex.toString());
                    }
                }
            }).start();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Utility.startLocationTrackingAlarm(getApplicationContext());
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {

                        SharedPreferences mPrefs = getSharedPreferences(APPLICATION_CONFIG_PREF, MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor = mPrefs.edit();

                        prefsEditor.putInt("notShowLocationPermission", 1);

                        prefsEditor.apply();
                    }
                }
                return;
        }
    }


    private void getLocation() {

        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, locationListener);
        } catch (Exception ex) {

        }
    }


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location currentLocation) {
//,

            double storeLat = 24.856483333333337;
            double storeLng = 67.02148833333334;

            double geofenceRadius = NEAR_LOCATION_GEOFENCE_RADIUS;
            double geofenceRadiusGrace = NEAR_LOCATION_GEOFENCE_RADIUS_GRACE;

            if (currentLocation.getLatitude() > storeLat - geofenceRadius && currentLocation.getLatitude() < storeLat + geofenceRadius && currentLocation.getLongitude() > storeLng - geofenceRadius && currentLocation.getLongitude() < storeLng + geofenceRadius) {
                buttonMarkAttendance.setEnabled(true);
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    buttonMarkAttendance.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.createbutton) );
                } else {
                    buttonMarkAttendance.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.createbutton));
                }

            } else {
                buttonMarkAttendance.setEnabled(false);
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    buttonMarkAttendance.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.submitbuttonshapedisabled) );
                } else {
                    buttonMarkAttendance.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.submitbuttonshapedisabled));
                }
            }


           // Toast.makeText(MarkAttendanceActivity.this, "this is current location\n Lat : " + currentLocation.getLatitude() + "\nLng : " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonMarkAttendance:
                Toast.makeText(this, "Attendance Marked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
