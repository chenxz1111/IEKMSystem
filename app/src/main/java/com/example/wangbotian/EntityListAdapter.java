package com.example.wangbotian;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import  java.util.List;

class ViewHolder{
    TextView tvLabel;
    TextView tvCategory;
}

public class EntityListAdapter extends MyBaseAdapter<EntityItem> {
    public EntityListAdapter(Context context, List<EntityItem> list){
        super(context, list);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        View convertView;
        ViewHolder viewHolder;
        if(view == null){
            convertView = inflater.inflate(R.layout.entity_list_item, parent, false);
            // Initialize ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.tvLabel = (TextView) convertView.findViewById(R.id.entity_list_title);
            viewHolder.tvCategory = (TextView) convertView.findViewById(R.id.entity_list_secondary);
            convertView.setTag(viewHolder);
        }else{
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EntityItem item = (EntityItem) mList.get(position);

        viewHolder.tvLabel.setText(item.getLabel());
        viewHolder.tvCategory.setText(item.getCategory());

        return convertView;
    }
}
