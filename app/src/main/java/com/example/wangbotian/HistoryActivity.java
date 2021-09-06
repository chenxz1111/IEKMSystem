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
import java.util.Set;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialToolbar top_bar;
    ListView listView;
    EntityItem[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        top_bar =  findViewById(R.id.topAppBar1);
        top_bar.setNavigationOnClickListener(this);

        listView = findViewById(R.id.list_view);

        String userName = AppApplication.getApp().getUsername();
        String param = "username=" + userName;
        try {
            String msg = OpenEducation.sendPost("http://192.168.3.192:8080/CatchHistory", param);
            JSONArray userHistory = JSONArray.parseArray(msg);
            Log.i("history", msg);
            if(userHistory != null) {
                items = new EntityItem[userHistory.size()];
                for (int i = 0; i < userHistory.size(); i++) {
                    JSONObject dataJson = userHistory.getJSONObject(i);
                    String label = dataJson.getString("label");
                    String cate = dataJson.getString("category");
                    items[i] = new EntityItem(label,cate);
                    items[i].access();
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
        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
        intent.putExtra("id",2);
        startActivity(intent);
    }
}