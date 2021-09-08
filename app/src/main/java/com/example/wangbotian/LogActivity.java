package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.content.*;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hjq.xtoast.XToast;
import com.javier.filterview.FilterView;
import com.javier.filterview.OnFilterViewResultListener;
import com.javier.filterview.single.OnSingleOptionListener;
import com.javier.filterview.single.SingleOption;
import com.javier.filterview.single.SingleSection;

import java.util.*;
/**
 登录类
 */
public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    Button logButton, regButton;
    TextInputEditText usernameText, passwordText;
    TextInputLayout usernameLay, passwordLay;
    private JSONObject main_result;
    private JSONArray chinese, math, english, physics, chemistry, biology, history, geo, politics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_log);

        logButton = findViewById(R.id.log_button);
        logButton.setOnClickListener(this);

        regButton = findViewById(R.id.reg_button);
        regButton.setOnClickListener(this);

        usernameText = findViewById(R.id.username_text);
        passwordText = findViewById(R.id.password_text);

        usernameLay = findViewById(R.id.username_layout);
        passwordLay = findViewById(R.id.password_layout);

    }

    public int checkPassword(String msg){
        if(msg.equals("2")) return 2; // 用户不存在
        else if(msg.equals("0")) return 0; // 密码不正确
        else if(msg.equals("1")) return 1;
        return -1;// 表示成功
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.log_button:
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                String param = "username=" + username + "&password=" + password;
                String msg = "1";
                try {
                    msg = OpenEducation.sendPost("http://47.93.219.219:8080/CheckPassword", param);

                    Log.i("msg", msg);
                    int res = checkPassword(msg);
                    if (res == 1) {
                        //                    System.out.println(OpenEducation.sendPost("http://192.168.3.192:8080/CheckUser", "username=testUser"));
                        //                    System.out.println(OpenEducation.sendPost("http://192.168.3.192:8080/AddUser", "username=testUser&password=123456"));
                        //                    System.out.println(OpenEducation.sendPost("http://192.168.3.192:8080/CheckPassword", "username=testUser&password=123456"));
                        //                    System.out.println(OpenEducation.sendPost("http://192.168.3.192:8080/ChangeId", "username=testUser&newname=testUser1"));
                        //                    System.out.println(OpenEducation.sendPost("http://192.168.3.192:8080/ChangePassword", "username=testUser1&password=1234567"));
                        main_result = JSON.parseObject(OpenEducation.sendPost("http://47.93.219.219:8080/getAllEntity", ""));
                        chinese = main_result.getJSONArray("chinese");
                        math = main_result.getJSONArray("math");
                        english = main_result.getJSONArray("english");
                        physics = main_result.getJSONArray("physics");
                        chemistry = main_result.getJSONArray("chemistry");
                        biology = main_result.getJSONArray("biology");
                        history = main_result.getJSONArray("history");
                        geo = main_result.getJSONArray("geo");
                        politics = main_result.getJSONArray("politics");
                        Collections.shuffle(chinese);
                        Collections.shuffle(math);
                        Collections.shuffle(english);
                        Collections.shuffle(physics);
                        Collections.shuffle(chemistry);
                        Collections.shuffle(biology);
                        Collections.shuffle(history);
                        Collections.shuffle(geo);
                        Collections.shuffle(politics);

                        AppApplication.getApp().chinese_list = chinese;
                        AppApplication.getApp().math_list = math;
                        AppApplication.getApp().english_list = english;
                        AppApplication.getApp().physics_list = physics;
                        AppApplication.getApp().chemistry_list = chemistry;
                        AppApplication.getApp().biology_list = biology;
                        AppApplication.getApp().history_list = history;
                        AppApplication.getApp().geo_list = geo;
                        AppApplication.getApp().politics_list = politics;

                        AppApplication.getApp().setUsername(username);
                        AppApplication.getApp().setMotto(OpenEducation.sendPost("http://47.93.219.219:8080/CatchMotto", "username=" + username));
                        AppApplication.getApp().setAvatarId(OpenEducation.sendPost("http://47.93.219.219:8080/CatchAvatar", "username=" + username));
                        Intent intent = new Intent();
                        new XToast<>(this)
                                .setDuration(1000)
                                .setView(R.layout.toast_hint)
                                .setAnimStyle(android.R.style.Animation_Activity)
                                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_finish)
                                .setText(android.R.id.message, "登录成功")
                                .show();
                        intent.setClass(LogActivity.this, MainActivity.class);
                        this.startActivity(intent);
                    } else if (res == 0) {
                        new XToast<>(this)
                                .setDuration(1000)
                                .setView(R.layout.toast_hint)
                                .setAnimStyle(android.R.style.Animation_Activity)
                                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                                .setText(android.R.id.message, "密码不正确")
                                .show();
                    } else if (res == 2) {
                        new XToast<>(this)
                                .setDuration(1000)
                                .setView(R.layout.toast_hint)
                                .setAnimStyle(android.R.style.Animation_Activity)
                                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                                .setText(android.R.id.message, "用户名不存在")
                                .show();
                    } else{
                        new XToast<>(this)
                                .setDuration(1000)
                                .setView(R.layout.toast_hint)
                                .setAnimStyle(android.R.style.Animation_Activity)
                                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                                .setText(android.R.id.message, "无网络")
                                .show();
                    }
                }catch (Exception e) {
                    Log.d("debug", e.toString());
                    new XToast<>(this)
                            .setDuration(1000)
                            .setView(R.layout.toast_hint)
                            .setAnimStyle(android.R.style.Animation_Activity)
                            .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                            .setText(android.R.id.message, "无网络")
                            .show();
                }
                    break;
            case R.id.reg_button:
                Intent intent = new Intent();
                intent.setClass(LogActivity.this, RegActivity.class);
                this.startActivity(intent);
                break;
        }

    }

}