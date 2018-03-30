package com.ctse.automatedbirthdaywisher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    SharedPreferenceController preference;
    Process process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.setTitle("");
        process = new Process();

        preference = new SharedPreferenceController(this);
        if (!preference.getPreference("language").equals("si")) {
            preference.setPreference("language", "en");
            Log.d(TAG, "Setting language for first use");
        }

        //change language
        process.changeLang(this, preference.getPreference("language"));
        Log.d(TAG, "Changing language to " + preference.getPreference("language"));

        //open Main screen after 2000 mili seconds
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(getBaseContext(), MainScreenActivity.class);
                startActivity(myIntent);
            }
        }, 2000);

    }
}
