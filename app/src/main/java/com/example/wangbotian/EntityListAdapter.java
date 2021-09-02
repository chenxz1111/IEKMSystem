package com.example.wangbotian;

import android.content.Entity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.cardview.widget.CardView;

import com.google.android.material.card.MaterialCardView;

import  java.util.List;

class ViewHolder{
    TextView tvLabel;
    TextView tvCategory;
    MaterialCardView tCard;
}

public class EntityListAdapter extends MyBaseAdapter<EntityItem> {
    Context c;
    View convertView;
    ViewHolder viewHolder;
    public EntityListAdapter(Context context, List<EntityItem> list){
        super(context, list);
        c = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        if(view == null){
            convertView = inflater.inflate(R.layout.entity_list_item, parent, false);
            // Initialize ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.tvLabel = (TextView) convertView.findViewById(R.id.entity_list_title);
            viewHolder.tvCategory = (TextView) convertView.findViewById(R.id.entity_list_secondary);
            viewHolder.tCard = convertView.findViewById(R.id.card_entity);
            convertView.setTag(viewHolder);
        }else{
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EntityItem item = (EntityItem) mList.get(position);

        viewHolder.tvLabel.setText(item.getLabel());
        viewHolder.tvCategory.setText(item.getCategory());
        if(item.isAccess()) {
            viewHolder.tCard.setCardBackgroundColor(0xFFCAD5D3);
        } else {
            viewHolder.tCard.setCardBackgroundColor(0xFDC9F1F1);
        }
        viewHolder.tCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(c, EntityActivity.class);
                intent.putExtra("label", item.getLabel());
                intent.putExtra("category", item.getCategory());
                c.startActivity(intent);
            }
        });

        return convertView;
    }
}
