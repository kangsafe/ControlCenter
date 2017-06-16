package com.ks.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Administrator on 2017/6/15.
 */

public class HomeWatcherReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "HomeReceiver";
    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    private static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
    private static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";
    private static final String SYSTEM_DIALOG_REASON_VOICE = "voiceinteraction";
    private long lastTime = 0;
    private int num = 1;
    private Handler handler;
    private boolean status = false;
    private FlashLightManager flashLightManager;

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (flashLightManager == null) {
            flashLightManager = new FlashLightManager(context);
            flashLightManager.init();
        }
        String action = intent.getAction();
        Log.i(LOG_TAG, "onReceive: action: " + action);
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            // android.intent.action.CLOSE_SYSTEM_DIALOGS
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            Log.i(LOG_TAG, "reason: " + reason);

            if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                // 短按Home键
                Log.i(LOG_TAG, "homekey" + System.currentTimeMillis() + ":" + lastTime);
                //双击
                if ((System.currentTimeMillis() - lastTime) < 500) {
                    num++;
                    Log.i(LOG_TAG, "homekey点击次数" + num);
                } else {
                    lastTime = System.currentTimeMillis();
                    Log.i(LOG_TAG, "homekey开始点击");
                    num = 1;
                    if (handler == null) {
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (num == 1) {
                                    Log.i(LOG_TAG, "单击");
                                    MyWindowManager.removeCtrlCenterBigWindow(context);
                                } else if (num >= 2) {
                                    Log.i(LOG_TAG, "双击");
                                    MyWindowManager.createCtrlCenterBigWindow(context);
                                } else {
                                    Log.i(LOG_TAG, "三连击");
                                }
                                lastTime = System.currentTimeMillis();
                                num = 1;
                                handler = null;
                            }
                        }, 500);
                    }
                }
            } else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
                // 长按Home键 或者 activity切换键
                Log.i(LOG_TAG, "long press home key or activity switch");
                doLongPress();
            } else if (SYSTEM_DIALOG_REASON_LOCK.equals(reason)) {
                // 锁屏
                Log.i(LOG_TAG, "lock");
            } else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
                // samsung 长按Home键
                Log.i(LOG_TAG, "assist");
                doLongPress();
            } else if (SYSTEM_DIALOG_REASON_VOICE.equals(reason)) {
                //google nuex 6p 长按Home键
                doLongPress();
            }
        }
    }

    private void doLongPress() {
        if (!status) {
            status = true;
            flashLightManager.turnOn();
        } else {
            status = false;
            flashLightManager.turnOff();
        }
    }
}
