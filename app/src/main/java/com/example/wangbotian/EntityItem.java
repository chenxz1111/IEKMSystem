package com.example.wangbotian;

import java.io.Serializable;

public class EntityItem implements Serializable {
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
}
