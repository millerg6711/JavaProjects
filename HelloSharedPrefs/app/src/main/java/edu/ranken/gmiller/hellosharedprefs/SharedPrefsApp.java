package edu.ranken.gmiller.hellosharedprefs;

import android.app.Application;

public class SharedPrefsApp extends Application {
    public static final String SHARED_PREF_FILE = "edu.ranken.gmiller.hellosharedprefs";
    public static final String COLOR_KEY = "color";
    public static final String COUNT_KEY = "count";
    public static final String HAS_COUNT = "hasCount";
    public static final int SETTINGS_REQUEST = 0;
}
