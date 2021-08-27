package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;
import com.alibaba.fastjson.*;
import android.os.Bundle;
import android.view.KeyEvent;

public class EntityActivity extends AppCompatActivity {

    private JSONObject entityDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);
    }

    @Override
    protected void onResume() {
        String name = getIntent().getStringExtra("name");
        String course = getIntent().getStringExtra("course");
        OpenEducation.entityDetail(course, name);

        super.onResume();
    }

}