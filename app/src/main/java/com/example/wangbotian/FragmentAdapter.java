package com.example.wangbotian;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.*;

public class FragmentAdapter extends FragmentPagerAdapter {


    private List<TabFragment> mFragmentList;//各导航的Fragment
    private List<String> mTitle; //导航的标题

    public FragmentAdapter(FragmentManager fragmentManager, List<TabFragment>fragments, List<String>title, int behavior){
        super(fragmentManager, behavior);
        mFragmentList=fragments;

        mTitle=title;

    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}














