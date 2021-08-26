package com.example.wangbotian;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class SearchActivity extends AppCompatActivity{
    private NiceSpinner niceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        niceSpinner = findViewById(R.id.nice_spinner);
        List<String> subject = new LinkedList<>(Arrays.asList("语文","数学","英语","物理","化学","生物","历史","地理","政治"));
        niceSpinner.attachDataSource(subject);
    }


}