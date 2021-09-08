package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wangbotian.view.*;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class RadarActivity extends AppCompatActivity {

    MaterialToolbar top_bar;
    private String [] title = {"语文","数学","英语","物理","化学","生物","历史","地理","政治"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        top_bar = findViewById(R.id.topAppBar_ra);
        top_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RadarActivity.this, MainActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });


        RadarView radarView = (RadarView) findViewById(R.id.radarView);

        List<RadarData> dataList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            RadarData data = new RadarData(title[i], 10);
            dataList.add(data);
        }
        radarView.setDataList(dataList);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RadarActivity.this, MainActivity.class);
        intent.putExtra("id",1);
        startActivity(intent);
        super.onBackPressed();
    }
}