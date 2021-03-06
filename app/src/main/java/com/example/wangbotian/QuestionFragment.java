package com.example.wangbotian;

 import android.content.Entity;
 import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
 import com.hjq.xtoast.XToast;
/**
 智能问答窗口
 */
public class QuestionFragment extends Fragment implements View.OnClickListener{

    MaterialCardView card_question, card_more, card_recommend, card_radar;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        card_question = v.findViewById(R.id.card_question);
        card_question.setOnClickListener(this);
        card_more = v.findViewById(R.id.card_5);
        card_more.setOnClickListener(this);
        card_recommend = v.findViewById(R.id.card_4);
        card_recommend.setOnClickListener(this);
        card_radar = v.findViewById(R.id.card_2);
        card_radar.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.card_question:
                intent.setClass(this.getActivity(), QuestionActivity.class);
                startActivity(intent);
                break;
            case R.id.card_2:
                intent.setClass(this.getActivity(), RadarActivity.class);
                startActivity(intent);
                break;
            case R.id.card_4:
                intent.setClass(this.getActivity(), RecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.card_5: //----------- JUST FOR TEST -------------
                Toast.makeText(this.getActivity(),"更多功能敬请期待~",Toast.LENGTH_LONG).show();
                break;
        }
    }



}