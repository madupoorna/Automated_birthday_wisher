package com.ctse.automatedbirthdaywisher;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Madupoorna on 3/29/2018.
 */

public class SharedPreferenceController {

    Context context;
    SharedPreferences prefs;
    String value;

    public SharedPreferenceController(Context context) {
        this.context = context;
    }

    public boolean setPreference(String key, String value) {
        prefs = context.getSharedPreferences("AUTO-WISH", MODE_PRIVATE);
        return prefs.edit().putString(key, value).commit();
    }

    public String getPreference(String key) {
        prefs = context.getSharedPreferences("AUTO-WISH", MODE_PRIVATE);
        value = prefs.getString(key, "empty");
        return value;
    }

}
