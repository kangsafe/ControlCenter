package com.ks.control;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Admin on 2017/6/13 0013 09:37.
 * Author: kang
 * Email: kangsafe@163.com
 */

public class FloatWindowBigView extends LinearLayout {

    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;
    private Context context;

    public FloatWindowBigView(final Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.floating_window_big, this);
        View view = findViewById(R.id.big_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        Button close = (Button) findViewById(R.id.close);
        final Button back = (Button) findViewById(R.id.back);
        ImageView camara = (ImageView) findViewById(R.id.floating_camara);
        ImageView text = (ImageView) findViewById(R.id.floating_text);
        ImageView home = (ImageView) findViewById(R.id.floating_home);
        ImageView voice = (ImageView) findViewById(R.id.floating_voice);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
                Intent intent = new Intent(getContext(), FloatWindowService.class);
                context.stopService(intent);
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        camara.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, EditDocumentActivity.class);
//                intent.putExtra(NoteActivity.FAB_ACTION, R.id.action_markdown_take_photo);
//                intent.putExtra(ExtraCode.GroupId, "");
//                context.startActivity(intent);
                back();
            }
        });
        text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, EditDocumentActivity.class);
//                intent.putExtra(ExtraCode.GroupId, "");
//                context.startActivity(intent);
            }
        });
        home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        voice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void back() {
        // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
        MyWindowManager.removeBigWindow(context);
        MyWindowManager.createSmallWindow(context);
    }
}
