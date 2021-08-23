package com.example.wangbotian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TabFragment extends Fragment {
    private TextView titleTv;

    private String mTitle;

    public TabFragment(String title){
        mTitle=title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_tab,container,false);
        titleTv=view.findViewById(R.id.tv_title);
        titleTv.setText(mTitle);
        return view;
    }
}