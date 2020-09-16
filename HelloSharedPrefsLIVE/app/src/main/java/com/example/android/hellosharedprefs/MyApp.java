package com.example.android.hellosharedprefs;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.preference.PreferenceManager;

public class MyApp extends Application {

    public static final String SHARED_PREF_FILE = "edu.ranken.gmiller.hellosharedprefs";
    public static final String AUTO_SAVE_KEY = "auto_save";
    public static final String COLOR_KEY = "color";
    public static final String COUNT_KEY = "count";

    private SharedPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

    public boolean getAutoSave() {
        return mPreferences.getBoolean(AUTO_SAVE_KEY, true);
    }

    public int getCount() {
        try {
            return Integer.parseInt(mPreferences.getString(COUNT_KEY, ""));

        } catch (NumberFormatException | NullPointerException ex) {
            return 0;
        }
    }

    public int getColor() {
        Resources res = getResources();
        String colorPref = mPreferences.getString(COLOR_KEY, "");
        switch (colorPref) {
            case "black":
                return res.getColor(android.R.color.black);
            case "red":
                return res.getColor(R.color.red_background);
            case "green":
                return res.getColor(R.color.green_background);
            case "blue":
                return res.getColor(R.color.blue_background);
            default:
                return res.getColor(R.color.default_background);
        }
    }

    public void saveState(int count, int color) {
        Resources res = getResources();
        String colorRef;
        if (color == res.getColor(android.R.color.black)) {
            colorRef = "black";
        } else if (color == res.getColor(R.color.red_background)) {
            colorRef = "red";
        } else if (color == res.getColor(R.color.green_background)) {
            colorRef = "green";
        } else if (color == res.getColor(R.color.blue_background)) {
            colorRef = "blue";
        } else {
            colorRef = "default";
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(COUNT_KEY, String.valueOf(count));
        editor.putString(COLOR_KEY, colorRef);
        editor.apply();
    }
}
