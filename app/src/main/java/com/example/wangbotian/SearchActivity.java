package com.example.wangbotian;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncListDiffer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.animsh.materialsearchbar.MaterialSearchBar;
import com.hjq.xtoast.XToast;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {
    private NiceSpinner spinner;
    MaterialSearchBar searchBar;
    TextView textView;
    String subject;
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.searchBar);
        searchBar.openSearch();
        spinner = findViewById(R.id.nice_spinner);
        List<String> subjects = new LinkedList<>(Arrays.asList("语文","数学","英语","物理","化学","生物","历史","地理","政治"));
        spinner.attachDataSource(subjects);
        this.subject = subjects.get(0);
        searchBar.setOnSearchActionListener(this);
        listView = findViewById(R.id.list_view);
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        if(enabled == false) {
            Intent intent = new Intent();
            intent.setClass(SearchActivity.this, MainActivity.class);
            SearchActivity.this.startActivity(intent);
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence searchKey) {
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                subject = (String)parent.getItemAtPosition(position);
            }
        });
        String result = OpenEducation.entitySearch(convertC2E(subject), searchKey.toString());
        Log.d("info", result);
        JSONObject resultJson = JSON.parseObject(result);
        JSONArray dataArray = JSON.parseArray(resultJson.getString("data"));
        String[] labels = new String[dataArray.size()];
        for(int i = 0; i < dataArray.size(); i++) {
            JSONObject dataJson = dataArray.getJSONObject(i);
            result = dataJson.getString("label");
            labels[i] = result;
            String kind = dataJson.getString("category");

        }
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,labels);
        this.listView.setAdapter(arrayAdapter);
        this.listView.setClickable(true);

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_BACK:
                break;
        }
    }

    public String convertC2E(String subject) {
        String course = "";
        switch (subject) {
            case "语文":
                course = "chinese";
                break;
            case "数学":
                course = "math";
                break;
            case "英语":
                course = "english";
                break;
            case "物理":
                course = "physics";
                break;
            case "化学":
                course = "chemistry";
                break;
            case "生物":
                course = "biology";
                break;
            case "政治":
                course = "politics";
                break;
            case "历史":
                course = "history";
                break;
            case "地理":
                course = "geo";
                break;
            default:
                break;
        }
        return course;
    }
}