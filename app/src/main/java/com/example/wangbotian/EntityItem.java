package com.example.wangbotian;

public class EntityItem implements Comparable<EntityItem> {
    public String label;
    public String category;

    public EntityItem(String label, String category){
        this.label = label;
        this.category = category;
    }

    public String getLabel(){
        return this.label;
    }

    public String getCategory(){
        return this.category;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public void setCategory(String category){
        this.category = category;
    }

    @Override
    public int compareTo(EntityItem e) {
        return label.compareTo(e.getLabel());
    }
}
