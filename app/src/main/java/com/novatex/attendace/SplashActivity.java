package com.novatex.attendace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.novatex.attendace.utilities.Global;

import static com.novatex.attendace.utilities.Constant.LOGIN_PREF;
import static com.novatex.attendace.utilities.Constant.SPLASH_SCREEN_DELAY;


public class SplashActivity extends AppCompatActivity {


    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            Global.version=pInfo.versionName;
        }
        catch (Exception ex)
        {
            Global.version="N/A";
        }
        delay();
    }


    private void delay() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                SharedPreferences mPrefs = getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
                String token = mPrefs.getString("token", "");

                if(token.equals(""))
                {

                    Intent i = new Intent(SplashActivity.this, EmailLoginActivity.class);

                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else
                {

                    Intent i = new Intent(SplashActivity.this, MarkAttendanceActivity.class);

                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        }, SPLASH_SCREEN_DELAY);


    }
}
