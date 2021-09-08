package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.example.wangbotian.view.*;
import com.google.android.material.appbar.MaterialToolbar;
import com.hjq.xtoast.XToast;

import java.util.ArrayList;
import java.util.List;

public class RadarActivity extends AppCompatActivity {

    MaterialToolbar top_bar;
    private String [] title = {"探索","钻研","实践"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        top_bar = findViewById(R.id.topAppBar_ra);
        top_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RadarActivity.this, MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });

        try {
            RadarView radarView = (RadarView) findViewById(R.id.radarView);

            List<RadarData> dataList = new ArrayList<>();
            int size[] = new int[3];

            size[0] = JSONArray.parseArray(OpenEducation.sendPost("http://47.93.219.219:8080/CatchHistory", "username=" + AppApplication.getApp().getUsername())).size() + 1;
            size[1] = JSONArray.parseArray(OpenEducation.sendPost("http://47.93.219.219:8080/CatchFavor", "username=" + AppApplication.getApp().getUsername())).size() + 1;
            size[2] = JSONArray.parseArray(OpenEducation.sendPost("http://47.93.219.219:8080/CatchMistake", "username=" + AppApplication.getApp().getUsername())).size() + 1;
            for (int i = 0; i < 3; i++) {
                RadarData data = new RadarData(title[i], size[i] * 100 / (size[0] + size[1] + size[2]));
                dataList.add(data);
            }
            radarView.setDataList(dataList);
        } catch (Exception e) {
            Log.d("debug", e.toString());
            new XToast<>(this)
                    .setDuration(1000)
                    .setView(R.layout.toast_hint)
                    .setAnimStyle(android.R.style.Animation_Activity)
                    .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                    .setText(android.R.id.message, "无网络或接口失效")
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RadarActivity.this, MainActivity.class);
        intent.putExtra("id",1);
        startActivity(intent);
        super.onBackPressed();
    }
}