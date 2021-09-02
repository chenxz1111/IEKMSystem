package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import org.w3c.dom.Text;

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
                        AppApplication.getApp().setMotto(user_motto.getText().toString());
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
}