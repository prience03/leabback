package com.duolebo.appbase.activity;

/**
 * Created by ottauto on 15-11-3.
 */
public interface IActivityHost {

    void addObserver(IActivityObserver observer);
    void removeObserver(IActivityObserver observer);
    void clearObservers();
}
