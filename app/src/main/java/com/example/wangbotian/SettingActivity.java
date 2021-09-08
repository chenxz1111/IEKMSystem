package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.xiaofeidev.round.RoundImageView;
import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {

    MaterialToolbar top_bar;
    LinearLayout user_name;
    TextView setting_name;
    LinearLayout setting_motto;
    LinearLayout setting_password;
    LinearLayout setting_about;
    LinearLayout quit_btn;
    RoundImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        top_bar =  findViewById(R.id.topAppBar3);
        top_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });
        user_name = findViewById(R.id.setting_username);
        setting_name = findViewById(R.id.setting_name);
        setting_name.setText(AppApplication.getApp().getUsername());
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, UsernameActivity.class);
                startActivity(intent);
            }
        });
        setting_motto = findViewById(R.id.setting_motto);
        setting_motto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MottoActivity.class);
                startActivity(intent);
            }
        });
        setting_password = findViewById(R.id.setting_password);
        setting_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, PasswordActivity.class);
                startActivity(intent);
            }
        });
        setting_about = findViewById(R.id.setting_about);
        setting_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        quit_btn = findViewById(R.id.quit_btn);
        quit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, LogActivity.class);
                startActivity(intent);
                finish();
            }
        });
        img = findViewById(R.id.setting_avatar);
        Drawable d = getDrawable(AppApplication.getApp().getAvatar());
        img.setImageDrawable(d);
    }
}