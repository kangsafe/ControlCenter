package com.ks.control;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static android.view.WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;

/**
 * Created by Admin on 2017/6/13 0013 09:38.
 * Author: kang
 * Email: kangsafe@163.com
 */

public class MyWindowManager {

    /**
     * 小悬浮窗View的实例
     */
    private static FloatWindowSmallView smallWindow;
    /**
     * 大悬浮窗View的实例
     */
    private static FloatWindowBigView bigWindow;

    /**
     * 小控制中心
     */
    private static CtrlCenterWindowSmallView ctrlCenterWindowSmallView;
    /**
     * 大控制中心
     */
    private static CtrlCenterWindowBigView ctrlCenterWindowBigView;

    /**
     * 小悬浮窗View的参数
     */
    private static WindowManager.LayoutParams smallWindowParams;
    /**
     * 大悬浮窗View的参数
     */
    private static WindowManager.LayoutParams bigWindowParams;

    /**
     * 小控制中心View的参数
     */
    private static WindowManager.LayoutParams ctrlCenterWindowSmallParams;
    /**
     * 大控制中心View参数
     */
    private static WindowManager.LayoutParams ctrlCenterWindowBigParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    /**
     * 用于获取手机可用内存
     */
    private static ActivityManager mActivityManager;

    /**
     * 用来判断服务是否运行.
     *
     * @param mContext
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static boolean isServiceRunning(Context mContext, Class<?> cla) {
        return isServiceRunning(mContext, cla.getName());
    }

    /**
     * 启动悬浮服务
     */
    public static void startFloatingServiceByConfig() {
        try {
//            if (SPHelper.getBoolean(SPConst.FLOATING, false)) {
//                MyWindowManager.startFloatingService();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动悬浮服务
     */
    public static void startFloatingService() {
        try {
//            if (!isServiceRunning(MyApplication.getContext(), "com.zd.note.floating.FloatWindowService")) {
//                Intent intent = new Intent(MyApplication.getContext(), FloatWindowService.class);
//                MyApplication.getContext().startService(intent);
//                SPHelper.putBoolean(SPConst.FLOATING, true);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止悬浮服务
     */
    public static void stopFloatingService() {
        try {
//            if (isServiceRunning(MyApplication.getContext(), "com.zd.note.floating.FloatWindowService")) {
//                Intent intent = new Intent(MyApplication.getContext(), FloatWindowService.class);
//                MyApplication.getContext().stopService(intent);
//            }
//            SPHelper.putBoolean(SPConst.FLOATING, false);
//            removeSmallWindow(MyApplication.getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动控制中心服务
     */
    public static void startCtrlCenterWindowServiceByConfig() {
        try {
//            if (SPHelper.getBoolean(SPConst.CTRLCENTER, false)) {
//                startCtrlCenterWindowService();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动控制中心服务
     */
    public static void startCtrlCenterWindowService() {
        try {
//            if (!isServiceRunning(MyApplication.getContext(), "com.zd.note.floating.CtrlCenterWindowService")) {
//                Intent intent = new Intent(MyApplication.getContext(), CtrlCenterWindowService.class);
//                MyApplication.getContext().startService(intent);
//                SPHelper.putBoolean(SPConst.CTRLCENTER, true);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动控制中心
     */
    public static void stopCtrlCenterWindowService() {
        try {
//            if (isServiceRunning(MyApplication.getContext(), "com.zd.note.floating.CtrlCenterWindowService")) {
//                Intent intent = new Intent(MyApplication.getContext(), CtrlCenterWindowService.class);
//                MyApplication.getContext().stopService(intent);
//            }
//            SPHelper.putBoolean(SPConst.CTRLCENTER, false);
//            removeCtrlCenterSmallWindow(MyApplication.getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (smallWindow == null) {
            smallWindow = new FloatWindowSmallView(context);
            if (smallWindowParams == null) {
                smallWindowParams = new WindowManager.LayoutParams();
                smallWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                smallWindowParams.format = PixelFormat.RGBA_8888;
                smallWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                smallWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallWindowParams.width = FloatWindowSmallView.viewWidth;
                smallWindowParams.height = FloatWindowSmallView.viewHeight;
                smallWindowParams.x = screenWidth;
                smallWindowParams.y = screenHeight / 2;
            }
            smallWindow.setParams(smallWindowParams);
            windowManager.addView(smallWindow, smallWindowParams);
        }
    }

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createCtrlCenterSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        if (ctrlCenterWindowSmallView == null) {
            ctrlCenterWindowSmallView = new CtrlCenterWindowSmallView(context);
            if (ctrlCenterWindowSmallParams == null) {
                ctrlCenterWindowSmallParams = new WindowManager.LayoutParams();
                ctrlCenterWindowSmallParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                ctrlCenterWindowSmallParams.format = PixelFormat.TRANSLUCENT;
                ctrlCenterWindowSmallParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                ctrlCenterWindowSmallParams.gravity = Gravity.BOTTOM;
                ctrlCenterWindowSmallParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                ctrlCenterWindowSmallParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                ctrlCenterWindowSmallParams.x = 0;
                ctrlCenterWindowSmallParams.y = 0;
            }
            ctrlCenterWindowSmallView.setParams(ctrlCenterWindowSmallParams);
            windowManager.addView(ctrlCenterWindowSmallView, ctrlCenterWindowSmallParams);
        }
    }

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createCtrlCenterBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        if (ctrlCenterWindowBigView == null) {
            ctrlCenterWindowBigView = new CtrlCenterWindowBigView(context);
            if (ctrlCenterWindowBigParams == null) {
                ctrlCenterWindowBigParams = new WindowManager.LayoutParams();
                ctrlCenterWindowBigParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                ctrlCenterWindowBigParams.format = PixelFormat.TRANSLUCENT;
                ctrlCenterWindowBigParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|FLAG_WATCH_OUTSIDE_TOUCH;
                ctrlCenterWindowBigParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                ctrlCenterWindowBigParams.width = (int) (windowManager.getDefaultDisplay().getWidth() * 0.95);//WindowManager.LayoutParams.MATCH_PARENT;
                ctrlCenterWindowBigParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                ctrlCenterWindowBigParams.x = 0;
                ctrlCenterWindowBigParams.y = 50;
            }
            windowManager.addView(ctrlCenterWindowBigView, ctrlCenterWindowBigParams);
        }
    }


    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (smallWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallWindow);
            smallWindow = null;
        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeCtrlCenterSmallWindow(Context context) {
        if (ctrlCenterWindowSmallView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(ctrlCenterWindowSmallView);
            ctrlCenterWindowSmallView = null;
        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeCtrlCenterBigWindow(Context context) {
        if (ctrlCenterWindowBigView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(ctrlCenterWindowBigView);
            ctrlCenterWindowBigView = null;
        }
    }

    /**
     * 创建一个大悬浮窗。位置为屏幕正中间。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (bigWindow == null) {
            bigWindow = new FloatWindowBigView(context);
            if (bigWindowParams == null) {
                bigWindowParams = new WindowManager.LayoutParams();
                bigWindowParams.x = screenWidth / 2 - FloatWindowBigView.viewWidth / 2;
                bigWindowParams.y = screenHeight / 2 - FloatWindowBigView.viewHeight / 2;
                bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                bigWindowParams.format = PixelFormat.RGBA_8888;
                bigWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigWindowParams.width = FloatWindowBigView.viewWidth;
                bigWindowParams.height = FloatWindowBigView.viewHeight;
            }
            windowManager.addView(bigWindow, bigWindowParams);
        }
    }

    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeBigWindow(Context context) {
        if (bigWindow != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bigWindow);
            bigWindow = null;
        }
    }

    /**
     * 更新小悬浮窗的TextView上的数据，显示内存使用的百分比。
     *
     * @param context 可传入应用程序上下文。
     */
    public static void updateUsedPercent(Context context) {
        if (smallWindow != null) {
//            TextView percentView = (TextView) smallWindow.findViewById(R.id.percent);
//            percentView.setText(getUsedPercentValue(context));
        }
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return smallWindow != null || bigWindow != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
     *
     * @param context 可传入应用程序上下文。
     * @return ActivityManager的实例，用于获取手机可用内存。
     */
    private static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    /**
     * 计算已使用内存的百分比，并返回。
     *
     * @param context 可传入应用程序上下文。
     * @return 已使用内存的百分比，以字符串形式返回。
     */
    public static String getUsedPercentValue(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent + "%";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "悬浮窗";
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存。
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }

}
