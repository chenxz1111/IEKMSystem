package com.example.wangbotian;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.cardview.widget.CardView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CardsDataAdapter extends ArrayAdapter<String> {

    public CardsDataAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        final boolean[] checked = {false};
        TextView v = contentView.findViewById(R.id.exam_txv);
        TextView answerA = contentView.findViewById(R.id.answer_A_text);
        TextView answerB = contentView.findViewById(R.id.answer_B_text);
        TextView answerC = contentView.findViewById(R.id.answer_C_text);
        TextView answerD = contentView.findViewById(R.id.answer_D_text);

        CardView ansCardA = contentView.findViewById(R.id.card_ans_a);
        CardView ansCardB = contentView.findViewById(R.id.card_ans_b);
        CardView ansCardC = contentView.findViewById(R.id.card_ans_c);
        CardView ansCardD = contentView.findViewById(R.id.card_ans_d);
        CardView rightCard = null;

        JSONObject exam = JSON.parseObject(getItem(position));
        String examBody = exam.getString("qBody");
        int idA = examBody.indexOf("A.");
        int idB = examBody.indexOf("B.");
        int idC = examBody.indexOf("C.");
        int idD = examBody.indexOf("D.");
        switch (exam.getString("qAnswer")){
            case "A":
                rightCard = ansCardA;
                break;
            case "B":
                rightCard = ansCardB;
                break;
            case "C":
                rightCard = ansCardC;
                break;
            case "D":
                rightCard = ansCardD;
                break;
        }
        v.setText(examBody.substring(0, idA));
        answerA.setText(examBody.substring(idA, idB));
        answerB.setText(examBody.substring(idB, idC));
        answerC.setText(examBody.substring(idC, idD));
        answerD.setText(examBody.substring(idD));

        CardView finalRightCard = rightCard;
        ansCardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checked[0]) {
                    ansCardA.setCardBackgroundColor(0xFFDFA3A3);
                    finalRightCard.setCardBackgroundColor(0xFF92D398);
                    checked[0] = true;
                }
            }
        });
        ansCardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checked[0]) {
                    ansCardB.setCardBackgroundColor(0xFFDFA3A3);
                    finalRightCard.setCardBackgroundColor(0xFF92D398);
                    checked[0] = true;
                }
            }
        });

        ansCardC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checked[0]) {
                    ansCardC.setCardBackgroundColor(0xFFDFA3A3);
                    finalRightCard.setCardBackgroundColor(0xFF92D398);
                    checked[0] = true;
                }
            }
        });
        ansCardD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checked[0]) {
                    ansCardD.setCardBackgroundColor(0xFFDFA3A3);
                    finalRightCard.setCardBackgroundColor(0xFF92D398);
                    checked[0] = true;
                }
            }
        });



        return contentView;
    }

}