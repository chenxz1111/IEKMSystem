package com.example.wangbotian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TabFragment extends Fragment {
    private TextView titleTv;

    private String mTitle;

    private ListView listView;

    private ArrayList<EntityItem> items_list;

    private EntityListAdapter adapter;

    public TabFragment(String title){
        mTitle=title;
    }

    public TabFragment(ArrayList<EntityItem> items_list) { this.items_list = items_list; }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_tab,container,false);
//        titleTv=view.findViewById(R.id.tv_title);
//        titleTv.setText(mTitle);
        this.listView = view.findViewById(R.id.list_view_home);
        adapter = new EntityListAdapter(this.getActivity(), items_list);
        this.listView.setDivider(null);
        this.listView.setAdapter(adapter);
        this.listView.setClickable(true);
        return view;
    }
}