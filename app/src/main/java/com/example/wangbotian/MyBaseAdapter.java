package com.example.wangbotian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
/**
 用来封装自己的Adapter的抽象类
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> mList;
    protected LayoutInflater inflater;

    public MyBaseAdapter(Context context, List<T> list){
        this.mList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return mList.size();
    }

    @Override
    public Object getItem(int position){
        return mList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public abstract View getView(int position, View view, ViewGroup parent);
}