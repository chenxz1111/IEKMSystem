package com.example.wangbotian;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.animsh.materialsearchbar.MaterialSearchBar;
import com.hjq.xtoast.XToast;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {
    private NiceSpinner niceSpinner;
    MaterialSearchBar searchBar;
    private List<String> lastSearches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.searchBar);
        searchBar.openSearch();
        niceSpinner = findViewById(R.id.nice_spinner);
        List<String> subject = new LinkedList<>(Arrays.asList("语文","数学","英语","物理","化学","生物","历史","地理","政治"));
        niceSpinner.attachDataSource(subject);

        searchBar.setOnSearchActionListener(this);
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
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_BACK:
                break;
        }
    }

}