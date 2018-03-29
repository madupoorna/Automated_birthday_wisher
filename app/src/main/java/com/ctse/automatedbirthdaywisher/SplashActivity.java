package com.ctse.automatedbirthdaywisher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

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
        }
        process.changeLang(this, preference.getPreference("language"));

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
