package com.example.wangbotian;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class EntityPropertyFragment extends Fragment {

    ListView propertyList;

    public EntityPropertyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entity_property, container, false);
        propertyList = v.findViewById(R.id.entity_property_list);
        EntityActivity entity = (EntityActivity)this.getActivity();
        EntityItem[] items = new EntityItem[entity.entityProperty.size()];
        for(int i = 0; i < entity.entityProperty.size(); i++) {
            items[i] = new EntityItem(entity.entityProperty.getJSONObject(i).getString("predicateLabel"), entity.entityProperty.getJSONObject(i).getString("object"));
        }
        ArrayList<EntityItem> items_list = new ArrayList<EntityItem>(Arrays.asList(items));
        EntityListAdapter adapter = new EntityListAdapter(this.getActivity(), items_list);
        propertyList.setDivider(null);
        propertyList.setAdapter(adapter);
        propertyList.setClickable(false);
        return v;
    }
}