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
import com.hjq.xtoast.XToast;

public class UsernameActivity extends AppCompatActivity implements View.OnClickListener{

    TextView userText;
    MaterialToolbar top_bar;
    String old_name;
    String new_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        userText = findViewById(R.id.change_username_text);
        old_name = AppApplication.getApp().getUsername();
        userText.setText(old_name);
        top_bar =  findViewById(R.id.topAppBar4);
        top_bar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.done_on_topbar:
                        new_name = userText.getText().toString();
                        String msg = OpenEducation.sendPost("http://192.168.3.192:8080/CheckUser", "username=" + new_name);
                        handleMsg(msg);
                }
                return true;
            }
        });
        top_bar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        intent = new Intent(UsernameActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void handleMsg(String msg){
        System.out.println(msg);
        if(msg.equals("1")){
            new XToast<>(this)
                    .setDuration(1000)
                    .setView(R.layout.toast_hint)
                    .setAnimStyle(android.R.style.Animation_Activity)
                    .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                    .setText(android.R.id.message, "用户名已存在")
                    .show();
            return;
        }else{
            System.out.println(OpenEducation.sendPost("http://192.168.3.192:8080/ChangeId", "username=" + old_name + "&newname=" + new_name));
            new XToast<>(this)
                    .setDuration(1000)
                    .setView(R.layout.toast_hint)
                    .setAnimStyle(android.R.style.Animation_Activity)
                    .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                    .setText(android.R.id.message, "更改用户名成功")
                    .show();
            AppApplication.getApp().setUsername(new_name);
            Intent intent = new Intent(UsernameActivity.this, SettingActivity.class);
            startActivity(intent);
        }
    }
}