package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.appbar.MaterialToolbar;
import com.hjq.xtoast.XToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 收藏功能类
 */
public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialToolbar top_bar;
    ListView listView;
    EntityItem[] items;
    private final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        top_bar =  findViewById(R.id.topAppBarcxz);
        top_bar.setNavigationOnClickListener(this);


        listView = findViewById(R.id.list_view);

        String userName = AppApplication.getApp().getUsername();
        String param = "username=" + userName;
        try {
            SharedPreferences history = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            Set<String> listHistory = history.getStringSet("entity_list_history", null);
            if(listHistory == null) {
                listHistory = new HashSet<String>();
            }
            String msg = OpenEducation.sendPost("http://47.93.219.219:8080/CatchFavor", param);
            JSONArray userHistory = JSONArray.parseArray(msg);
            Log.i("history", msg);
            if(userHistory != null) {
                items = new EntityItem[userHistory.size()];
                for (int i = 0; i < userHistory.size(); i++) {
                    JSONObject dataJson = userHistory.getJSONObject(i);
                    String label = dataJson.getString("label");
                    String cate = dataJson.getString("category");
                    items[i] = new EntityItem(label,cate);
                    // items[i].access();
                    if(listHistory.contains(label + ";" + cate)) {
                        items[i].access();
                    }
                }
                ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(items));
                EntityListAdapter adapter = new EntityListAdapter(this, items_list);
                this.listView.setDivider(null);
                this.listView.setAdapter(adapter);
                this.listView.setClickable(true);
            }
        } catch (Exception e) {
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
    public void onClick(View view) {
        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
        intent.putExtra("id",2);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
        intent.putExtra("id",2);
        startActivity(intent);
        super.onBackPressed();
    }
}