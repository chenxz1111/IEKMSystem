package com.example.wangbotian;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 关联实体类
 */

public class EntityRelationFragment extends Fragment {

    ListView relationList;
    private final String PREFS_NAME = "MyPrefsFile";
    public EntityRelationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entity_relation, container, false);
        relationList = v.findViewById(R.id.entity_relation_list);
        EntityActivity entity = (EntityActivity)this.getActivity();
        EntityItem[] items = new EntityItem[entity.entityRelation.size()];
        SharedPreferences history = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> listHistory = history.getStringSet("entity_list_history", null);
        if(listHistory == null) {
            listHistory = new HashSet<String>();
        }
        for(int i = 0; i < entity.entityRelation.size(); i++) {
            String tmp;
            if (entity.entityRelation.getJSONObject(i).containsKey("subject")) {
                tmp = entity.entityRelation.getJSONObject(i).getString("subject_label");
                items[i] = new EntityItem(tmp, entity.entityRelation.getJSONObject(i).getString("predicate_label"));
            }

            else {
                tmp = entity.entityRelation.getJSONObject(i).getString("object_label");
                items[i] = new EntityItem(tmp, entity.entityRelation.getJSONObject(i).getString("predicate_label"));
            }
            if(listHistory.contains(tmp + ";" + entity.entityRelation.getJSONObject(i).getString("predicate_label"))) {
                items[i].access();
            }
        }
        ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(items));
        EntityListAdapter adapter = new EntityListAdapter(this.getActivity(), items_list);
        relationList.setDivider(null);
        relationList.setAdapter(adapter);
        relationList.setClickable(true);
        return v;
    }
}