package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.hjq.xtoast.XToast;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    Button regButton;
    TextInputEditText usernameText, passwordText, checkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        regButton = findViewById(R.id.reg_button);
        regButton.setOnClickListener(this);

        usernameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);
        checkText = findViewById(R.id.check_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_button:
                if (!usernameText.getText().toString().equals("") && passwordText.getText().toString().equals(checkText.getText().toString())) {
                    Intent intent = new Intent();
                    new XToast<>(this)
                            .setDuration(1000)
                            .setView(R.layout.toast_hint)
                            .setAnimStyle(android.R.style.Animation_Activity)
                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                            .setText(android.R.id.message, "注册成功")
                            .show();
                    intent.setClass(RegActivity.this, LogActivity.class);
                    this.startActivity(intent);
                }
                else if (usernameText.getText().toString().equals("")){
                    new XToast<>(this)
                            .setDuration(1000)
                            .setView(R.layout.toast_hint)
                            .setAnimStyle(android.R.style.Animation_Activity)
                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                            .setText(android.R.id.message, "用户名不合法")
                            .show();
                }
                else {
                    new XToast<>(this)
                            .setDuration(1000)
                            .setView(R.layout.toast_hint)
                            .setAnimStyle(android.R.style.Animation_Activity)
                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                            .setText(android.R.id.message, "密码不一致")
                            .show();
                }
                break;
        }

    }
}