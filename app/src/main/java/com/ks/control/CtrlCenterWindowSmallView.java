package com.ks.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Admin on 2017/6/13 0013 09:36.
 * Author: kang
 * Email: kangsafe@163.com
 */
public class CtrlCenterWindowSmallView extends LinearLayout {

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager windowManager;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;

    public CtrlCenterWindowSmallView(final Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.floating_ctrl_center_window_small, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                yDownInScreen = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                yInScreen = event.getRawY();
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (yDownInScreen - yInScreen > 5) {
                    openBigWindow();
                }
                yDownInScreen = yInScreen;
                updateViewPosition();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 打开大悬浮窗，同时关闭小悬浮窗。
     */
    private void openBigWindow() {
        MyWindowManager.removeCtrlCenterSmallWindow(getContext());
        MyWindowManager.createCtrlCenterBigWindow(getContext());
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.x = 0;
        mParams.y = (int) (yDownInScreen - yInScreen);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }
}