package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

import com.example.wangbotian.dao.ChannelManage;
import com.example.wangbotian.edit.ChannelActivity;
import com.google.android.material.tabs.TabLayout;
import com.example.wangbotian.dao.ChannelItem;

import java.util.*;


public class HomeFragment extends Fragment implements View.OnClickListener{
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<TabFragment> fragmentList;
    private LinearLayout ll_more_columns;
    private ImageView button_more_columns;

    private TabLayout tabLayout;
    private List<String> mTitles;
    private String [] title={"语文","数学","英语","物理","化学","生物","历史","地理","政治"};
    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    public final static int CHANNELREQUEST = 1; // 请求码
    public final static int CHANNELRESULT = 10; // 返回码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pager=view.findViewById(R.id.page);
        tabLayout=view.findViewById(R.id.tab_layout);
        button_more_columns = (ImageView) view.findViewById(R.id.button_more_columns);
        button_more_columns.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_more_columns:
                Intent intent = new Intent();
                intent.setClass(this.getActivity(), ChannelActivity.class);
                startActivityForResult(intent, CHANNELREQUEST);
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        setChangelView();
    }

    private void setChangelView(){
        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).getUserChannel());
        int count = userChannelList.size();
        fragmentList.clear();
        mTitles.clear();
        for(int i = 0; i < count; i++){
            mTitles.add(userChannelList.get(i).getName());
            fragmentList.add(new TabFragment(mTitles.get(i)));
            System.out.println(mTitles.get(i));
        }
        fragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList,mTitles, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHANNELREQUEST:
                if (resultCode == CHANNELRESULT) {
                    setChangelView();
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}