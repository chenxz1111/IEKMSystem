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

public class QuestionFragment extends Fragment implements View.OnClickListener{

    MaterialCardView card_question, card_more;

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
            case R.id.card_5: //----------- JUST FOR TEST -------------
                intent.setClass(this.getActivity(), EntityActivity.class);
                intent.putExtra("label", "李白");
                startActivity(intent);
                break;
        }
    }


}