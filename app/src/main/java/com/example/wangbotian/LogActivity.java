package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.content.*;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hjq.xtoast.XToast;
import com.javier.filterview.FilterView;
import com.javier.filterview.OnFilterViewResultListener;
import com.javier.filterview.single.OnSingleOptionListener;
import com.javier.filterview.single.SingleOption;
import com.javier.filterview.single.SingleSection;

import org.json.JSONArray;

import java.util.*;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    Button logButton, regButton;
    TextInputEditText usernameText, passwordText;
    TextInputLayout usernameLay, passwordLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_log);

        logButton = findViewById(R.id.log_button);
        logButton.setOnClickListener(this);

        regButton = findViewById(R.id.reg_button);
        regButton.setOnClickListener(this);

        usernameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);

        usernameLay = findViewById(R.id.username_layout);
        passwordLay = findViewById(R.id.password_layout);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.log_button:
                if (!usernameText.getText().toString().equals("admin")) {
//                    System.out.println(OpenEducation.sendGet("http://192.168.3.192:8080/test", ""));
                    Intent intent = new Intent();
                    new XToast<>(this)
                            .setDuration(1000)
                            .setView(R.layout.toast_hint)
                            .setAnimStyle(android.R.style.Animation_Activity)
                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                            .setText(android.R.id.message, "登录成功")
                            .show();
                    intent.setClass(LogActivity.this, MainActivity.class);
                    this.startActivity(intent);
                }
                else {
                    new XToast<>(this)
                            .setDuration(1000)
                            .setView(R.layout.toast_hint)
                            .setAnimStyle(android.R.style.Animation_Activity)
                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                            .setText(android.R.id.message, "登录失败")
                            .show();
                }
                break;
            case R.id.reg_button:
                Intent intent = new Intent();
                intent.setClass(LogActivity.this, RegActivity.class);
                this.startActivity(intent);
                break;
        }

    }

}