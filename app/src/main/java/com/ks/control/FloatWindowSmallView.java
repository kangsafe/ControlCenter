package com.ks.control;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/6/13 0013 09:36.
 * Author: kang
 * Email: kangsafe@163.com
 */
public class FloatWindowSmallView extends LinearLayout implements View.OnClickListener {

    /**
     * 记录小悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录小悬浮窗的高度
     */
    public static int viewHeight;

    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;
    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarWidth;

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager windowManager;

    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;


    private int[] res = {};//{R.id.iv_open, R.id.iv_camera, R.id.iv_music, R.id.iv_place, R.id.iv_sleep, R.id.iv_thought, R.id.iv_with};
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    private boolean isOpen = false;//菜单是否打开状态
    private final int r = 400;//扇形半径
    private float angle;//按钮之间的夹角
    private final long intervalTime = 100; //菜单展开的时间间隔

    public FloatWindowSmallView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.floating_window_small, this);
        View view = findViewById(R.id.small_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
//        TextView percentView = (TextView) findViewById(R.id.percent);
//        percentView.setText(MyWindowManager.getUsedPercentValue(context));
//        initImageView();
//        计算按钮之间的夹角
//        angle = (float) Math.PI / (2 * (res.length - 1));
        statusBarWidth = windowManager.getDefaultDisplay().getWidth();
    }

    /**
     * 初始化View
     */
    private void initImageView() {
        ImageView imageView = null;
        for (int i = 0; i < res.length; i++) {
            imageView = (ImageView) findViewById(res[i]);
            imageView.setOnClickListener(this);
            imageViewList.add(imageView);
        }
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.iv_open:
//                if (isOpen) {
//                    closeAnim();
//                } else {
//                    startAnim();
//                }
//                break;
            default:
//                Toast.makeText(MyApplication.getContext(), "" + view.getId(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 展开菜单
     */
    private void startAnim() {
        ObjectAnimator animatorX = null;
        ObjectAnimator animatorY = null;
        float translationX;//横坐标偏移距离
        float translationY;//纵坐标偏移距离
        //左上角
        if (mParams.x == 0 && mParams.y == 0) {
            for (int i = 0; i < res.length; i++) {
                translationX = (float) (r * Math.sin(i * 90 / res.length));
                translationY = (float) (r * Math.cos(i * 90 / res.length));
                animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, translationX);
                animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, translationY);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(animatorX, animatorY);
                animSet.setDuration(i * intervalTime);
                animSet.start();
            }
        }
        //上边
        if (mParams.x > r && mParams.x < statusBarWidth - r && mParams.y == 0) {
            for (int i = 0; i < res.length; i++) {
                translationX = mParams.x + (float) (r * Math.sin(i * 180 / res.length));
                translationY = (float) (r * Math.cos(i * 180 / res.length));
                animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, translationX);
                animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, translationY);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(animatorX, animatorY);
                animSet.setDuration(i * intervalTime);
                animSet.start();
            }
        }
        //右上角
        if (mParams.x == statusBarWidth && mParams.y == 0) {
            for (int i = 0; i < res.length; i++) {
                translationX = statusBarWidth - (float) (r * Math.sin(i * 90 / res.length));
                translationY = (float) (r * Math.cos(i * 90 / res.length));
                animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, translationX);
                animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, translationY);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(animatorX, animatorY);
                animSet.setDuration(i * intervalTime);
                animSet.start();
            }
        }
        //左边
        if (mParams.x == 0 && mParams.y > r && mParams.y < statusBarHeight - r) {
            for (int i = 0; i < res.length; i++) {
                translationX = (float) (r * Math.sin(i * 180 / res.length));
                translationY = mParams.y + (float) (r * Math.cos(i * 180 / res.length));
                animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, translationX);
                animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, translationY);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(animatorX, animatorY);
                animSet.setDuration(i * intervalTime);
                animSet.start();
            }
        }
        //右边
        if (mParams.x == statusBarWidth && mParams.y > r && mParams.y < statusBarHeight - r) {
            for (int i = 0; i < res.length; i++) {
                translationX = statusBarWidth - (float) (r * Math.sin(i * 180 / res.length));
                translationY = mParams.y + (float) (r * Math.cos(i * 180 / res.length));
                animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, translationX);
                animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, translationY);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(animatorX, animatorY);
                animSet.setDuration(i * intervalTime);
                animSet.start();
            }
        }
        //左下角
        if (mParams.x == 0 && mParams.y == statusBarHeight) {
            for (int i = 0; i < res.length; i++) {
                translationX = (float) (r * Math.sin(i * 90 / res.length));
                translationY = mParams.y - (float) (r * Math.cos(i * 90 / res.length));
                animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, translationX);
                animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, translationY);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(animatorX, animatorY);
                animSet.setDuration(i * intervalTime);
                animSet.start();
            }
        }
        //下边
        if (mParams.x > r && mParams.x < statusBarWidth - r && mParams.y == statusBarHeight) {
            for (int i = 0; i < res.length; i++) {
                translationX = mParams.x + (float) (r * Math.sin(i * 180 / res.length));
                translationY = statusBarHeight - (float) (r * Math.cos(i * 180 / res.length));
                animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, translationX);
                animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, translationY);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(animatorX, animatorY);
                animSet.setDuration(i * intervalTime);
                animSet.start();
            }
        }
        //右下角
        if (mParams.x == statusBarWidth && mParams.y == statusBarHeight) {
            for (int i = 0; i < res.length; i++) {
                translationX = mParams.x - (float) (r * Math.sin(i * 90 / res.length));
                translationY = statusBarHeight - (float) (r * Math.cos(i * 90 / res.length));
                animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, translationX);
                animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, translationY);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(animatorX, animatorY);
                animSet.setDuration(i * intervalTime);
                animSet.start();
            }
        }
