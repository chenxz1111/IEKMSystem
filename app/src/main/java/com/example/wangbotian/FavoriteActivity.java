package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialToolbar top_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        top_bar =  findViewById(R.id.topAppBar);
        top_bar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
        intent.putExtra("id",2);
        startActivity(intent);
    }

}