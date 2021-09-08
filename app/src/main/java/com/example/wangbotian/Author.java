package com.example.wangbotian;

import java.util.Date;

import cn.jiguang.imui.commons.models.IUser;
/**
 问答界面封装类
 */
public class Author implements IUser {

    private String id;
    private String displayName;
    private String avatar;

    public Author(String id, String displayName, String avatar) {
        this.id = id;
        this.displayName = displayName;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getAvatarFilePath() {
        return avatar;
    }
}