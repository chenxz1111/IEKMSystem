package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.*;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.hjq.xtoast.XToast;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class EntityActivity extends AppCompatActivity{

    final String[] courses = new String[] {"chinese", "english", "math", "physics", "chemistry", "biology", "geo", "politics"};
    JSONObject entityData;
    JSONArray entityProperty;
    JSONArray entityRelation;
    JSONObject entityExam;
    private EntityViewAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView entityName;
    private final String PREFS_NAME = "MyPrefsFile";
    private MaterialToolbar toolbar;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity);
        String name = getIntent().getStringExtra("label");
        String category = getIntent().getStringExtra("category");
        toolbar = findViewById(R.id.AppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent = new Intent(EntityActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent();
                switch (menuItem.getItemId()) {
                    case R.id.share_on_topbar:
                        intent = new Intent(EntityActivity.this, ShareActivity.class);
                        intent.putExtra("share_text", entityData.toString());
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        entityName = findViewById(R.id.entity_name);
        toolbar = findViewById(R.id.topAppBar);
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
        button = findViewById(R.id.floating_action_button);

        String userName = AppApplication.getApp().getUsername();
        String param = "username=" + userName;
        try {
            String msg = OpenEducation.sendPost("http://47.93.219.219:8080/CatchFavor", param);
            JSONArray userFavor = JSONArray.parseArray(msg);
            Log.i("history", msg);
            Boolean hasFavor = false;
            for(int i = 0; i < userFavor.size(); i++) {
                JSONObject dataJson = userFavor.getJSONObject(i);
                String label = dataJson.getString("label");
                String cate = dataJson.getString("category");
                if(label.equals(name) && cate.equals(category)) {
                    hasFavor = true;
                    break;
                }
            }
            if(hasFavor) {
                button.setImageDrawable(getDrawable(R.drawable.baseline_favorite_white_24));
            } else {
                button.setImageDrawable(getDrawable(R.drawable.outline_favorite_border_white_24));
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userName = AppApplication.getApp().getUsername();
                    String param = "username=" + userName + "&label=" + name + "&category=" + category;
                    String msg = OpenEducation.sendPost("http://47.93.219.219:8080/ChangeFavor", param);
                    Log.i("msg", msg);
                    if(msg.equals("1")) {
                        button.setImageDrawable(getDrawable(R.drawable.baseline_favorite_white_24));

                    } else {
                        button.setImageDrawable(getDrawable(R.drawable.outline_favorite_border_white_24));
                    }
                }
            });



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
                Log.i("history", "");
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

            userName = AppApplication.getApp().getUsername();
            param = "username=" + userName;
            msg = OpenEducation.sendPost("http://47.93.219.219:8080/CatchHistory", param);
            JSONArray userHistory = JSONArray.parseArray(msg);
            Log.i("history", msg);
            Boolean hasExist = false;
            for(int i = 0; i < userHistory.size(); i++) {
                JSONObject dataJson = userHistory.getJSONObject(i);
                String label = dataJson.getString("label");
                String cate = dataJson.getString("category");
                if(label.equals(name) && cate.equals(category)) {
                    hasExist = true;
                    break;
                }
            }
            if(!hasExist) {
                param = "username=" + userName + "&label=" + name + "&category=" + category;
                msg = OpenEducation.sendPost("http://47.93.219.219:8080/AddHistory", param);
            }
        }catch (Exception e) {
            Log.d("debug", e.toString());
            new XToast<>(this)
                    .setDuration(1000)
                    .setView(R.layout.toast_hint)
                    .setAnimStyle(android.R.style.Animation_Activity)
                    .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                    .setText(android.R.id.message, "无网络")
                    .show();
        }

    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EntityActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}