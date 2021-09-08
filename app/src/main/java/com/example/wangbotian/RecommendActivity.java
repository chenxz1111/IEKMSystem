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

public class RecommendActivity extends AppCompatActivity {

    MaterialToolbar top_bar;
    CardsDataAdapter cardAdapter;
    CardStack cardStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        top_bar = findViewById(R.id.topAppBar_re);
        top_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecommendActivity.this, MainActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });
        try {
            cardStack = findViewById(R.id.recommend_card_stake);
            cardStack.setContentResource(R.layout.exam_card);
            cardStack.setStackMargin(20);
            JSONObject entityExam = JSON.parseObject(OpenEducation.entityExam("李白"));
            JSONArray dataArray = getRecommend();
            cardAdapter = new CardsDataAdapter(this.getApplicationContext(), 0, 2);
            for (int i = 0; i < dataArray.size(); i++) {
                if (dataArray.getString(i).indexOf("A.") > 0)
                    cardAdapter.add(dataArray.getString(i));
            }
            cardStack.setAdapter(cardAdapter);
        }
        catch (Exception e) {
            Log.d("debug", e.toString());
            new XToast<>(this)
                    .setDuration(1000)
                    .setView(R.layout.toast_hint)
                    .setAnimStyle(android.R.style.Animation_Activity)
                    .setImageDrawable(android.R.id.icon, R.mipmap.ic_dialog_tip_error)
                    .setText(android.R.id.message, "无网络或接口失效")
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RecommendActivity.this, MainActivity.class);
        intent.putExtra("id",1);
        startActivity(intent);
        super.onBackPressed();
    }

    public JSONArray getRecommend(){
        JSONArray result = new JSONArray();
        JSONArray favor = JSON.parseArray(OpenEducation.sendPost("http://47.93.219.219:8080/CatchFavor", "username=" + AppApplication.getApp().getUsername()));
        JSONArray history = JSON.parseArray(OpenEducation.sendPost("http://47.93.219.219:8080/CatchHistory", "username=" + AppApplication.getApp().getUsername()));
        for (int i = 0; i < history.size(); i+=2){
            String label = history.getJSONObject(i).getString("label");
            JSONArray labelQuestion = JSONArray.parseArray(JSONObject.parseObject(OpenEducation.entityExam(label)).getString("data"));
            if (labelQuestion.size() > 0) {
                JSONObject addObject = labelQuestion.getJSONObject((int) (Math.random() * labelQuestion.size()));
                result.add(addObject);
            }
        }
        for (int i = 0; i < favor.size(); i+=2){
            String label = favor.getJSONObject(i).getString("label");
            JSONArray labelQuestion = JSONArray.parseArray(JSONObject.parseObject(OpenEducation.entityExam(label)).getString("data"));
            if (labelQuestion.size() > 0) {
                JSONObject addObject = labelQuestion.getJSONObject((int) (Math.random() * labelQuestion.size()));
                result.add(addObject);
            }
        }
        return result;
    }

}