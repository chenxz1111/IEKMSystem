package com.example.wangbotian;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MultiImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoSourceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;
import com.sina.weibo.sdk.share.WbShareCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


public class ShareActivity extends Activity implements View.OnClickListener, WbShareCallback {

    String shareText;
    EditText editText;

    private RadioButton mShareClientOnly;

    private RadioButton mShareClientH5;

    private Button mCommitBtn;

    //在微博开发平台为应用申请的App Key
    private static final String APP_KY = "2525180379";
    //在微博开放平台设置的授权回调页
    private static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    //在微博开放平台为应用申请的高级权限
    private static final String SCOPE = null;

    private IWBAPI mWBAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mShareClientOnly = findViewById(R.id.share_client_only);
        mShareClientH5 = findViewById(R.id.share_client_h5);
        mCommitBtn = findViewById(R.id.commit);
        mCommitBtn.setOnClickListener(this);
        AuthInfo authInfo = new AuthInfo(this, APP_KY, REDIRECT_URL, SCOPE);
        mWBAPI = WBAPIFactory.createWBAPI(this);
        mWBAPI.registerApp(this, authInfo);
        mWBAPI.setLoggerEnable(true);
        Intent intent = getIntent();
        shareText = intent.getStringExtra("share_text");
        editText = findViewById(R.id.share_edit);
        editText.setText(shareText);
    }

    @Override
    public void onClick(View v) {
        if (v == mCommitBtn) {
            doWeiboShare();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mWBAPI.doResultIntent(data, this);
    }

    private void doWeiboShare() {
        WeiboMultiMessage message = new WeiboMultiMessage();

        TextObject textObject = new TextObject();
        String text = "我正在使用微博客户端发博器分享文字。";

        // 分享文字
        text = shareText;
        textObject.text = editText.getText().toString();
        message.textObject = textObject;

        mWBAPI.shareMessage(message, mShareClientOnly.isChecked());
    }

    @Override
    public void onComplete() {
        Toast.makeText(ShareActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(UiError error) {
        Toast.makeText(ShareActivity.this, "分享失败:" + error.errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(ShareActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
    }
}