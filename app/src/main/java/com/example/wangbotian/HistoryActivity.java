package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialToolbar top_bar;
    ListView listView;
    private final String PREFS_NAME = "MyPrefsFile";
    EntityItem[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        top_bar =  findViewById(R.id.topAppBar1);
        top_bar.setNavigationOnClickListener(this);

        listView = findViewById(R.id.list_view);

        SharedPreferences history = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> listHistory = history.getStringSet("entity_list_history", null);
        if(listHistory != null) {
            items = new EntityItem[listHistory.size()];
            int i = 0;
            for(String s : listHistory) {
                int flag = s.indexOf(";");
                String label = s.substring(0, flag);
                String category = s.substring(flag + 1);
                items[i] = new EntityItem(label, category);
                items[i].access();
                i++;
            }

            ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(items));
            EntityListAdapter adapter = new EntityListAdapter(this, items_list);
            this.listView.setDivider(null);
            this.listView.setAdapter(adapter);
            this.listView.setClickable(true);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
        intent.putExtra("id",2);
        startActivity(intent);
    }
}