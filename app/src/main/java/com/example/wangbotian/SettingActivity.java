package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar top_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        top_bar =  findViewById(R.id.topAppBar3);
        top_bar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        intent.putExtra("id",2);
        startActivity(intent);
    }
}