/**
 * 
 */
package com.duolebo.appbase.app;

import android.content.Intent;

/**
 * @author zlhl
 * @date 2014年5月12日
 */
public interface IAppObserver {
        public void onApplicationInstalled(Intent intent, String packageName);
        public void onApplicationUninstalled(Intent intent, String packageName);
        public void onApplicationUpdated(Intent intent, String packageName);
}
