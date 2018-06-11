package com.open.sample.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import com.duolebo.appbase.utils.Preference;

/**
 */
abstract public class ActivityBase extends com.duolebo.appbase.activity.ActivityBase {
    private static String TAG = "ActivityBase";
    abstract protected String getActivityName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Preference pref;

    private  Preference getPref(Context context) {
        if (null == pref) {
            pref = new Preference(context, "UserAuth");
        }
        return pref;
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
