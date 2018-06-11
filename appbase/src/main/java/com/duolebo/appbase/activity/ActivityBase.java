package com.duolebo.appbase.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ottauto on 15-11-3.
 */
public class ActivityBase extends  Activity implements IActivityHost {

    private ArrayList<IActivityObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(IActivityObserver observer) {
        synchronized (observers) {
            if (observers.indexOf(observer) < 0) {
                observers.add(observer);
            }
        }
    }

    @Override
    public void removeObserver(IActivityObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    @Override
    public void clearObservers() {
        synchronized (observers) {
            observers.clear();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iterator<IActivityObserver> itor = observers.iterator();
        while (itor.hasNext()) {
            IActivityObserver observer = itor.next();
            observer.onActivityCreate(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Iterator<IActivityObserver> itor = observers.iterator();
        while (itor.hasNext()) {
            IActivityObserver observer = itor.next();
            observer.onActivityStart(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Iterator<IActivityObserver> itor = observers.iterator();
        while (itor.hasNext()) {
            IActivityObserver observer = itor.next();
            observer.onActivityResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Iterator<IActivityObserver> itor = observers.iterator();
        while (itor.hasNext()) {
            IActivityObserver observer = itor.next();
            observer.onActivityPause(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Iterator<IActivityObserver> itor = observers.iterator();
        while (itor.hasNext()) {
            IActivityObserver observer = itor.next();
            observer.onActivityStop(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Iterator<IActivityObserver> itor = observers.iterator();
        while (itor.hasNext()) {
            IActivityObserver observer = itor.next();
            observer.onActivityDestroy(this);
            itor.remove();
        }
    }
}
