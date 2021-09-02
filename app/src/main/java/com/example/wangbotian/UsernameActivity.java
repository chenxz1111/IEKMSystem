package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Application;

import com.google.android.material.appbar.MaterialToolbar;

public class UsernameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView userText;
    MaterialToolbar top_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        userText = findViewById(R.id.change_username_text);
        userText.setText(AppApplication.getApp().getUsername());
        top_bar =  findViewById(R.id.topAppBar4);
        top_bar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.done_on_topbar:
                        AppApplication.getApp().setUsername(userText.getText().toString());
                        Intent intent = new Intent(UsernameActivity.this, SettingActivity.class);
                        startActivity(intent);
                }
                return true;
            }
        });
        top_bar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()) {
            case R.id.topAppBar4:
                intent = new Intent(UsernameActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}