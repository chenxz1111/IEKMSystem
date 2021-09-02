package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.*;
import com.google.android.material.tabs.TabLayout;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class EntityActivity extends AppCompatActivity implements View.OnClickListener{

    final String[] courses = new String[] {"chinese", "english", "math", "physics", "chemistry", "biology", "geo", "politics"};
    JSONObject entityData;
    JSONArray entityProperty;
    JSONArray entityRelation;
    JSONObject entityExam;
    private EntityViewAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView entityName;
    ImageView entityBack, entityShare;
    private final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);

        entityName = findViewById(R.id.entity_name);
        entityBack = findViewById(R.id.entity_detail_back);
        entityBack.setOnClickListener(this);
        entityShare = findViewById(R.id.entity_share);
        entityShare.setOnClickListener(this);
        tabLayout = findViewById(R.id.entity_tab);
        viewPager = findViewById(R.id.entity_viewpager);
        viewPagerAdapter = new EntityViewAdapter(getSupportFragmentManager(), EntityViewAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabLayout.getTabAt(0).select();
                        break;
                    case 1:
                        tabLayout.getTabAt(1).select();
                        break;
                    case 2:
                        tabLayout.getTabAt(2).select();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        String name = getIntent().getStringExtra("label");
        String category = getIntent().getStringExtra("category");
        String course = "chinese";
        for (String cs : courses){
            if (OpenEducation.entityDetail(cs, name).indexOf("[]") <= 0) {
                course = cs;
                break;
            }
        }
        SharedPreferences history = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> listHistory = history.getStringSet("entity_list_history", null);
        if(listHistory == null) {
            listHistory = new HashSet<String>();
        }

        if(listHistory.contains(name + ";" + category)) {
            Set<String> detailHistory = history.getStringSet("entity_detail_history", null);
            for(String d: detailHistory) {
                if(d.indexOf(name + ";" + category) == 0) {
                    String s = d.substring(name.length() + category.length() + 1);
                    entityData = JSONObject.parseObject(s);
                    Log.i("detailInfo", entityData.toString());
                    break;
                }
            }

            entityRelation = entityData.getJSONArray("content");
            entityProperty = entityData.getJSONArray("property");
            entityName.setText(entityData.getString("label"));
            entityExam = entityData.getJSONObject("exam");
        } else {
            JSONObject result = JSON.parseObject(OpenEducation.entityDetail(course, name));
            entityData = result.getJSONObject("data");
            entityRelation = entityData.getJSONArray("content");
            entityProperty = entityData.getJSONArray("property");
            entityName.setText(entityData.getString("label"));
            entityExam = JSON.parseObject(OpenEducation.entityExam(entityData.getString("label")));

            JSONObject saveInPhone = entityData;
            saveInPhone.put("exam", entityExam);
            SharedPreferences entityHistory = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = entityHistory.edit();
            Set<String> detailHistory = history.getStringSet("entity_detail_history", null);
            if(detailHistory == null) {
                detailHistory = new HashSet<>();
            }
            listHistory.add(name + ";" + category);
            detailHistory.add(name + ";" + category + saveInPhone.toString());
            editor.putStringSet("entity_list_history", listHistory);
            editor.putStringSet("entity_detail_history", detailHistory);
            Log.i("detail", saveInPhone.toString());
            editor.commit();
        }
    }



    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.entity_detail_back:
                intent = new Intent(EntityActivity.this, MainActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                break;
            case R.id.entity_share:
                intent = new Intent(EntityActivity.this, ShareActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EntityActivity.this, MainActivity.class);
        intent.putExtra("id",1);
        startActivity(intent);
        super.onBackPressed();
    }

}