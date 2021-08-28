package com.example.wangbotian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchResult extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        Intent intent = getIntent();
        int searchNum = Integer.parseInt(intent.getStringExtra("search_num"));
        String[] labels = intent.getStringArrayExtra("search_labels");
        String[] categories = intent.getStringArrayExtra("search_categories");
        listView = findViewById(R.id.list_view);

        EntityItem[] items = new EntityItem[searchNum];
        for(int i = 0; i < searchNum; i++) {
            items[i] = new EntityItem(labels[i], categories[i]);
        }
        ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(items));
        EntityListAdapter adapter = new EntityListAdapter(this, items_list);
        this.listView.setDivider(null);
        this.listView.setAdapter(adapter);
        this.listView.setClickable(true);
    }
}
