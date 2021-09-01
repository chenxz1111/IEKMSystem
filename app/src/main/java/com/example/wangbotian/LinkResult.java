package com.example.wangbotian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class LinkResult extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    Button backToSearch;
    String course;
    String searchKey = "";
    String[] labels;
    String[] categories;
    EntityItem[] items;
//    int[] startIndex;
//    int[] endIndex;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_result);

        Intent intent = getIntent();
        int searchNum = Integer.parseInt(intent.getStringExtra("search_num"));
        items = new EntityItem[searchNum];
        labels = intent.getStringArrayExtra("search_labels");
        categories = intent.getStringArrayExtra("search_categories");
        course = intent.getStringExtra("search_course");
        searchKey = intent.getStringExtra("search_key");
        content = findViewById(R.id.contextAgain);
//        startIndex = intent.getIntArrayExtra("start_index");
//        endIndex = intent.getIntArrayExtra("end_index");
        backToSearch = findViewById(R.id.textButton);
        backToSearch.setOnClickListener(this);

        SpannableStringBuilder builder = new SpannableStringBuilder(searchKey);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FAFA02"));
        for(int i = 0; i < searchNum; i++) {
            items[i] = new EntityItem(labels[i], categories[i]);
            int startIndex = searchKey.indexOf(labels[i]);
            int endIndex = startIndex + labels[i].length();
            Log.i(""+startIndex, "" + endIndex);
            builder.setSpan(CharacterStyle.wrap(colorSpan), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        content.setText(builder);

        listView = findViewById(R.id.list_view);
        ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(items));
        EntityListAdapter adapter = new EntityListAdapter(this, items_list);
        this.listView.setDivider(null);
        this.listView.setAdapter(adapter);
        this.listView.setClickable(true);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textButton:
                Intent intent = new Intent();
                intent.setClass(LinkResult.this, LinkActivity.class);
                intent.putExtra("search_key", searchKey);
                intent.putExtra("search_course", course);
                this.startActivity(intent);
                this.finish();
                break;
        }

    }
}