//        for (int i = 1; i < res.length; i++) {
//            translationX = (float) (r * Math.sin(i * angle));
//            translationY = (float) (r * Math.cos(i * angle));
//            animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", 0F, translationX);
//            animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, translationY);
//            AnimatorSet animSet = new AnimatorSet();
//            animSet.playTogether(animatorX, animatorY);
//            animSet.setDuration(i * intervalTime);
//            animSet.start();
//        }
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    private void closeAnim() {
        ObjectAnimator animatorX = null;
        ObjectAnimator animatorY = null;
        float translationX;//横坐标偏移距离
        float translationY;//纵坐标偏移距离
        for (int i = res.length - 1; i > 0; i--) {
            translationX = (float) (r * Math.sin(i * angle));
            translationY = (float) (r * Math.cos(i * angle));
            animatorX = ObjectAnimator.ofFloat(imageViewList.get(i), "translationX", translationX, 0F);
            animatorY = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", translationY, 0F);
            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(animatorX, animatorY);
            animSet.setDuration((res.length - i) * intervalTime);
            animSet.start();
        }
        isOpen = false;
    }

    /**
     * 监听是否按下返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下返回键，执行退出操作
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出弹框
     */
    private void showDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("退出")
                .setMessage("要退出么？")
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//                        finish();
                    }
                })
                .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (Math.abs(xDownInScreen - xInScreen) < 5 && Math.abs(yDownInScreen - yInScreen) < 5) {
                    openBigWindow();
                } else {
                    if ((xInScreen - xInView) > statusBarWidth / 2) {
                        mParams.x = statusBarWidth;
                        mParams.y = (int) (yInScreen - yInView);
                    } else {
                        mParams.x = 0;
                        mParams.y = (int) (yInScreen - yInView);
                    }
                    windowManager.updateViewLayout(this, mParams);
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
//        if (xInScreen > getStatusBarWidth() / 2) {
//            mParams.x = getStatusBarWidth();
//            mParams.y = (int) (yInScreen - yInView);
//        } else {
//            mParams.x = 0;
//            mParams.y = (int) (yInScreen - yInView);
//        }
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 打开大悬浮窗，同时关闭小悬浮窗。
     */
    private void openBigWindow() {
        MyWindowManager.createBigWindow(getContext());
        MyWindowManager.removeSmallWindow(getContext());
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

//    private void initFloatingActionsMenu(View view) {
//        // 添加 右下角的白色+号按钮
//        final ImageView fabIcon = new ImageView(getContext());
//        fabIcon.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.ic_action_more));
//        final FloatingActionButton fabButton = new FloatingActionButton.Builder(getActivity())
//                .setContentView(fabIcon)
//                .setPosition(FloatingActionButton.POSITION_BOTTOM_LEFT)
//                .build();
//
//        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(getActivity());
//
//        ImageView imageViewQuit = new ImageView(getContext());
//        ImageView imageViewTool = new ImageView(getContext());
//        ImageView imageViewPalette = new ImageView(getContext());
//        ImageView imageViewCamera = new ImageView(getContext());
//        imageViewQuit.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.icon_fab_take_photo));
//        imageViewTool.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.icon_fab_new_note));
//        imageViewPalette.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.icon_fab_markdown));
//        imageViewCamera.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.icon_fab_finger_paint));
//
//        SubActionButton buttonQuit = rLSubBuilder.setContentView(imageViewQuit).build();
//        SubActionButton buttonPalette = rLSubBuilder.setContentView(imageViewPalette).build();
//        SubActionButton buttonTool = rLSubBuilder.setContentView(imageViewTool).build();
//        SubActionButton buttonCamera = rLSubBuilder.setContentView(imageViewCamera).build();
//
//        // Build the menu with default options: light theme, 90 degrees, 72dp
//        // radius.
//        // Set 4 default SubActionButtons
//        // FloatingActionMenu通过attachTo(fabButton)附着到FloatingActionButton
//        final FloatingActionMenu buttonToolMenu = new FloatingActionMenu.Builder(getActivity())
//                .addSubActionView(buttonPalette)
//                .addSubActionView(buttonCamera)
//                .addSubActionView(buttonTool)
//                .addSubActionView(buttonQuit)
//                .setStartAngle(0)
//                .setEndAngle(-90)
//                .attachTo(fabButton)
//                .build();
//
//        // Listen menu open and close events to animate the button content view
//        buttonToolMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
//            @Override
//            public void onMenuOpened(FloatingActionMenu menu) {
//                // 增加按钮中的+号图标顺时针旋转45度
//                // Rotate the icon of fabButton 45 degrees clockwise
//                fabIcon.setRotation(0);
//                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
//                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
//                animation.start();
//            }
//
//            @Override
//            public void onMenuClosed(FloatingActionMenu menu) {
//                // 增加按钮中的+号图标逆时针旋转45度
//                // Rotate the icon of fabButton 45 degrees
//                // counter-clockwise
//                fabIcon.setRotation(45);
//                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
//                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIcon, pvhR);
//                animation.start();
//            }
//        });
//
//        RxView.clicks(buttonQuit)
//                .throttleFirst(1, TimeUnit.SECONDS)
//                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
//                .subscribe(v -> {
//                    Voip.getInstance().hangUpCall(callId);
//                    finishActivity();
//                });
//
//        RxView.clicks(buttonPalette)
//                .throttleFirst(1, TimeUnit.SECONDS)
//                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
//                .subscribe(v -> {
//                    buttonToolMenu.close(true);
////                    buttonToolMenu.collapse();
//                    dialogPalette.show();
//                });
//
//        RxView.clicks(buttonCamera)
//                .throttleFirst(1, TimeUnit.SECONDS)
//                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
//                .subscribe(v -> {
//                    buttonToolMenu.close(true);
////                    buttonToolMenu.collapse();
//                    dialogSelectImage.show();
//                });
//    }
}