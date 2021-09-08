package com.example.wangbotian;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.alibaba.fastjson.JSONArray;
import com.example.wangbotian.db.SQLHelper;

public class AppApplication extends Application {

    private static AppApplication mAppApplication;
    private SQLHelper sqlHelper;
    private String username = "你的名字";
    private String motto = "你的格言";
    private String password = "123456";
    private String avatarId = "1";
    public JSONArray chinese_list, math_list, english_list, physics_list, chemistry_list, biology_list, history_list, geo_list, politics_list;

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getMotto(){
        return motto;
    }

    public void setMotto(String motto){
        this.motto = motto;
    }

    public String getPassword() { return password; }

    public void setPassword(String password){
        this.password = password;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication = this;
    }

    /**
     * 获取Application
     */
    public static AppApplication getApp() {
        return mAppApplication;
    }

    /**
     * 获取数据库Helper
     */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(mAppApplication);
        return sqlHelper;
    }

    @Override
    public void onTerminate() {
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTerminate();
        //整体摧毁的时候调用这个方法
    }

    public int getAvatar(){
        switch (avatarId){
            case "2":
                return R.drawable.profile_2;
            case "3":
                return R.drawable.profile_3;
            case "4":
                return R.drawable.profile_4;
            case "5":
                return R.drawable.profile_5;
            default:
                return R.drawable.profile_1;
        }
    }

}