package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hjq.xtoast.XToast;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialToolbar top_bar;
    private String old_password;
    private String new_password;
    private String confirm_password;
    private TextInputLayout old_password_text;
    private TextInputLayout new_password_text;
    private TextInputLayout confirm_password_text;
    Button confirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        top_bar = findViewById(R.id.topAppBar6);
        top_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(PasswordActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        confirm_btn = findViewById(R.id.okay_button);
        confirm_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.okay_button:
                old_password_text = findViewById(R.id.old_password);
                new_password_text = findViewById(R.id.new_password);
                confirm_password_text = findViewById(R.id.confirm_password);
                old_password = old_password_text.getEditText().getText().toString();
                new_password = new_password_text.getEditText().getText().toString();
                confirm_password = confirm_password_text.getEditText().getText().toString();
                System.out.println(AppApplication.getApp().getPassword());
                System.out.println(old_password);
                if(! old_password.equals(AppApplication.getApp().getPassword())){
                    new XToast<>(this)
                            .setDuration(1000)
                            .setView(R.layout.toast_hint)
                            .setAnimStyle(android.R.style.Animation_Activity)
                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                            .setText(android.R.id.message, "原密码错误")
                            .show();
                }else if(! confirm_password.equals(new_password)){
                    new XToast<>(this)
                            .setDuration(1000)
                            .setView(R.layout.toast_hint)
                            .setAnimStyle(android.R.style.Animation_Activity)
                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                            .setText(android.R.id.message, "确认密码错误")
                            .show();
                }else{
                    AppApplication.getApp().setPassword(new_password);
                    Intent intent = new Intent();
                    new XToast<>(this)
                            .setDuration(1000)
                            .setView(R.layout.toast_hint)
                            .setAnimStyle(android.R.style.Animation_Activity)
                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                            .setText(android.R.id.message, "密码修改成功")
                            .show();
                    intent.setClass(PasswordActivity.this, SettingActivity.class);
                    this.startActivity(intent);
                }
        }
    }
}