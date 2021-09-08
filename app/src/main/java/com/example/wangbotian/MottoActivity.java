package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.hjq.xtoast.XToast;

import org.w3c.dom.Text;
/**
 格言类
 */
public class MottoActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar top_bar;
    TextView user_motto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motto);
        top_bar =  findViewById(R.id.topAppBar5);
        user_motto = findViewById(R.id.change_motto);
        user_motto.setText(AppApplication.getApp().getMotto());
        top_bar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.done_on_topbar:
                        String new_motto = user_motto.getText().toString();
                        OpenEducation.sendPost("http://47.93.219.219:8080/ChangeMotto", "username=" + AppApplication.getApp().getUsername() + "&motto=" + new_motto);
                        success_hint();
                        AppApplication.getApp().setMotto(new_motto);
                        Intent intent = new Intent(MottoActivity.this, SettingActivity.class);
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
        intent = new Intent(MottoActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void success_hint() {
        new XToast<>(this)
                .setDuration(1000)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Activity)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                .setText(android.R.id.message, "更改个性签名成功")
                .show();
    }
}