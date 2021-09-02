package com.example.wangbotian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.android.material.appbar.MaterialToolbar;
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
        JSONArray dataArray = JSON.parseArray("[{\"qAnswer\":\"D\",\"id\":184710, \"qBody\":\"错题(1/1)\n您的答案:A\n正确答案:D\n\n写下君不见黄河之水天上来,奔流到海不复回诗句的是()A.杜牧B.李商隐C.杜甫D.李白\" }]");
        cardAdapter = new CardsDataAdapter(this.getApplicationContext(),0);
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