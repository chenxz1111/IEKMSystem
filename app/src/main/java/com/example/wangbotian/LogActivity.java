package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.hjq.xtoast.XToast;


public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    Button logButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        logButton = findViewById(R.id.log_button);
        logButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        new XToast<>(this)
                .setDuration(1000)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Activity)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                .setText(android.R.id.message, "login successfully")
                .show();
        intent.setClass(LogActivity.this, MainActivity.class);
        this.startActivity(intent);
    }
}