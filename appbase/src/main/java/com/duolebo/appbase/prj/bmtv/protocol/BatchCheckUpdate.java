/**
 * 
 */

package com.duolebo.appbase.prj.bmtv.protocol;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.duolebo.appbase.IModel;
import com.duolebo.appbase.app.AppManager;
import com.duolebo.appbase.prj.bmtv.model.BatchCheckUpdateData;

import java.util.List;
import java.util.Map;

/**
 * @author zlhl
 * @date 2014年5月12日
 */
public class BatchCheckUpdate extends ProtocolBase {

    private BatchCheckUpdateData data = new BatchCheckUpdateData();

    public BatchCheckUpdate(Context context, IProtocolConfig config) {
        super(context, config);
    }

    @Override
    public IModel getData() {
        return data;
    }

    @Override
    protected void prepareProtocolBody(Map<String, String> body) {
        // return nothing.
    }
    
    @Override
    protected String prepareProtocolBody() {
        List<ApplicationInfo> appInfos = AppManager.getAllAppInfo(getContext(), false);
        PackageManager pm = getContext().getPackageManager();
        PackageInfo pkgInfo = null;
        StringBuilder sb = new StringBuilder();
        sb.append("<contentlist>");
        for (ApplicationInfo appInfo : appInfos) {
            try {
                pkgInfo = pm.getPackageInfo(appInfo.packageName, 0);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
                continue;
            }

            if (null != pkgInfo) {
                sb.append("<content>");
                sb.append("<package_name>");
                sb.append(appInfo.packageName);
                sb.append("</package_name>");
                sb.append("<version>");
                sb.append(pkgInfo.versionName);
                sb.append("</version>");
                sb.append("<version_code>");
                sb.append(pkgInfo.versionCode);
                sb.append("</version_code>");
                sb.append("</content>");
            }
        }
        sb.append("</contentlist>");
		return sb.toString();
    }

    @Override
    protected String prepareProtocolRequestKey() {
        return "BatchCheckUpdate";
    }
    
}
