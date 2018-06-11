package com.duolebo.appbase.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    
    private String prefix;

    private SharedPreferences prefs;

    public Preference(Context context, String preferenceName) {
        this(context, preferenceName, "");
    }
    
    public Preference(Context context, String preferenceName, String prefix) {
        this.prefix = (null != prefix ? prefix : "");
        prefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }
    
    @SuppressLint("NewApi")
    public void save(String key, String value) {
        prefs.edit().putString(prefix + "_" + key, value).commit();
    }
    
    public void save(String key, boolean value) {
        prefs.edit().putBoolean(prefix + "_" + key, value).commit();
    }
    
    public void save(String key, int value) {
        prefs.edit().putInt(prefix + "_" + key, value).commit();
    }
    
    public String load(String key) {
        return prefs.getString(prefix + "_" + key, "");
    }
    
    public boolean loadBoolean(String key) {
        return prefs.getBoolean(prefix + "_" + key, false);
    }

    public int loadInt(String key) {
        return prefs.getInt(prefix + "_" + key, 0);
    }
}
