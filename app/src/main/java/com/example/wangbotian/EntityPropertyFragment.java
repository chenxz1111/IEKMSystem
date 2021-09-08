package com.example.wangbotian;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 实体属性类
 */
public class EntityPropertyFragment extends Fragment {

    ListView propertyList;
    private final String PREFS_NAME = "MyPrefsFile";
    public EntityPropertyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entity_property, container, false);
        propertyList = v.findViewById(R.id.entity_property_list);
        EntityActivity entity = (EntityActivity)this.getActivity();
        EntityItem[] items = new EntityItem[entity.entityProperty.size()];
        SharedPreferences history = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> listHistory = history.getStringSet("entity_list_history", null);
        if(listHistory == null) {
            listHistory = new HashSet<String>();
        }
        for(int i = 0; i < entity.entityProperty.size(); i++) {
            items[i] = new EntityItem(entity.entityProperty.getJSONObject(i).getString("predicateLabel"), entity.entityProperty.getJSONObject(i).getString("object"));
            if(listHistory.contains(entity.entityProperty.getJSONObject(i).getString("predicateLabel") + ";" + entity.entityProperty.getJSONObject(i).getString("object"))) {
                items[i].access();
            }
        }
        ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(items));
        EntityListAdapter adapter = new EntityListAdapter(this.getActivity(), items_list);
        propertyList.setDivider(null);
        propertyList.setAdapter(adapter);
        propertyList.setClickable(false);
        return v;
    }
}