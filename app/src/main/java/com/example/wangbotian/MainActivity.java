package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import io.github.vejei.bottomnavigationbar.BottomNavigationBar;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationBar bottomNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        //bottomNavigationBar = BottomNavigationBar.
    }

}

