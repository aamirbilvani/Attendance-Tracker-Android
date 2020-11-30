package com.novatex.attendace.utilities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ChangeLocation {


    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static Location lastLocation;
    private Context ClassContext;

    private IOnLocationChange locationChangeListener;


    public ChangeLocation(Context context) {
        ClassContext = context;
    }

    public void StartNearLocation() {


        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ClassContext.checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ClassContext.checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
            }

            LocationManager locationManager = (LocationManager) ClassContext.getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, locationListener);


        } catch (Exception ex) {
            Log.e("error", ex.toString());
        }


    }


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location currentLocation) {



            try {
                if (Utility.isBetterLocation(currentLocation, lastLocation)) {
                    lastLocation = currentLocation;

                    locationChangeListener.OnLocationChange(currentLocation,ClassContext);;

                    LocationManager locationManager = (LocationManager) ClassContext.getSystemService(Context.LOCATION_SERVICE);

                    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    locationManager.removeUpdates(this);
                    //}

                    //Utility.startNearStoreNotificationAlarm(ClassContext);

                }


            } catch (Exception ex) {
                Log.e("msg", ex.toString());
            }

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

    public void setCallBackListener(IOnLocationChange listener){
        this.locationChangeListener = listener;
    }

    public interface IOnLocationChange
    {
        void OnLocationChange(Location location, Context context);
    }

}



