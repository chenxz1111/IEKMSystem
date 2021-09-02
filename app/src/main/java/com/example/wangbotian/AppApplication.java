package com.example.wangbotian;

import android.app.Application;
import android.content.Context;

import com.example.wangbotian.db.SQLHelper;

public class AppApplication extends Application {

    private static AppApplication mAppApplication;
    private SQLHelper sqlHelper;
    private String username = "你的名字";
    private String motto = "你的格言";

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

}