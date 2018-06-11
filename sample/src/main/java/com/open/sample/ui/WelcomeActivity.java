package com.open.sample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duolebo.appbase.AppBaseHandler;
import com.duolebo.appbase.IAppBaseCallback;
import com.duolebo.appbase.IProtocol;
import com.duolebo.appbase.log.KLog;
import com.duolebo.appbase.prj.bmtv.protocol.GetToken;
import com.duolebo.appbase.utils.NetUtils;
import com.duolebo.appbase.utils.Preference;
import com.open.sample.R;
import com.open.sample.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class WelcomeActivity extends ActivityBase implements IAppBaseCallback{
    private RelativeLayout rl_wecome;
    private TextView tv_welcome_versionName;
    private LinearLayout ll_welcome_versionName;
    private static final String TAG = "WelcomeActivity";

    private final static int MSG_TO_ACTIVITY = 1;
    private final static int MSG_CHECK_NETWORK_CONNECTIVITY = 2;


    public AppBaseHandler dataHandler;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_TO_ACTIVITY:
                    Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case MSG_CHECK_NETWORK_CONNECTIVITY:
                    if (NetUtils.isConnected(WelcomeActivity.this)) {
                        initUI();
                        doGetToken();
                    }else {
                        rl_wecome.setBackgroundResource(R.mipmap.new_home_bg);
                    }
                    break;
            }
        }
    };

    @Override
    protected String getActivityName() {
        return WelcomeActivity.class.getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViews();
        dataHandler = new AppBaseHandler(this);
        handler.sendEmptyMessage(MSG_CHECK_NETWORK_CONNECTIVITY);

    }


    private void initUI() {
        rl_wecome.setBackgroundResource(R.mipmap.windowbackground);
        tv_welcome_versionName.setText("版本号:" );
        setAlphaAnimation(ll_welcome_versionName);
    }

    /**
     * 功能：版本号的透明度动画
     */
    private void setAlphaAnimation(View view) {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(500);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    private void findViews() {
        rl_wecome = (RelativeLayout)findViewById(R.id.rl_wecome);
        tv_welcome_versionName = (TextView) findViewById(R.id.tv_welcome_versionName);
        ll_welcome_versionName = (LinearLayout) findViewById(R.id.ll_welcome_versionName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }



    private void doGetToken() {
        (new GetToken(getBaseContext(), Config.getInstance())).execute(dataHandler);
    }

    @Override
    public void onProtocolSucceed(IProtocol protocol) {
        if (protocol instanceof GetToken) {
            handler.sendEmptyMessageDelayed(MSG_TO_ACTIVITY, 1000);
        }
    }

    @Override
    public void onProtocolFailed(IProtocol protocol) {
        rl_wecome.setBackgroundResource(R.mipmap.windowbackground);

    }

    @Override
    public void onHttpFailed(IProtocol protocol) {
        rl_wecome.setBackgroundResource(R.mipmap.windowbackground);
    }


    public static String get(Context context) {
        return getPref(context).load(PREF_USERID);
    }
    private static Preference pref;
    private static Preference getPref(Context context) {
        if (null == pref) {
            pref = new Preference(context, "bmtv");
        }
        return pref;
    }
    private static final String PREF_USERID = "ERROR";



}
