package com.example.wangbotian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 实体搜索类
 */
public class SearchActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener, View.OnClickListener {
    private NiceSpinner spinner;
    MaterialSearchBar searchBar;
    String subject = "语文";
    protected ListView listView;
    private final String PREFS_NAME = "MyPrefsFile";
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        subject = "语文";
        searchBar = findViewById(R.id.searchBar);
        searchBar.openSearch();
        spinner = findViewById(R.id.nice_spinner);
        List<String> subjects = new LinkedList<>(Arrays.asList("语文","数学","英语","物理","化学","生物","历史","地理","政治"));
        spinner.attachDataSource(subjects);
        this.subject = subjects.get(0);
        searchBar.setOnSearchActionListener(this);
        listView = findViewById(R.id.list_view);
        searchBar.setMaxSuggestionCount(1000);
        searchBar.setHint("实体检索");
        button = findViewById(R.id.textButton);
        button.setOnClickListener(this);
        try {
            searchBar.setLastSuggestions(getHistory());
        } catch (Exception e) {}

        try {
            Intent intent = getIntent();
            subject = intent.getStringExtra("search_course");
            String searchKey = intent.getStringExtra("search_key");
            spinner.setSelectedIndex(subjects.indexOf(subject));
            searchBar.setText(searchKey);

        } catch (Exception e) {}

        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                subject = (String)parent.getItemAtPosition(position);
            }
        });
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        if(enabled == false) {
            Intent intent = new Intent();
            intent.setClass(SearchActivity.this, MainActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence searchKey) {

        try{
            if(subject == null) {
                subject = "语文";
            }
            String result = OpenEducation.entitySearch(convertC2E(subject), searchKey.toString());

            Log.d("data_ret", result);
            JSONObject resultJson = JSON.parseObject(result);
            JSONArray dataArray = JSON.parseArray(resultJson.getString("data"));
            String[] labels = new String[dataArray.size()];
            String[] categories = new String[dataArray.size()];

            for(int i = 0; i < dataArray.size(); i++) {
                JSONObject dataJson = dataArray.getJSONObject(i);
                labels[i] = dataJson.getString("label");
                categories[i] = dataJson.getString("category");
            }

            Intent intent = new Intent();
            intent.setClass(SearchActivity.this, SearchResult.class);
            intent.putExtra("search_num", ""+dataArray.size());
            intent.putExtra("search_labels", labels);
            intent.putExtra("search_categories", categories);
            intent.putExtra("search_key", searchKey.toString());
            intent.putExtra("search_course", subject);

            this.startActivity(intent);
            this.finish();
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

    private List<String> getHistory() {
        SharedPreferences history = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> searchHistory = history.getStringSet("history", null);
        List<String> historyList = new ArrayList<>(searchHistory);
        Log.d("history_num", ""+historyList.size());
        return  historyList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save last queries to disk
        SharedPreferences history = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = history.edit();
        Set<String> searchHistory = new LinkedHashSet<>(searchBar.getLastSuggestions());
        editor.putStringSet("history", searchHistory);
        editor.commit();
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_BACK:
                break;
        }
    }

    public static String convertC2E(String subject) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textButton:
                searchBar.clearSuggestions();
                break;
        }

    }
}