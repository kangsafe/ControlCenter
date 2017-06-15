package com.ks.control;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by Admin on 2017/6/13 0013 09:36.
 * Author: kang
 * Email: kangsafe@163.com
 */
public class CtrlCenterWindowBigView extends LinearLayout {
    View big;

    public CtrlCenterWindowBigView(final Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.floating_ctrl_center_window_big, this);
        big = view.findViewById(R.id.ctrl_center_big_container);
        big.setAnimation(AnimationUtil.moveToViewLocation());
        //拍照
        findViewById(R.id.floating_camara).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, EditDocumentActivity.class);
//                intent.putExtra(NoteActivity.FAB_ACTION, R.id.action_markdown_take_photo);
//                intent.putExtra(ExtraCode.GroupId, "");
//                context.startActivity(intent);
                back();
            }
        });
        //文本
        findViewById(R.id.floating_text).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, EditDocumentActivity.class);
//                intent.putExtra(ExtraCode.GroupId, "");
//                context.startActivity(intent);
                back();
            }
        });
        //语音
        findViewById(R.id.floating_voice).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void back() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(big, "translationY", 0, big.getHeight());
        animator.setDuration(500);
        animator.start();
        animator.setInterpolator(AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.linear));
//        big.setAnimation(animator);
//        big.setAnimation(AnimationUtil.moveToViewBottom());
//        big.setVisibility(GONE);
//        ViewHelper.setTranslationY(big, big.getHeight());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyWindowManager.removeCtrlCenterBigWindow(getContext());
                MyWindowManager.createCtrlCenterSmallWindow(getContext());
            }
        }, 500);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i("Key", big.getVisibility() + ":" + event.getAction() + ":" + event.getKeyCode());
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            back();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("Key", "Visiblity:" + big.getVisibility() + ",Action:" + event.getAction() +
                ",RawX:" + event.getRawX() + ",RawY:" + event.getRawY() + ",X:" + event.getX() + ",Y:" + event.getY());
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        // Notify touch outside listener if user tapped outside a given view
//            Rect viewRect = new Rect();
//            big.getGlobalVisibleRect(viewRect);
//            if (!viewRect.contains((int) event.getRawX(), (int) event.getRawY())) {
//                back();
//            }
//        }
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            back();
            return true;
        }
        return super.dispatchTouchEvent(event);
    }
//
//    public void onAttachedToWindow() {
//        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
//        super.onAttachedToWindow();
//    }

//    public void setOnTouchOutsideViewListener(View view, OnTouchOutsideViewListener onTouchOutsideViewListener) {
//        mTouchOutsideView = view;
//        mOnTouchOutsideViewListener = onTouchOutsideViewListener;
//    }
//
//    public OnTouchOutsideViewListener getOnTouchOutsideViewListener() {
//        return mOnTouchOutsideViewListener;
//    }

//    @Override
//    public boolean dispatchTouchEvent(final MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            // Notify touch outside listener if user tapped outside a given view
//            if (mOnTouchOutsideViewListener != null && mTouchOutsideView != null
//                    && mTouchOutsideView.getVisibility() == View.VISIBLE) {
//                Rect viewRect = new Rect();
//                mTouchOutsideView.getGlobalVisibleRect(viewRect);
//                if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
//                    mOnTouchOutsideViewListener.onTouchOutside(mTouchOutsideView, ev);
//                }
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }

//    /**
//     * Interface definition for a callback to be invoked when a touch event has occurred outside a formerly specified
//     * view. See {@link #setOnTouchOutsideViewListener(View, OnTouchOutsideViewListener).}
//     */
//    public interface OnTouchOutsideViewListener {
//
//        /**
//         * Called when a touch event has occurred outside a given view.
//         *
//         * @param view  The view that has not been touched.
//         * @param event The MotionEvent object containing full information about the event.
//         */
//        void onTouchOutside(View view, MotionEvent event);
//    }
}