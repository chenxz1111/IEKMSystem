package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.content.*;
import android.widget.EditText;

import com.hjq.xtoast.XToast;

import java.util.*;

public class LogActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    Button logButton;
    EditText userText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        logButton = findViewById(R.id.log_button);
        logButton.setOnClickListener(this);

        userText = findViewById(R.id.user_txv);
        userText.setFocusable(true);
        userText.setFocusableInTouchMode(true);

        passwordText = findViewById(R.id.password_txv);
        passwordText.setFocusable(true);
        passwordText.setFocusableInTouchMode(true);

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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}