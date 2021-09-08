package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.appbar.MaterialToolbar;
import com.hjq.xtoast.XToast;
import com.wenchao.cardstack.CardStack;

public class MistakesActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialToolbar top_bar;
    CardsDataAdapter cardAdapter;
    CardStack cardStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistakes);
        top_bar =  findViewById(R.id.topAppBar2);
        top_bar.setNavigationOnClickListener(this);
        cardStack = findViewById(R.id.mistake_card_stake);
        cardStack.setContentResource(R.layout.exam_card);
        cardStack.setStackMargin(20);

        JSONArray result = JSON.parseArray(OpenEducation.sendPost("http://47.93.219.219:8080/CatchMistake", "username=" + AppApplication.getApp().getUsername()));
        JSONArray dataArray = new JSONArray();
        try {
            for (int i = 0; i < result.size(); i++) {
                JSONObject js = result.getJSONObject(i);
                String mistakeStr = "{\"qAnswer\":\"" + js.getString("right") + "\",\"qBody\":\"错题(" + (i + 1) + "/" + result.size() + ")\n您的答案:" + js.getString("wrong") + "\n正确答案:" + js.getString("right") + "\n\n" + js.getString("question") + js.getString("a") + js.getString("b") + js.getString("c") + js.getString("d") + "\"}";
                dataArray.add(JSONObject.parseObject(mistakeStr));
            }
        } catch (Exception e) {
        Log.d("debug", e.toString());
        new XToast<>(this)
                .setDuration(1000)
                .setView(R.layout.toast_hint)
                .setAnimStyle(android.R.style.Animation_Activity)
                .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                .setText(android.R.id.message, "无网络或接口失效")
                .show();
        }

        //JSONArray dataArray = JSON.parseArray("[{\"qAnswer\":\"D\",\"id\":184710, \"qBody\":\"错题(1/1)\n您的答案:A\n正确答案:D\n\n写下君不见黄河之水天上来,奔流到海不复回诗句的是()A.杜牧B.李商隐C.杜甫D.李白\" }]");
        cardAdapter = new CardsDataAdapter(this.getApplicationContext(),0, 1);
        for (int i = 0; i < dataArray.size(); i++){
            if (dataArray.getString(i).indexOf("A.") > 0)
                cardAdapter.add(dataArray.getString(i));
        }
        cardStack.setAdapter(cardAdapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MistakesActivity.this, MainActivity.class);
        intent.putExtra("id",2);
        startActivity(intent);
    }
}