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
    private String[] labelList = {"李白", "杜甫", "白居易", "动词", "形容词", "名词", "光合作用", "食物链", "突触"};
    private String[] categoryList = {"人物", "人物", "人物", "实义动词", "形容词", "普通名词", "光反应和暗反应", "生态系统的结构", "通过神经系统的调节"};
    String[] tmpList = {"《父与子》","《康熙字典》","《离骚》","《六国论》","《方山子传》","《锦瑟》","《屈原》"};
    String[] tmpCate = {"课文","课文","作品","作品","作品","作品","作品"};
    private ArrayList<ArrayList<EntityItem>> entityList = new ArrayList<ArrayList<EntityItem>>();
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
        for(int i = 0; i < 9; i++){
            entityList.add(new ArrayList<EntityItem>());
        }
        for(int i = 0; i < 3; i++){
            entityList.get(0).add(new EntityItem(labelList[i], categoryList[i]));
            entityList.get(2).add(new EntityItem(labelList[i+3], categoryList[i+3]));
            entityList.get(5).add(new EntityItem(labelList[i+6], categoryList[i+6]));
        }
        for(int i = 0; i < tmpCate.length; i++) {
            entityList.get(0).add(new EntityItem(tmpList[i], categoryList[i]));
        }
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
            String sub = userChannelList.get(i).getName();
            int id = convertC2N(sub);
            mTitles.add(userChannelList.get(i).getName());
//            fragmentList.add(new TabFragment(mTitles.get(i)));
            fragmentList.add(new TabFragment(entityList.get(id)));
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

    public static int convertC2N(String subject) {
        int id = 0;
        switch (subject) {
            case "语文":
                id = 0;
                break;
            case "数学":
                id = 1;
                break;
            case "英语":
                id = 2;
                break;
            case "物理":
                id = 3;
                break;
            case "化学":
                id = 4;
                break;
            case "生物":
                id = 5;
                break;
            case "历史":
                id = 6;
                break;
            case "地理":
                id = 7;
                break;
            case "政治":
                id = 8;
                break;
            default:
                break;
        }
        return id;
    }

}