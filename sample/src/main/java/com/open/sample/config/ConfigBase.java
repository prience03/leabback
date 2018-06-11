package com.open.sample.config;

import android.content.Context;
import android.text.TextUtils;

import com.duolebo.appbase.app.AppManager;
import com.duolebo.appbase.prj.XMLHelper;
import com.duolebo.appbase.prj.bmtv.protocol.IDataConfig;
import com.duolebo.appbase.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by zlhl on 2015/3/28. // 知了：OTTAUTO  喂呦：ADVU_SHOP
 */
public class ConfigBase implements IDataConfig {
    //    private final String TVCODE = "TCL_OTTAUTO";
    private final String TVCODE = "APK_OTTAUTO";
    // 渠道号 和 manifest 一致 ，同时修改 。
//    private final String baiduTongjiChannel = "CIBN";
    private final boolean supportXiaomiTongji = true;

    private final String DialogOfflineAsset = "file:///android_asset/exit_ad.jpg";
    private final String PlayOfflineAsset = "file:///android_asset/play_ad.jpg";
    private final int DefaultPageIndex = 0;
    private final boolean IndicatorWithoutSubMenuClickable = true;
    private final String AboutLogoAsset = "file:///android_asset/about.png";
    private final boolean MaskEnabled = true;
    private final String AliPageName = "淘宝网";
    private final boolean IndicatorAtBottom = true;
    private final boolean supportBaiduTongji = true;
    private final String baiduTongjiAppKey = "6caf2f53e3";
    private final boolean showManagePage = false;
    private final int appPage = 1;
    private final boolean listWithSearch = false;
    private final boolean defaultStartAppPage = false;
    private final boolean appDetailWithRecommends = false;
    private final boolean linkToLocationSetting = false;
    private final boolean withoutExitAlert = false;
    private final String appPageName = "我的应用";
    private final String localAppName = "本地应用";
    private final boolean withBigCellGap = false;
    private final int newUiMode = 0;
    private final boolean IS_YINGYONGDAOHANG = false;
    private final boolean KEEP_ALIPAGE = false;
    private final String skinFilename = "myimages";
    private final String second_bg  = "second";
    private final String home_bg  = "home";
    private final String skin_version  = "skin_version";

    public static String paraUrl = "http://app.ottauto.cp59.ott.cibntv.net/checkIp.json";
    public static String url = "http://180.76.109.5/api/action.do";
    public static String adVideoUrl = "http://114.113.126.203:9998";

    @Override
    public String getProtocolUrl() {
        return "http://180.76.109.5/api/action.do";
    }


    private static String sTVID = null;

    @Override
    public String getTvid() {
        if (null == sTVID) {
            String mac = NetUtils.getMACAddress("eth0").replaceAll(":", "");

            if (TextUtils.isEmpty(mac))
                mac = NetUtils.getMACAddress("wlan0").replaceAll(":", "");

            if (TextUtils.isEmpty(mac))
                mac = NetUtils.getMACAddress(null).replaceAll(":", "");

            sTVID = getTvcode() + mac;
        }

        return sTVID;
    }

    @Override
    public String getChannel() {
        return "";
    }

    public String getTvcode() {
        return TVCODE;
    }

    public String getDialogOfflineAsset() {
        return DialogOfflineAsset;
    }

    public String getPlayOfflineAsset() {
        return PlayOfflineAsset;
    }

    public int getDefaultPageIndex() {
        return DefaultPageIndex;
    }

    public boolean isIndicatorWithoutSubMenuClickable() {
        return IndicatorWithoutSubMenuClickable;
    }

    public String getAboutLogoAsset() {
        return AboutLogoAsset;
    }

    public boolean isMaskEnabled() {
        return MaskEnabled;
    }

    public String getAliPageName() {
        return AliPageName;
    }

    public boolean isIndicatorAtBottom() {
        return IndicatorAtBottom;
    }

    public boolean isSupportBaiduTongji() {
        return supportBaiduTongji;
    }

    public String getBaiduTongjiAppKey() {
        return baiduTongjiAppKey;
    }

    public String getBaiduTongjiChannel() {
//        return baiduTongjiChannel;
        return "";
    }

    public boolean isShowManagePage() {
        return showManagePage;
    }

    public int getAppPage() {
        return appPage;
    }

    public boolean isListWithSearch() {
        return listWithSearch;
    }

    public boolean isDefaultStartAppPage() {
        return defaultStartAppPage;
    }

    public boolean isAppDetailWithRecommends() {
        return appDetailWithRecommends;
    }

    public boolean isLinkToLocationSetting() {
        return linkToLocationSetting;
    }

    public boolean isWithoutExitAlert() {
        return withoutExitAlert;
    }

    public String getAppPageName() {
        return appPageName;
    }

    public String getLocalAppName() {
        return localAppName;
    }

    public boolean isWithBigCellGap() {
        return withBigCellGap;
    }

    public int getNewUiMode() {
        return newUiMode;
    }

    public boolean isIsYingyongdaohang() {
        return IS_YINGYONGDAOHANG;
    }

    public boolean launchSetting(Context context) {
        return AppManager.launchSetting(context);
    }

    public boolean launchNetworkSetting(Context context) {
        return AppManager.launchNetworkSetting(context);
    }

    public boolean keepAliPage() {
        return KEEP_ALIPAGE;
    }

    public String proxy(String url) {
        return url;
    }

    public JSONArray getJsonArray(String res) throws JSONException {
        return new JSONArray(res);
    }

    public JSONObject getJsonObject(String res) throws JSONException {
        return new JSONObject(res);
    }

    public XMLHelper getXMLHelper(String res) throws XmlPullParserException {
        return new XMLHelper(res);
    }

    public String getSkinFilename() {
        return skinFilename;
    }

    public String getSecond_bg() {
        return second_bg;
    }

    public String getHome_bg() {
        return home_bg;
    }

    public String getSkin_version() {
        return skin_version;
    }
}
