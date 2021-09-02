package com.example.wangbotian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.wangbotian.edit.ChannelActivity;

public class AccountFragment extends Fragment implements View.OnClickListener {

    LinearLayout favorite;
    LinearLayout history;
    LinearLayout mistakes;
    LinearLayout setting;
    TextView name;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        favorite = view.findViewById(R.id.account_favorite);
        history = view.findViewById(R.id.account_history);
        mistakes = view.findViewById(R.id.account_mistakes);
        setting = view.findViewById(R.id.account_setting);
        name = view.findViewById(R.id.account_name);
        name.setText(AppApplication.getApp().getUsername());
        favorite.setOnClickListener(this);
        history.setOnClickListener(this);
        mistakes.setOnClickListener(this);
        setting.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.account_favorite:
                intent.setClass(this.getActivity(), FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.account_history:
                intent.setClass(this.getActivity(), HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.account_mistakes:
                intent.setClass(this.getActivity(), MistakesActivity.class);
                startActivity(intent);
                break;
            case R.id.account_setting:
                intent.setClass(this.getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}