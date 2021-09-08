package com.example.wangbotian;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.cardview.widget.CardView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CardsDataAdapter extends ArrayAdapter<String> {

    int mistake;

    public CardsDataAdapter(Context context, int textViewResourceId, int mis) {
        super(context, textViewResourceId);
        mistake = mis;
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        final boolean[] checked = {false};
        String right = null;
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
                right = "A";
                break;
            case "B":
                rightCard = ansCardB;
                right = "B";
                break;
            case "C":
                rightCard = ansCardC;
                right = "C";
                break;
            case "D":
                rightCard = ansCardD;
                right = "D";
                break;
        }
        v.setText(examBody.substring(0, idA));
        answerA.setText(examBody.substring(idA, idB));
        answerB.setText(examBody.substring(idB, idC));
        answerC.setText(examBody.substring(idC, idD));
        answerD.setText(examBody.substring(idD));
        if (mistake == 1) {

            ansCardA.setCardBackgroundColor(0xFFDFA3A3);//---------DELETE
            ansCardD.setCardBackgroundColor(0xFF92D398); //-----------DELETE

        }

        else {
            CardView finalRightCard = rightCard;
            String finalRight = right;
            ansCardA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!checked[0]) {
                        ansCardA.setCardBackgroundColor(0xFFDFA3A3);
                        finalRightCard.setCardBackgroundColor(0xFF92D398);
                        if (!finalRight.equals("A") && mistake == 0)
                            OpenEducation.sendPost("http://47.93.219.219:8080/AddMistake", "username=" + AppApplication.getApp().getUsername()
                            + "&question=" + examBody.substring(0, idA) + "&a=" + examBody.substring(idA, idB) + "&b=" + examBody.substring(idB, idC)
                            + "&c=" + examBody.substring(idC, idD) + "&d=" + examBody.substring(idD) + "&wrong=A&right=" + finalRight);
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
                        if (!finalRight.equals("B") && mistake == 0)
                            OpenEducation.sendPost("http://47.93.219.219:8080/AddMistake", "username=" + AppApplication.getApp().getUsername()
                                    + "&question=" + examBody.substring(0, idA) + "&a=" + examBody.substring(idA, idB) + "&b=" + examBody.substring(idB, idC)
                                    + "&c=" + examBody.substring(idC, idD) + "&d=" + examBody.substring(idD) + "&wrong=B&right=" + finalRight);
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
                        if (!finalRight.equals("C") && mistake == 0)
                            OpenEducation.sendPost("http://47.93.219.219:8080/AddMistake", "username=" + AppApplication.getApp().getUsername()
                                    + "&question=" + examBody.substring(0, idA) + "&a=" + examBody.substring(idA, idB) + "&b=" + examBody.substring(idB, idC)
                                    + "&c=" + examBody.substring(idC, idD) + "&d=" + examBody.substring(idD) + "&wrong=C&right=" + finalRight);
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
                        if (!finalRight.equals("D") && mistake == 0)
                            OpenEducation.sendPost("http://47.93.219.219:8080/AddMistake", "username=" + AppApplication.getApp().getUsername()
                                    + "&question=" + examBody.substring(0, idA) + "&a=" + examBody.substring(idA, idB) + "&b=" + examBody.substring(idB, idC)
                                    + "&c=" + examBody.substring(idC, idD) + "&d=" + examBody.substring(idD) + "&wrong=D&right=" + finalRight);
                        checked[0] = true;
                    }
                }
            });
        }


        return contentView;
    }

}