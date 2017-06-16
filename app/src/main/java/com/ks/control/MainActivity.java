package com.ks.control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button startBtn;
    Button powerOffBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = (Button) findViewById(R.id.vstart);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWindowManager.createCtrlCenterSmallWindow(getApplicationContext());
            }
        });
        powerOffBtn = (Button) findViewById(R.id.vpoweroff);
        powerOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmPowerManager.bcShutdown(getApplicationContext());
            }
        });
        findViewById(R.id.vpanel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTips("操作方式与问题", "任意时刻底部上划即弹出面板，屏幕外亦可" +
                                "你也可以设置左、右边缘轻触启动面板；" +
                                "如未弹出，请尝试调大触摸感知区域；" +
                                "确认系统『设置-应用-权限管理』已开启『悬浮窗』等权限", "我知道了",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
        });
        findViewById(R.id.vprotect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTips("保护应用不被系统停止", "本引用仅有700K左右，内存消耗也极少；" +
                        "不会后台持续工作，不随意消耗用户电量；不会后台连接网路，不任意消耗用户流量；" +
                        "未使用黑科技保活，你来决定让它随时服务。", "查看保护方法", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                        intent.putExtra("url", "https://control.litesuits.com/");
                        getApplicationContext().startActivity(intent);
                    }
                });
            }
        });
        findViewById(R.id.vsetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });
    }

    HomeKeyEventBroadCastReceiver homeKeyEventBroadCastReceiver;

    @Override
    protected void onStart() {
        super.onStart();
//        homeKeyEventBroadCastReceiver = new HomeKeyEventBroadCastReceiver();
//        registerReceiver(homeKeyEventBroadCastReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private static HomeWatcherReceiver mHomeKeyReceiver = null;

    private static void registerHomeKeyReceiver(Context context) {
        Log.i("LOG_TAG", "registerHomeKeyReceiver");
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        context.registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    private static void unregisterHomeKeyReceiver(Context context) {
        Log.i("LOG_TAG", "unregisterHomeKeyReceiver");
        if (null != mHomeKeyReceiver) {
            context.unregisterReceiver(mHomeKeyReceiver);
        }
    }

    AlertDialog dialog;

    private void showTips(String title, String msg, String ptitle, DialogInterface.OnClickListener listener) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this).create();
        }
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, ptitle, listener);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerHomeKeyReceiver(this);
    }

    @Override
    protected void onPause() {
//        unregisterHomeKeyReceiver(this);
        super.onPause();
    }
}
