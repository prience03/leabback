package com.duolebo.appbase.activity;

import android.app.Activity;

/**
 * Created by ottauto on 15-11-3.
 */
public interface IActivityObserver {
    void onActivityCreate(Activity activity);
    void onActivityStart(Activity activity);
    void onActivityResume(Activity activity);
    void onActivityPause(Activity activity);
    void onActivityStop(Activity activity);
    void onActivityDestroy(Activity activity);
}
