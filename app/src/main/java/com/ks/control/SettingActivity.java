package com.ks.control;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    SharedPreferences sp;
    CheckBox ckb0;
    CheckBox ckb1;
    CheckBox ckb2;
    int num = 0;
    SeekBar seekBar;
    private String title = "屏幕『底部%1$S设备像素』为感知区域";
    private TextView tv;
    int h = 3;
    CheckBox seekdefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sp = getSharedPreferences("setting", MODE_PRIVATE);

        ckb0 = (CheckBox) findViewById(R.id.ckb0);
        ckb1 = (CheckBox) findViewById(R.id.ckb1);
        ckb2 = (CheckBox) findViewById(R.id.ckb2);
        ckb0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    num = 0;
                }
                setCheckBox();
            }
        });
        ckb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    num = 1;
                }
                setCheckBox();
            }
        });
        ckb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    num = 2;
                }
                setCheckBox();
            }
        });

        tv = (TextView) findViewById(R.id.seektitle);
        seekBar = (SeekBar) findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                h = i;
                setTouchHeight();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekdefault = (CheckBox) findViewById(R.id.seekdefault);
        seekdefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    h = 0;
                    setTouchHeight();
                    seekBar.setProgress(0);
                }
            }
        });
        h = sp.getInt("height", 0);
        setTouchHeight();
        num = sp.getInt("position", 0);
        setCheckBox();
    }

    private void setCheckBox() {
        if (num == 0) {
            ckb0.setChecked(true);
            ckb1.setChecked(false);
            ckb2.setChecked(false);
        } else if (num == 1) {
            ckb0.setChecked(false);
            ckb1.setChecked(true);
            ckb2.setChecked(false);
        } else {
            ckb0.setChecked(false);
            ckb1.setChecked(false);
            ckb2.setChecked(true);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("position", num);
        editor.commit();
    }

    private void setTouchHeight() {
        tv.setText(String.format(title, h + 3));
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("height", h);
        editor.commit();
        if (h != 0) {
            seekdefault.setChecked(false);
        } else {
            seekdefault.setChecked(true);
        }
    }
}
