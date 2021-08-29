package com.example.wangbotian;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class EntityRelationFragment extends Fragment {

    ListView relationList;

    public EntityRelationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entity_relation, container, false);
        relationList = v.findViewById(R.id.entity_relation_list);
        EntityActivity entity = (EntityActivity)this.getActivity();
        EntityItem[] items = new EntityItem[entity.entityRelation.size()];
        for(int i = 0; i < entity.entityRelation.size(); i++) {
            if (entity.entityRelation.getJSONObject(i).containsKey("subject"))
                items[i] = new EntityItem(entity.entityRelation.getJSONObject(i).getString("subject_label"), entity.entityRelation.getJSONObject(i).getString("predicate_label"));
            else
                items[i] = new EntityItem(entity.entityRelation.getJSONObject(i).getString("object_label"), entity.entityRelation.getJSONObject(i).getString("predicate_label"));
        }
        ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(items));
        EntityListAdapter adapter = new EntityListAdapter(this.getActivity(), items_list);
        relationList.setDivider(null);
        relationList.setAdapter(adapter);
        relationList.setClickable(true);
        return v;
    }
}