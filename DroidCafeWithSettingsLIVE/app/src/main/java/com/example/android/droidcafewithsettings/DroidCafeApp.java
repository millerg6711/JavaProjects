package com.example.android.droidcafewithsettings;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class DroidCafeApp extends Application {

    private SharedPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public String getMarket() {
        return mPreferences.getString("market", "");
    }

    public String getDeliveryMethod() {
        return mPreferences.getString("delivery_method", "");
    }
}
