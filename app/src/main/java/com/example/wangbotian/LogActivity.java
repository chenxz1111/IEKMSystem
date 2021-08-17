package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    Button logButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        logButton = findViewById(R.id.log_button);
        logButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(LogActivity.this, MainActivity.class);
        this.startActivity(intent);
    }
}