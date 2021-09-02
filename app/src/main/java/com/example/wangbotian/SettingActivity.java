package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar top_bar;
    LinearLayout user_name;
    TextView setting_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        top_bar =  findViewById(R.id.topAppBar3);
        top_bar.setNavigationOnClickListener(this);
        user_name = findViewById(R.id.setting_username);
        setting_name = findViewById(R.id.setting_name);
        setting_name.setText(AppApplication.getApp().getUsername());
        user_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()) {
            case R.id.topAppBar3:
                intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
                this.finish();
                break;
            case R.id.setting_username:
                intent = new Intent(SettingActivity.this, UsernameActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}