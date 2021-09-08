package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

/**
 关于我们，应用开发背景及开发者信息
 */

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    MaterialToolbar top_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        top_bar = findViewById(R.id.topAppBar7);
        top_bar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        intent = new Intent(AboutActivity.this, SettingActivity.class);
        startActivity(intent);
    }
}